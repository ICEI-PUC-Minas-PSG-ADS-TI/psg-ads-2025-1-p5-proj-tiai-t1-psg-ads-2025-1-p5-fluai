package org.example.project.ui.screens.signup

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class SignUpUiState(
    val isLoading: Boolean = false,
    val textEmail: String = "",
    val textPassword: String = "",
    val isErrorEmail: Boolean = false,
    val isErrorPassword: Boolean = false,
)


class SignUpViewModel(
    componentContext: ComponentContext,
    private val onNavigateToAuth : () -> Unit
) : ComponentContext by componentContext{

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event : SignUpScreenEvent){
        when(event){
            SignUpScreenEvent.GoToAuth -> onNavigateToAuth()
        }
    }

    fun onTextPasswordChange(password : String){
        val isPasswordValid = isValidPassword(password)
        _uiState.update{
            it.copy(
                textPassword = password,
                isErrorPassword = isPasswordValid.not()
            )
        }
    }

    fun onTextEmailChange(email : String){
        val isValid = isValidEmail(email)
        _uiState.update {
            it.copy(
                textEmail = email,
                isErrorEmail = isValid.not()
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

sealed interface SignUpScreenEvent {
    data object GoToAuth : SignUpScreenEvent
}