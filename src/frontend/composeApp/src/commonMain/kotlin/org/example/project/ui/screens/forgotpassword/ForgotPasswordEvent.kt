package org.example.project.ui.screens.forgotpassword

sealed interface ForgotPasswordEvent {
    data object NavigateBack : ForgotPasswordEvent
    data class SubmitEmail(val email: String) : ForgotPasswordEvent
}