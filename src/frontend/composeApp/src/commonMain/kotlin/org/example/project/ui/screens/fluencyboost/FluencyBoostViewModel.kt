package org.example.project.ui.screens.fluencyboost

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.database.local.user.UserLocalDataSource
import org.example.project.domain.model.AuthData
import org.example.project.domain.model.Email
import org.example.project.domain.model.LevelingTestAnswers
import org.example.project.domain.model.Questions
import org.example.project.domain.usecase.HomeUseCase
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
    private val homeUseCase: HomeUseCase,
    private val userLocalDataSource: UserLocalDataSource,
    private val levelingTestUseCase: LevelingTestUseCase,
    val navigateToHome: (AuthData) -> Unit
) : ComponentContext by componentContext {

    private val _fluencyBoostResult = MutableStateFlow<FluencyBoostResult?>(FluencyBoostResult.Loading)
    val fluencyBoostResult : MutableStateFlow<FluencyBoostResult?> = _fluencyBoostResult

    private val _questions = mutableStateListOf<Questions>()
    val questions: List<Questions> get() = _questions

    private val _currentQuestionIndex = mutableStateOf(0)
    val currentQuestionIndex: Int get() = _currentQuestionIndex.value

    private val _answers = mutableStateListOf<String>()


    private suspend fun preloadQuestions() {
        _fluencyBoostResult.value = FluencyBoostResult.Generating
        val response = homeUseCase.preloadQuestions(Email(authData.email))
        response.onSuccess {
            _fluencyBoostResult.value = FluencyBoostResult.Success
        }.onFailure {
            _fluencyBoostResult.value =
                FluencyBoostResult.Error(message = it.message ?: "Err desconhecido")
        }
    }


    fun getQuestion() {
        coroutineScope.launch {
            preloadQuestions()
            if(fluencyBoostResult.value is FluencyBoostResult.Error) return@launch
            val response = levelingTestUseCase.getQuestionSmartChallenges(Email(authData.email))
            response.onSuccess { questionList ->
                println(questionList)
                _questions.clear()
                _questions.addAll(questionList)
                _fluencyBoostResult.value = FluencyBoostResult.Success
            }.onFailure {
                _fluencyBoostResult.value = FluencyBoostResult.Error(message = it.message ?: "Err desconhecido")
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
            is FluencyBoostEvent.GoToHome -> navigateToHome(authData)
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