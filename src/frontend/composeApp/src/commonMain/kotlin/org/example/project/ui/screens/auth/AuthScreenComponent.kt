package org.example.project.ui.screens.auth

import com.arkivanov.decompose.ComponentContext

class AuthScreenComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext{

    private val viewModel = AuthViewModel()

    fun onEmailChanged(email: String) = viewModel.onTextEmailChange(email)
    fun onPasswordChange(password : String) = viewModel.onTextPasswordChange(password)

}