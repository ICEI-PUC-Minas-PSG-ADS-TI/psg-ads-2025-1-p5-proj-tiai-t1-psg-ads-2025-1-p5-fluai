package org.example.project.ui.screens.splash

import com.arkivanov.decompose.ComponentContext

class SplashScreenComponent(
    componentContext: ComponentContext,
    private val onNavigateToAuthScreen: () -> Unit
): ComponentContext by componentContext{

    fun onEvent(event : SplashScreenEvent){
        when(event){
            SplashScreenEvent.GoToAuth -> onNavigateToAuthScreen()
        }
    }
}

sealed interface SplashScreenEvent {
    data object GoToAuth : SplashScreenEvent
}