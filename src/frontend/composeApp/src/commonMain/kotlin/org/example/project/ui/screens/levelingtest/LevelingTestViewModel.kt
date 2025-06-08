package org.example.project.ui.screens.levelingtest

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.Questions
import org.example.project.domain.usecase.LevelingTestUseCase
import org.example.project.ui.extensions.coroutineScope
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.asSharedFlow

sealed class LevelingTestResult {
    data object Loading : LevelingTestResult()
    data object Success : LevelingTestResult()
    data class Error(val message: String) : LevelingTestResult()
}

class LevelingTestViewModel(
    componentContext: ComponentContext,
    private val levelingTestUseCase: LevelingTestUseCase
) : ComponentContext by componentContext{

    private val _levelingTestResult = MutableSharedFlow<LevelingTestResult>()
    val levelingTestResult = _levelingTestResult.asSharedFlow()

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
        levelingTestUseCase.getQuestion()
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
        }
    }

}

sealed class LevelingTestEvent {
    data class SubmitAnswer(val answer: String) : LevelingTestEvent()
    data object GetQuestions : LevelingTestEvent()
}





