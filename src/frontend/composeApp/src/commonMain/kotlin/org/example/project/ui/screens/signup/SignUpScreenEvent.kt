package org.example.project.ui.screens.signup

sealed interface SignUpScreenEvent {
    data object GoToAuth : SignUpScreenEvent
    data object GoToAuthBySignUp : SignUpScreenEvent
    data class SendUserData(val name: String, val email: String, val password: String) :
        SignUpScreenEvent
}