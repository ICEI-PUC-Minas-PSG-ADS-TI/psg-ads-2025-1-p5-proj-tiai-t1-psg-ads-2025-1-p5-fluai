package org.example.project.ui.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun showAlertDialog(isDisplayDialogError: MutableState<Boolean>, message: String, onClick : () -> Unit) {
    if (isDisplayDialogError.value) {
        AlertDialog(
            text = message,
            onDismiss = { isDisplayDialogError.value = false },
            onClick = onClick
        )
    }
}