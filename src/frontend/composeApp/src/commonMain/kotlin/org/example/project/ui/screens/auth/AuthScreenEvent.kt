package org.example.project.ui.screens.auth

sealed interface AuthScreenEvent {
    data object GoToSignUp : AuthScreenEvent
    data object GoToHome : AuthScreenEvent
    data class SignIn(val email: String, val password: String) : AuthScreenEvent
}