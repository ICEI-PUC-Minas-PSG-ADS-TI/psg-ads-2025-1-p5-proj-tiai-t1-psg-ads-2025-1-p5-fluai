package org.example.project.ui.screens.levelingtest

import androidx.compose.runtime.mutableStateListOf
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.Questions
import org.example.project.domain.usecase.LevelingTestUseCase
import org.example.project.ui.extensions.coroutineScope
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.asSharedFlow
import org.example.project.domain.model.AuthData
import org.example.project.domain.model.LevelingTestAnswers

sealed class LevelingTestResult {
    data object Loading : LevelingTestResult()
    data object Success : LevelingTestResult()
    data class Completed(val message: String) : LevelingTestResult()
    data class Error(val message: String) : LevelingTestResult()
}

class LevelingTestViewModel(
    componentContext: ComponentContext,
    private val levelingTestUseCase: LevelingTestUseCase,
    private val authData: AuthData,
    private val onNavigateToHome : (AuthData) -> Unit
) : ComponentContext by componentContext{

    private val _levelingTestResult = MutableSharedFlow<LevelingTestResult?>()
    val levelingTestResult = _levelingTestResult.asSharedFlow()

    private val _answers = mutableStateListOf<String>()

    private val _questions =  mutableStateListOf<Questions>()
    val questions : List<Questions> get() = _questions

    private val _currentQuestionIndex = mutableStateOf(0)
    val currentQuestionIndex: Int get() = _currentQuestionIndex.value


    suspend fun getQuestion(){
         _levelingTestResult.emit(LevelingTestResult.Loading)
         val response = levelingTestUseCase.getQuestion()
         response.onSuccess { questionList ->
             println(questionList)
             _questions.clear()
             _questions.addAll(questionList)
             _levelingTestResult.emit(LevelingTestResult.Success)
         }.onFailure {
             _levelingTestResult.emit(LevelingTestResult.Error(it.message ?: "Erro desconhecido"))
         }
    }

    private suspend fun submitAnswer(answer: String){
        _answers.add(answer)
        if (_currentQuestionIndex.value < _questions.size - 1){
            _currentQuestionIndex.value += 1
        }else{
            _levelingTestResult.emit(LevelingTestResult.Loading)
            val formattedAnswer = LevelingTestAnswers(email = authData.email, questions = _answers)
            val response = levelingTestUseCase.submitAnswer(formattedAnswer)
            response.onSuccess {
                _levelingTestResult.emit(LevelingTestResult.Completed(it.response))
            }.onFailure {
                _levelingTestResult.emit(LevelingTestResult.Error(it.message ?: "Erro desconhecido"))
            }

        }
    }

    fun onEvent(event : LevelingTestEvent){
        when(event){
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





