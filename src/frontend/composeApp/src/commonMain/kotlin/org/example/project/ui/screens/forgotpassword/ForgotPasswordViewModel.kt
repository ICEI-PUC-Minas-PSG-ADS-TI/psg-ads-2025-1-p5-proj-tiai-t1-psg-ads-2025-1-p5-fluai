package org.example.project.ui.screens.forgotpassword

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.ForgotPasswordUseCase
import org.example.project.ui.extensions.coroutineScope

class ForgotPasswordViewModel(
    componentContext: ComponentContext,
    private val onNavigateBack: () -> Unit,
    private val onNavigateToResetLinkScreen: (String) -> Unit,
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ComponentContext by componentContext {

    fun onEvent(event: ForgotPasswordEvent) {
        when (event) {
            is ForgotPasswordEvent.NavigateBack -> {
                onNavigateBack()
            }

            is ForgotPasswordEvent.SubmitEmail -> {
                coroutineScope.launch {
                    sendResetLink(event.email)
                }
            }
        }
    }

    private suspend fun sendResetLink(email: String) {
        val result = forgotPasswordUseCase.forgotPassword(email)
        result.onSuccess { response ->
            val oobCode = extractOobCode(response.message)
            println("Extracted oobCode: $oobCode")

            if (oobCode != null) {
                onNavigateToResetLinkScreen(oobCode)
            } else {
                println("ERROR: Failed to extract oobCode from link")
            }
        }.onFailure { error ->
            println("ERROR: ${error.message}")
        }
    }

    private fun extractOobCode(link: String): String? {
        val regex = Regex("[?&]oobCode=([^&]+)")
        val encodedCode = regex.find(link)?.groupValues?.get(1)
        return encodedCode?.let { decodeUrlEncoded(it) }
    }

    private fun decodeUrlEncoded(encoded: String): String {
        val result = StringBuilder()
        var index = 0

        while (index < encoded.length) {
            when (val char = encoded[index]) {
                '%' -> {
                    if (index + 2 < encoded.length) {
                        val hex = encoded.substring(index + 1, index + 3)
                        val charCode = hex.toIntOrNull(16) ?: 0
                        result.append(charCode.toChar())
                        index += 3
                        continue
                    }
                }
                '+' -> result.append(' ')
                else -> result.append(char)
            }
            index++
        }
        return result.toString()
    }
}