package org.example.project.ui.screens.splash

import com.arkivanov.decompose.ComponentContext


class SplashViewModel(
    componentContext: ComponentContext,
    private val onNavigateToAuthScreen: () -> Unit,
    private val onNavigateToSignUp: () -> Unit
): ComponentContext by componentContext{

    fun onEvent(event : SplashScreenEvent){
        when(event){
            SplashScreenEvent.GoToAuth -> onNavigateToAuthScreen()
            SplashScreenEvent.GoToSignUp -> onNavigateToSignUp()
        }
    }
}

sealed interface SplashScreenEvent {
    data object GoToAuth : SplashScreenEvent
    data object GoToSignUp : SplashScreenEvent
}