package org.example.project.ui.components.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun showSuccessDialog(isDisplaySuccessDialog : MutableState<Boolean>, message : String, onClick : () -> Unit){
    if(isDisplaySuccessDialog.value){
        SuccessDialog(text = message, onClick = onClick)
    }
}