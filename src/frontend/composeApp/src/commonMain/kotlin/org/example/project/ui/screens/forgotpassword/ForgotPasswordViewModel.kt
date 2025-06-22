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
                .onSuccess { response ->
                    val oobCode = extractOobCode(response.message)
                    println("Link recebido: ${response.message}")
                    println("oobCode extraído: $oobCode")
                    if (oobCode != null) {
                        println("Chamando navegação...")
                        onNavigateToResetLinkScreen(oobCode)
                    } else {
                        println("Erro: oobCode não encontrado no link.")
                    }
                }
                .onFailure { error ->
                    error.printStackTrace()
                    println("Erro ao enviar email de redefinição: ${error.message}")
                }
        }
    }

    private fun extractOobCode(link: String): String? {
        val regex = Regex("[?&]oobCode=([^&]+)")
        return regex.find(link)?.groupValues?.get(1)
    }
}