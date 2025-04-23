package org.example.project.ui.screens.signup

import com.arkivanov.decompose.ComponentContext
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.io.IOException
import org.example.project.data.utils.AppError
import org.example.project.domain.model.User
import org.example.project.domain.usecase.SignUpUseCase


data class SignUpUiState(
    val textName: String = "",
    val textEmail: String = "",
    val textPassword: String = "",
    val isErrorEmail: Boolean = false,
    val isErrorPassword: Boolean = false,
    val isErrorName: Boolean = false
)

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


    suspend fun sendUserData(name: String, email: String, password: String) {
        _signUpResult.emit(SignUpResult.Loading)
            val user = User(username = name, password = password, email = email)
            val result = signUpUseCase.addUser(user)
            if(result.isSuccess){
                _signUpResult.emit(SignUpResult.Success)
            }
            else {
                val error = result.exceptionOrNull()
                _signUpResult.emit(SignUpResult.Error("Erro interno: ${error?.message}"))
            }

    }


    fun isFieldsValid() =
        _uiState.value.isErrorEmail || _uiState.value.textEmail.isEmpty() || _uiState.value.isErrorPassword || _uiState.value.textPassword.isEmpty() || _uiState.value.textName.isEmpty()

    fun onEvent(event: SignUpScreenEvent) {
        when (event) {
            SignUpScreenEvent.GoToAuth -> onNavigateToAuth()
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

private fun isValidName(name: String): Boolean {
    val nameRegex = "^[A-ZÀ-Ÿ][a-zà-ÿ]+(?: [A-ZÀ-Ÿ][a-zà-ÿ]+)*$".toRegex()
    return name.matches(nameRegex)
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