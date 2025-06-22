package org.example.project.ui.screens.levelingtest

import androidx.compose.runtime.mutableStateListOf
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.Questions
import org.example.project.domain.usecase.LevelingTestUseCase
import org.example.project.ui.extensions.coroutineScope
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.delay
import org.example.project.data.database.local.user.UserLocalDataSource
import org.example.project.data.utils.SessionTimeTracker
import org.example.project.domain.model.AuthData
import org.example.project.domain.model.Email
import org.example.project.domain.model.LevelingTestAnswers
import org.example.project.domain.service.TooManyRequestsException
import org.example.project.domain.usecase.HomeUseCase

sealed class LevelingTestResult {
    data object Loading : LevelingTestResult()
    data object Success : LevelingTestResult()
    data object Generating : LevelingTestResult()
    data class Completed(val message: String) : LevelingTestResult()
    data class Error(val message: String) : LevelingTestResult()
}

class LevelingTestViewModel(
    componentContext: ComponentContext,
    private val levelingTestUseCase: LevelingTestUseCase,
    private val homeUseCase: HomeUseCase,
    private val userLocalDataSource: UserLocalDataSource,
    private val authData: AuthData,
    private val onNavigateToHome: (AuthData, Int) -> Unit
) : ComponentContext by componentContext {

    private val sessionTracker = SessionTimeTracker()

    init {
        sessionTracker.startSession()
    }

    fun onExit(){
        val seconds = sessionTracker.endSession()
        onNavigateToHome(authData, seconds)
    }


    private val _isFirstLesson = mutableStateOf(false)

    private val _levelingTestResult = MutableStateFlow<LevelingTestResult?>(LevelingTestResult.Loading)
    val levelingTestResult : MutableStateFlow<LevelingTestResult?> = _levelingTestResult

    private val _answers = mutableStateListOf<String>()

    private val _questions =  mutableStateListOf<Questions>()
    val questions : List<Questions> get() = _questions

    private val _currentQuestionIndex = mutableStateOf(0)
    val currentQuestionIndex: Int get() = _currentQuestionIndex.value



    suspend fun getQuestion() {
        val needTest = homeUseCase.verifyLevelingTest(Email(authData.email))
        _isFirstLesson.value = needTest.isFailure
        _levelingTestResult.value = LevelingTestResult.Loading

        val maxRetries = 5
        var attempts = 0

        while (attempts < maxRetries) {
            val response = if (_isFirstLesson.value) {
                levelingTestUseCase.getQuestion()
            } else {
                levelingTestUseCase.getQuestionSmartChallenges(Email(email = authData.email))
            }

            if (response.isSuccess) {
                val questionList = response.getOrNull().orEmpty()
                _questions.clear()
                _questions.addAll(questionList)
                _levelingTestResult.value = LevelingTestResult.Success
                return
            }

            val error = response.exceptionOrNull()
            if (error is TooManyRequestsException) {
                _levelingTestResult.value = LevelingTestResult.Generating
                delay(25000L)
                attempts++
            } else {
                _levelingTestResult.value = LevelingTestResult.Error(error?.message ?: "Erro desconhecido")
                return
            }
        }

        _levelingTestResult.value = LevelingTestResult.Error("Atividades ainda estão sendo geradas. Tente novamente em alguns minutos.")
    }


    private suspend fun submitAnswer(answer: String) {
        _answers.add(answer)
        if (_currentQuestionIndex.value < _questions.size - 1) {
            _currentQuestionIndex.value += 1
        } else {
            _levelingTestResult.value = LevelingTestResult.Loading
            val formattedAnswer = LevelingTestAnswers(email = authData.email, questions = _answers)
            val response = levelingTestUseCase.submitAnswer(formattedAnswer)
            response.onSuccess {
                _levelingTestResult.value = LevelingTestResult.Completed(it.response)
                userLocalDataSource.incrementSmartChallengesCompleted(authData.email)
            }.onFailure {
                _levelingTestResult.value = LevelingTestResult.Error(it.message ?: "Erro desconhecido")
            }

        }
    }

    fun onEvent(event: LevelingTestEvent) {
        when (event) {
            is LevelingTestEvent.SubmitAnswer -> {
                coroutineScope.launch {
                    submitAnswer(event.answer)
                }
            }

            is LevelingTestEvent.GetQuestions -> {
                coroutineScope.launch {
                    getQuestion()
                }
            }

            is LevelingTestEvent.GoToHome -> {
                val seconds = sessionTracker.endSession()
                onNavigateToHome(authData, seconds)
            }
        }
    }

}

sealed class LevelingTestEvent {
    data class SubmitAnswer(val answer: String) : LevelingTestEvent()
    data object GetQuestions : LevelingTestEvent()
    data object GoToHome : LevelingTestEvent()
}





