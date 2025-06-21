package org.example.project.ui.screens.useraccount

sealed interface UserAccountEvent{
    data object Logout : UserAccountEvent
}