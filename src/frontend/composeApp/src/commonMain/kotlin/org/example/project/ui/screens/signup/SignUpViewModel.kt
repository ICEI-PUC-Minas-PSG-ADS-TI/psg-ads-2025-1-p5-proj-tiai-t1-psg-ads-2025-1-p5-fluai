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
import org.example.project.domain.error.Result
import org.example.project.ui.extensions.coroutineScope
import org.example.project.ui.screens.signup.model.UserDataValidator
import org.example.project.domain.error.NameError
import org.example.project.domain.error.EmailError
import org.example.project.domain.error.PasswordError
import org.example.project.domain.error.mapErrorToFriendlyMessage


data class SignUpUiState(
    val textName: String = "",
    val textEmail: String = "",
    val textPassword: String = "",
    val textErrorName : String = "",
    val textErrorEmail : String = "",
    val textErrorPassword : String = "",
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
    private val userDataValidator: UserDataValidator,
    componentContext: ComponentContext,
    private val signUpUseCase: SignUpUseCase,
    private val onNavigateToAuthBySignUp: () -> Unit,
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
        when(val result = signUpUseCase.addUser(user)){
            is Result.Error -> {
                val errorMessage = mapErrorToFriendlyMessage(result.error)
                _signUpResult.emit(SignUpResult.Error(message = errorMessage))
            }
            is Result.Success -> {
                _signUpResult.emit(SignUpResult.Success)
            }
        }
    }

    fun onEvent(event: SignUpScreenEvent) {
        when (event) {
            SignUpScreenEvent.GoToAuthBySignUp -> onNavigateToAuthBySignUp()
            SignUpScreenEvent.GoToAuth -> onNavigateToAuth()
            is SignUpScreenEvent.SendUserData -> {
                scope.launch {
                    sendUserData()
                }
            }
        }
    }

    fun onTextNameChange(name: String) {
        _uiState.update {
            it.copy(
                textName = name,
                isErrorName = false,
                textErrorName = ""
            )
        }
        when(val result = userDataValidator.isValidName(name)){
            is Result.Error -> {
                when(result.error){
                    NameError.TOO_SHORT -> {
                        _uiState.update{
                            it.copy(
                                isErrorName = true,
                                textErrorName = "O nome deve ter pelo menos 3 caracteres."
                            )
                        }
                    }
                    NameError.NO_UPPERCASE -> {
                        _uiState.update{
                            it.copy(
                                isErrorName = true,
                                textErrorName = "O nome deve começar com letra maiúscula."
                            )
                        }
                    }
                }
            }
            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        isErrorName = result.data.not(),
                        textErrorName = ""
                    )
                }
            }
        }
    }

    fun onTextPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                textPassword = password,
                isErrorPassword = false,
                textErrorPassword = ""
            )
        }
        when(val result = userDataValidator.isValidPassword(password)){
            is Result.Error -> {
                when(result.error){
                    PasswordError.TOO_SHORT -> {
                        _uiState.update{
                            it.copy(
                                isErrorPassword = true,
                                textErrorPassword = "A senha deve ter pelo menos 6 caracteres."
                            )
                        }
                    }
                }
            }
            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        isErrorPassword = result.data.not(),
                        textErrorPassword = ""
                    )
                }
            }
        }
    }

    fun onTextEmailChange(email: String) {
        _uiState.update {
            it.copy(
                textEmail = email,
                isErrorEmail = false,
                textErrorEmail = ""
            )
        }
        when(val result = userDataValidator.isValidEmail(email)){
            is Result.Error -> {
                when(result.error){
                    EmailError.INVALID_EMAIL -> {
                        _uiState.update{
                            it.copy(
                                isErrorEmail = true,
                                textErrorEmail = "Email inválido"
                            )
                        }
                    }
                }
            }
            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        isErrorEmail = result.data.not(),
                        textErrorEmail = ""
                    )
                }
            }
        }
    }

}

sealed interface SignUpScreenEvent {
    data object GoToAuth : SignUpScreenEvent
    data object GoToAuthBySignUp : SignUpScreenEvent
    data object SendUserData : SignUpScreenEvent
}