package org.example.project.ui.screens.learningpath

import com.arkivanov.decompose.ComponentContext
import org.example.project.domain.model.AuthData

class LearningPathViewModel(
    componentContext: ComponentContext,
    private val authData: AuthData,
    private val onNavigateToLevelingTest : (AuthData) -> Unit
) : ComponentContext by componentContext{
    fun onEvent(event : LearningPathEvent){
        when(event){
            is LearningPathEvent.GoToLevelingTest -> onNavigateToLevelingTest(authData)
        }
    }
}

sealed class LearningPathEvent{
    data object GoToLevelingTest : LearningPathEvent()
}