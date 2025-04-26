package org.example.project.ui.screens.signup

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.User
import org.example.project.domain.usecase.SignUpUseCase
import org.example.project.ui.extensions.coroutineScope
import org.example.project.ui.utils.isValidEmail
import org.example.project.ui.utils.isValidName
import org.example.project.ui.utils.isValidPassword


data class SignUpUiState(
    val textName: String = "",
    val textEmail: String = "",
    val textPassword: String = "",
    val isErrorEmail: Boolean = false,
    val isErrorPassword: Boolean = false,
    val isErrorName: Boolean = false
) {
    val isValid: Boolean
        get() = textName.isNotEmpty() && textEmail.isNotEmpty() && textPassword.isNotEmpty() && !isErrorName && !isErrorEmail && !isErrorPassword
}

sealed class SignUpResult {
    data object Loading : SignUpResult()
    data object Success : SignUpResult()
    data class Error(val message: String) : SignUpResult()
}


class SignUpViewModel(
    componentContext: ComponentContext,
    private val signUpUseCase: SignUpUseCase,
    private val onNavigateToAuth: () -> Unit
) : ComponentContext by componentContext {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _signUpResult = MutableSharedFlow<SignUpResult?>()
    val signUpResult = _signUpResult.asSharedFlow()

    private val scope = coroutineScope


    private suspend fun sendUserData() {
        _signUpResult.emit(SignUpResult.Loading)
        val user = User(
            username = _uiState.value.textName,
            password = _uiState.value.textPassword,
            email = _uiState.value.textEmail
        )
        val result = signUpUseCase.addUser(user)
        result
            .onSuccess {
                _signUpResult.emit(SignUpResult.Success)
            }
            .onFailure {
                val error = result.exceptionOrNull()
                _signUpResult.emit(SignUpResult.Error("Erro interno: ${error?.message}"))
            }
    }

    fun onEvent(event: SignUpScreenEvent) {
        when (event) {
            SignUpScreenEvent.GoToAuth -> onNavigateToAuth()
            is SignUpScreenEvent.IsValidEmail -> isValidEmail(event.email)
            is SignUpScreenEvent.IsValidPassword -> isValidPassword(event.password)
            is SignUpScreenEvent.IsValidName -> isValidName(event.name)
            is SignUpScreenEvent.SendUserData -> {
                scope.launch {
                    sendUserData()
                }
            }
        }
    }

    fun onTextNameChange(name: String) {
        val isErrorName = isValidName(name)
        _uiState.update {
            it.copy(
                textName = name,
                isErrorName = isErrorName.not()
            )
        }
    }

    fun onTextPasswordChange(password: String) {
        val isPasswordValid = isValidPassword(password)
        _uiState.update {
            it.copy(
                textPassword = password,
                isErrorPassword = isPasswordValid.not()
            )
        }
    }

    fun onTextEmailChange(email: String) {
        val isValid = isValidEmail(email)
        _uiState.update {
            it.copy(
                textEmail = email,
                isErrorEmail = isValid.not()
            )
        }
    }
}

sealed interface SignUpScreenEvent {
    data object GoToAuth : SignUpScreenEvent
    data class IsValidEmail(val email: String) : SignUpScreenEvent
    data class IsValidName(val name: String) : SignUpScreenEvent
    data class IsValidPassword(val password: String) : SignUpScreenEvent
    data object SendUserData : SignUpScreenEvent
}