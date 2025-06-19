package org.example.project.ui.screens.learningpath

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.AuthData
import org.example.project.domain.model.Email
import org.example.project.domain.usecase.HomeUseCase
import org.example.project.ui.extensions.coroutineScope

sealed class LearningPathResult {
    data object Loading : LearningPathResult()
    data object Success : LearningPathResult()
    data object Error : LearningPathResult()
}


class LearningPathViewModel(
    componentContext: ComponentContext,
    private val authData: AuthData,
    private val homeUseCase: HomeUseCase,
    private val onNavigateToLevelingTest : (AuthData) -> Unit,
    private val onNavigateToFluencyBoost : (AuthData) -> Unit
) : ComponentContext by componentContext{

    private val _learningPath = MutableStateFlow<LearningPathResult>(LearningPathResult.Loading)
    val learningPath : StateFlow<LearningPathResult> = _learningPath

    init {
        preloadQuestions()
    }


    private fun preloadQuestions(){
        coroutineScope.launch {
            _learningPath.value = LearningPathResult.Loading
            val response = homeUseCase.preloadQuestions(Email(authData.email))
            response.onSuccess {
               _learningPath.value = LearningPathResult.Success
            }.onFailure {
                _learningPath.value = LearningPathResult.Error
            }
        }
    }

    fun onEvent(event : LearningPathEvent){
        when(event){
            is LearningPathEvent.GoToLevelingTest -> onNavigateToLevelingTest(authData)
            is LearningPathEvent.GoToFluencyBoost -> onNavigateToFluencyBoost(authData)
        }
    }
}

sealed class LearningPathEvent{
    data object GoToLevelingTest : LearningPathEvent()
    data object GoToFluencyBoost : LearningPathEvent()
}