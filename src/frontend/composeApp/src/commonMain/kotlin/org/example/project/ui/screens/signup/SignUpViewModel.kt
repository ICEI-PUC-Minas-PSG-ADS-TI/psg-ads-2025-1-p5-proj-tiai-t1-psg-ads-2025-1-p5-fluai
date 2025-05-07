package org.example.project.ui.screens.signup

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.User
import org.example.project.domain.usecase.SignUpUseCase
import org.example.project.ui.extensions.coroutineScope

class SignUpViewModel(
    componentContext: ComponentContext,
    private val signUpUseCase: SignUpUseCase,
    private val onNavigateToAuthBySignUp: () -> Unit,
    private val onNavigateToAuth: () -> Unit
) : ComponentContext by componentContext {

    private val _signUpResult = MutableSharedFlow<SignUpResult?>()
    val signUpResult = _signUpResult.asSharedFlow()

    private val scope = coroutineScope

    private suspend fun sendUserData(name: String, email: String, password: String) {
        _signUpResult.emit(SignUpResult.Loading)

        val user = User(
            username = name,
            password = email,
            email = password
        )

        val result = signUpUseCase.addUser(user)

        result
            .onSuccess {
                _signUpResult.emit(SignUpResult.Success)
            }
            .onFailure {
                _signUpResult.emit(SignUpResult.Error(it.message ?: "Erro desconhecido"))
            }
    }

    fun onEvent(event: SignUpScreenEvent) {
        when (event) {
            is SignUpScreenEvent.GoToAuthBySignUp -> onNavigateToAuthBySignUp()
            is SignUpScreenEvent.GoToAuth -> onNavigateToAuth()
            is SignUpScreenEvent.SendUserData -> {
                scope.launch {
                    sendUserData(
                        name = event.name,
                        email = event.email,
                        password = event.password
                    )
                }
            }
        }
    }
}
