package org.example.project.ui.screens.resetpassword

import com.arkivanov.decompose.ComponentContext

class ResetPasswordViewModel(
    componentContext: ComponentContext,
    val resetLink: String,
    val onNavigateBackToLogin: () -> Unit,
): ComponentContext by componentContext {
}