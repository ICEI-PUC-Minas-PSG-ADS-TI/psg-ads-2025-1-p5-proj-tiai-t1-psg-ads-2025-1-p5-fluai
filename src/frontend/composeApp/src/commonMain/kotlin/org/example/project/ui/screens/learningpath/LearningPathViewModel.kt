package org.example.project.ui.screens.learningpath

import com.arkivanov.decompose.ComponentContext

class LearningPathViewModel(
    componentContext: ComponentContext,
    private val onNavigateToLevelingTest : () -> Unit
) : ComponentContext by componentContext{
    fun onEvent(event : LearningPathEvent){
        when(event){
            is LearningPathEvent.GoToLevelingTest -> onNavigateToLevelingTest()
        }
    }
}

sealed class LearningPathEvent{
    data object GoToLevelingTest : LearningPathEvent()
}