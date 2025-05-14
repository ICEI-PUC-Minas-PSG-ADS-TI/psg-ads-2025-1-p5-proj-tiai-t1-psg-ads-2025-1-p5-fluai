package org.example.project.ui.screens.auth

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.AuthUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.example.project.domain.model.AuthData
import org.example.project.ui.extensions.coroutineScope


class AuthViewModel(
    componentContext: ComponentContext,
    private val authUseCase: AuthUseCase,
    private val onNavigateToSignUp: () -> Unit,
    private val onNavigationToHome: (AuthData) -> Unit
) : ComponentContext by componentContext {

    private val _authStateResult = MutableSharedFlow<AuthResult>()
    val authStateResult = _authStateResult.asSharedFlow()


    private suspend fun sendUserData(email: String, password: String) {
        _authStateResult.emit(AuthResult.Loading)
        val result = authUseCase.authenticate(email = email, password = password)
        result
            .onSuccess {
                _authStateResult.emit(AuthResult.Success(it))
                onNavigationToHome.invoke(it)
            }
            .onFailure {
                _authStateResult.emit(AuthResult.Error(message = it.message ?: "Erro Desconhecido"))
            }
    }

    private val scope = coroutineScope

    fun onEvent(event: AuthScreenEvent) {
        when (event) {
            is AuthScreenEvent.GoToSignUp -> onNavigateToSignUp()
            is AuthScreenEvent.GoToHome -> onNavigateToSignUp()
            is AuthScreenEvent.SignIn -> {
                scope.launch {
                    sendUserData(email = event.email, password = event.password)
                }
            }
        }
    }
}