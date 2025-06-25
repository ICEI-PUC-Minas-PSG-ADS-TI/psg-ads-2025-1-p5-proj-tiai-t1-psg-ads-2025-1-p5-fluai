package org.example.project.ui.screens.resetpassword

sealed class ResetPasswordEvent {
    data class ConfirmReset(val newPassword: String) : ResetPasswordEvent()
}