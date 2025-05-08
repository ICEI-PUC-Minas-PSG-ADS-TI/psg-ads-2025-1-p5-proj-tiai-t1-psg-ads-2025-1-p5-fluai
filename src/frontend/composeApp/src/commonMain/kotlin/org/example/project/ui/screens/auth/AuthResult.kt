package org.example.project.ui.screens.auth

sealed class AuthResult {
    data object Success : AuthResult()
    data object Loading : AuthResult()
    data class Error(val message: String) : AuthResult()
}