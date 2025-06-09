package org.example.project.ui.screens.auth

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AuthUiState(
    val isLoading: Boolean = false,
    val textEmail: String = "",
    val textPassword: String = "",
    val isErrorEmail: Boolean = false,
    val isErrorPassword: Boolean = false,
    val loginResult: AuthResult? = null,
    val goToHome: Boolean = false
)

sealed class AuthResult(message: String) {
    data object Success : AuthResult(message = "Sucesso!")
    data object Error : AuthResult(message = "Error")
}


class AuthViewModel(
    componentContext: ComponentContext,
    private val onNavigateToSignUp : () -> Unit,
    private val onNavigateToForgotPassword : () -> Unit
) : ComponentContext by componentContext{

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    fun onTextPasswordChange(password : String){
        val isPasswordValid = isValidPassword(password)
        _uiState.update{
            it.copy(
                textPassword = password,
                isErrorPassword = isPasswordValid.not()
            )
        }
    }

    fun onTextEmailChange(email: String) {
        val isEmailValid = isValidEmail(email)
        _uiState.update {
            it.copy(
                textEmail = email,
                isErrorEmail = isEmailValid.not()
            )
        }

    }

    fun onEvent(event : AuthScreenEvent){
        when(event){
            AuthScreenEvent.GoToSignUp -> onNavigateToSignUp()
            AuthScreenEvent.GoToForgotPassword -> onNavigateToForgotPassword()
        }
    }
}

private fun isValidEmail(email: String): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}

private fun isValidPassword(password: String): Boolean {
    return password.length > 3
}

sealed interface AuthScreenEvent {
    data object GoToSignUp : AuthScreenEvent
    data object GoToForgotPassword : AuthScreenEvent
}