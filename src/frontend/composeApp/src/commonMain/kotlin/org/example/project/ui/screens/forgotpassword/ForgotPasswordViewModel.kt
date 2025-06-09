package org.example.project.ui.screens.forgotpassword

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.ForgotPasswordUseCase

class ForgotPasswordViewModel(
    componentContext: ComponentContext,
    private val onNavigateBack: () -> Unit,
    private val onNavigateToResetLinkScreen: (String) -> Unit,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ComponentContext by componentContext {

    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.NavigateBack -> {
                onNavigateBack()
            }

            is ForgotPasswordEvent.SubmitEmail -> {
                sendResetLink(event.email)
            }
        }
    }

    private fun sendResetLink(email: String) {
        viewModelScope.launch {
            val result = forgotPasswordUseCase(email)
            result
                .onSuccess { resetLink ->
                    onNavigateToResetLinkScreen(resetLink)
                }
                .onFailure { error ->
                    println("Erro ao enviar email de redefinição: ${error.message}")
                }
        }
    }
}