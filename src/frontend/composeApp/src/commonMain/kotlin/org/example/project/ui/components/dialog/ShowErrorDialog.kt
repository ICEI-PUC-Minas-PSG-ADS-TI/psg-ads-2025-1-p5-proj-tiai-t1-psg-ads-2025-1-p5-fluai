package org.example.project.ui.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun showErrorDialog(isDisplayDialogError: MutableState<Boolean>, errorMessage: String) {
    if (isDisplayDialogError.value) {
        ErrorDialog(
            errorMessage = errorMessage,
            onDismiss = { isDisplayDialogError.value = false })
    }
}