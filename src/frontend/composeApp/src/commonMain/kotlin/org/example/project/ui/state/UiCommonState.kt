package org.example.project.ui.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class UiCommonState(
    val isDisplaySuccessDialog : MutableState<Boolean>,
    val isDisplayDialogError : MutableState<Boolean>,
    val showCircularProgressBar : MutableState<Boolean>,
    val errorMessage : MutableState<String>
)

@Composable
fun rememberUiCommonState() : UiCommonState{
    return remember {
        UiCommonState(
            isDisplaySuccessDialog = mutableStateOf(false),
            isDisplayDialogError = mutableStateOf(false),
            showCircularProgressBar = mutableStateOf(false),
            errorMessage = mutableStateOf("")
        )
    }
}