package org.example.project.ui.screens.resetpassword

sealed class ResetPasswordResult {
    data object Loading : ResetPasswordResult()
    data object Success : ResetPasswordResult()
    data class Error(val message: String) : ResetPasswordResult()
}