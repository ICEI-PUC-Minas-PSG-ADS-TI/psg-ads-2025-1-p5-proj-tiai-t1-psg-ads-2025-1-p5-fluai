package org.example.project.ui.screens.signup.model

data class FormCallbacks(
    val onClickNameChange: (String) -> Unit,
    val onClickEmailChange: (String) -> Unit,
    val onClickPasswordChange: (String) -> Unit
)