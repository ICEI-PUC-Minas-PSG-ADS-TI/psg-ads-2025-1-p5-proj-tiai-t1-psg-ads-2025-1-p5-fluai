package org.example.project.ui.screens.levelingtest

import androidx.compose.runtime.mutableStateListOf
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.Questions
import org.example.project.domain.usecase.LevelingTestUseCase
import org.example.project.ui.extensions.coroutineScope
import androidx.compose.runtime.mutableStateOf
import org.example.project.data.database.local.user.UserLocalDataSource
import org.example.project.domain.model.AuthData
import org.example.project.domain.model.Email
import org.example.project.domain.model.LevelingTestAnswers
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
    private val onNavigateToHome: (AuthData) -> Unit
) : ComponentContext by componentContext {


    private val _isFirstLesson = mutableStateOf(false)

    private val _levelingTestResult = MutableStateFlow<LevelingTestResult?>(LevelingTestResult.Loading)
    val levelingTestResult : MutableStateFlow<LevelingTestResult?> = _levelingTestResult

    private val _answers = mutableStateListOf<String>()

    private val _questions =  mutableStateListOf<Questions>()
    val questions : List<Questions> get() = _questions

    private val _currentQuestionIndex = mutableStateOf(0)
    val currentQuestionIndex: Int get() = _currentQuestionIndex.value



    private suspend fun preloadQuestions() {
        _levelingTestResult.value = LevelingTestResult.Generating
        val response = homeUseCase.preloadQuestions(Email(authData.email))
        response.onSuccess {
            _levelingTestResult.value = LevelingTestResult.Success
        }.onFailure {
            _levelingTestResult.value =
                LevelingTestResult.Error(message = it.message ?: "Err desconhecido")
        }
    }

    suspend fun getQuestion() {

        val needTest = homeUseCase.verifyLevelingTest(Email(authData.email))
        _isFirstLesson.value = needTest.isFailure
        _levelingTestResult.value = LevelingTestResult.Loading
        val response = if(_isFirstLesson.value){
            levelingTestUseCase.getQuestion()
        }else{
            preloadQuestions()
            levelingTestUseCase.getQuestionSmartChallenges(Email(email = authData.email))
        }

        response.onSuccess { questionList ->
            _questions.clear()
            _questions.addAll(questionList)
            _levelingTestResult.value = LevelingTestResult.Success
        }.onFailure {
            _levelingTestResult.value = LevelingTestResult.Error(it.message ?: "Erro desconhecido")
        }
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

            is LevelingTestEvent.GoToHome -> onNavigateToHome(authData)
        }
    }

}

sealed class LevelingTestEvent {
    data class SubmitAnswer(val answer: String) : LevelingTestEvent()
    data object GetQuestions : LevelingTestEvent()
    data object GoToHome : LevelingTestEvent()
}





