package org.example.project.ui.screens.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AuthUiState(
    val isLoading: Boolean = false,
    val textEmail: String = "",
    val textPassword: String = "",
    val isErrorEmail: Boolean = false,
    val isErrorPassword: Boolean = false,
    val loginResult: LoginResult? = null,
    val goToHome: Boolean = false
)

sealed class LoginResult(message: String) {
    data object Success : LoginResult(message = "Sucesso!")
    data object Error : LoginResult(message = "Error")
}

class AuthViewModel : ViewModel() {
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
}

private fun isValidEmail(email: String): Boolean {
    val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
    return email.matches(emailRegex)
}

private fun isValidPassword(password: String): Boolean {
    return password.length > 3
}