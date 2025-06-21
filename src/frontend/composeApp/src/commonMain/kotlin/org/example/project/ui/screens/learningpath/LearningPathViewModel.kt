package org.example.project.ui.screens.learningpath

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.data.database.entities.UserEntity
import org.example.project.data.database.local.user.UserLocalDataSource
import org.example.project.domain.model.AuthData
import org.example.project.ui.extensions.coroutineScope

sealed class LearningPathResult {
    data object Loading : LearningPathResult()
    data object Success : LearningPathResult()
    data object Error : LearningPathResult()
}


class LearningPathViewModel(
    componentContext: ComponentContext,
    private val authData: AuthData,
    private val userLocalDataSource: UserLocalDataSource,
    private val onNavigateToLevelingTest : (AuthData) -> Unit,
    private val onNavigateToFluencyBoost : (AuthData) -> Unit
) : ComponentContext by componentContext{


    private val _loggedUserFlow = MutableStateFlow<UserEntity?>(null)
    val loggedUserFlow : StateFlow<UserEntity?> = _loggedUserFlow

    init {
        coroutineScope.launch {
            userLocalDataSource.observeLoggedUser().collect{
                _loggedUserFlow.value = it
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