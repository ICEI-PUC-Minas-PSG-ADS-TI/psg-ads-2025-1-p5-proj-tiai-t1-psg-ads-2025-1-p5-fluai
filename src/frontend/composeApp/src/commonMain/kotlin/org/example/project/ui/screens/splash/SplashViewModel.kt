package org.example.project.ui.screens.splash

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import org.example.project.domain.model.AuthData
import org.example.project.domain.repository.SessionRepository
import org.example.project.ui.extensions.coroutineScope

class SplashViewModel(
    componentContext: ComponentContext,
    private val onNavigateToAuthScreen: () -> Unit,
    private val onNavigateToSignUp: () -> Unit,
    private val onNavigateToHome: (AuthData) -> Unit,
    private val sessionRepository: SessionRepository,
) : ComponentContext by componentContext {

    init {
        checkSession()
    }

    private fun checkSession(){
        coroutineScope.launch {
            if (sessionRepository.checkSession()){
                sessionRepository.getCurrentUser()?.let(onNavigateToHome)
            }
        }
    }

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