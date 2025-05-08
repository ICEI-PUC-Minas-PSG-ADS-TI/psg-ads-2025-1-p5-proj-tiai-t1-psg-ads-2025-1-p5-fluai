package org.example.project.ui.screens.auth

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import org.example.project.domain.model.UserLogin
import org.example.project.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.example.project.ui.extensions.coroutineScope


class AuthViewModel(
    componentContext: ComponentContext,
    private val authUseCase: AuthUseCase,
    private val onNavigateToSignUp: () -> Unit
) : ComponentContext by componentContext {

    private val _authStateResult = MutableSharedFlow<AuthResult>()
    val authStateResult = _authStateResult.asSharedFlow()


    private suspend fun sendUserData(email: String, password: String) {
        _authStateResult.emit(AuthResult.Loading)
        val user = UserLogin(email = email, password = password)
        val result = authUseCase.sendUserData(user)
        result
            .onSuccess {
                _authStateResult.emit(AuthResult.Success)
            }
            .onFailure {
                AuthResult.Error(message = it.message ?: "Erro desconhecido")
            }
    }

    private val scope = coroutineScope

    fun onEvent(event: AuthScreenEvent) {
        when (event) {
            is AuthScreenEvent.GoToSignUp -> onNavigateToSignUp()
            is AuthScreenEvent.GoToHome -> onNavigateToSignUp()
            is AuthScreenEvent.SendUserData -> {
                scope.launch {
                    sendUserData(email = event.email, password = event.password)
                }
            }
        }
    }
}