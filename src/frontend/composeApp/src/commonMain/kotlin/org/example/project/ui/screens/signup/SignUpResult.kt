package org.example.project.ui.screens.signup

sealed class SignUpResult {
    data object Loading : SignUpResult()
    data object Success : SignUpResult()
    data class Error(val message: String) : SignUpResult()
}
