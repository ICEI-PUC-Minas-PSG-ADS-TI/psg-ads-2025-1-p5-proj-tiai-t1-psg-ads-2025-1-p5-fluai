package org.example.project.ui.screens.fluencyboost

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.database.local.user.UserLocalDataSource
import org.example.project.data.utils.SessionTimeTracker
import org.example.project.domain.model.AuthData
import org.example.project.domain.model.Email
import org.example.project.domain.model.LevelingTestAnswers
import org.example.project.domain.model.Questions
import org.example.project.domain.service.TooManyRequestsException
import org.example.project.domain.usecase.LevelingTestUseCase
import org.example.project.ui.extensions.coroutineScope

sealed class FluencyBoostResult {
    data object Loading : FluencyBoostResult()
    data object Success : FluencyBoostResult()
    data object Generating : FluencyBoostResult()
    data class Completed(val message: String) : FluencyBoostResult()
    data class Error(val message: String) : FluencyBoostResult()
}

class FluencyBoostViewModel(
    componentContext: ComponentContext,
    private val authData: AuthData,
    private val userLocalDataSource: UserLocalDataSource,
    private val levelingTestUseCase: LevelingTestUseCase,
    val navigateToHome: (AuthData, Int) -> Unit
) : ComponentContext by componentContext {

    private val sessionTracker = SessionTimeTracker()

    init {
        sessionTracker.startSession()
    }

    fun onExit(){
        val seconds = sessionTracker.endSession()
        navigateToHome(authData, seconds)
    }

    private val _fluencyBoostResult = MutableStateFlow<FluencyBoostResult?>(FluencyBoostResult.Loading)
    val fluencyBoostResult : MutableStateFlow<FluencyBoostResult?> = _fluencyBoostResult

    private val _questions = mutableStateListOf<Questions>()
    val questions: List<Questions> get() = _questions

    private val _currentQuestionIndex = mutableStateOf(0)
    val currentQuestionIndex: Int get() = _currentQuestionIndex.value

    private val _answers = mutableStateListOf<String>()

    fun getQuestion() {
        coroutineScope.launch {
            _fluencyBoostResult.value = FluencyBoostResult.Loading
            val maxRetries = 5
            var attempts = 0

            while (attempts < maxRetries) {
                val response = levelingTestUseCase.getQuestionSmartChallenges(Email(authData.email))

                if (response.isSuccess) {
                    val questionList = response.getOrNull().orEmpty()
                    _questions.clear()
                    _questions.addAll(questionList)
                    _fluencyBoostResult.value = FluencyBoostResult.Success
                    return@launch
                }

                val error = response.exceptionOrNull()

                if (error is TooManyRequestsException) {
                    _fluencyBoostResult.value = FluencyBoostResult.Generating
                    delay(20000L)
                    attempts++
                } else {
                    _fluencyBoostResult.value = FluencyBoostResult.Error(
                        message = error?.message ?: "Erro desconhecido"
                    )
                    return@launch
                }
            }
        }
    }

    private suspend fun submitAnswer(answer: String) {
        _answers.add(answer)
        if (_currentQuestionIndex.value < _questions.size - 1) {
            _currentQuestionIndex.value += 1
        } else {
            _fluencyBoostResult.value = FluencyBoostResult.Loading
            val response = levelingTestUseCase.submitAnswer(
                LevelingTestAnswers(
                    email = authData.email,
                    questions = _answers
                )
            )
            response.onSuccess {
                _fluencyBoostResult.value = FluencyBoostResult.Completed(it.response)
                userLocalDataSource.incrementFluencyBoostCompleted(authData.email)
            }.onFailure {
                _fluencyBoostResult.value = FluencyBoostResult.Error(message = it.message ?: "Err desconhecido")
            }
        }

    }

    fun onEvent(event: FluencyBoostEvent) {
        when (event) {
            is FluencyBoostEvent.GoToHome -> {
                val seconds = sessionTracker.endSession()
                navigateToHome(authData, seconds)
            }
            is FluencyBoostEvent.SubmitAnswer -> {
                coroutineScope.launch {
                    submitAnswer(event.answer)
                }
            }
        }

    }

}

sealed class FluencyBoostEvent {
    data object GoToHome : FluencyBoostEvent()
    data class SubmitAnswer(val answer: String) : FluencyBoostEvent()
}