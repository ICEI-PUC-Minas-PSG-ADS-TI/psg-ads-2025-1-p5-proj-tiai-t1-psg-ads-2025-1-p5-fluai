package org.example.project.ui.screens.auth

import org.example.project.domain.model.AuthData

sealed class AuthResult {
    data class Success(val authData: AuthData) : AuthResult()
    data object Loading : AuthResult()
    data class Error(val message: String) : AuthResult()
}