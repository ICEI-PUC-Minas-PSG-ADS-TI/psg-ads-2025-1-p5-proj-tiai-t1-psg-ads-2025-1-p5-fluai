package org.example.project.ui.screens.resetpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.theme.Blue
import org.example.project.ui.components.PrimaryButton
import org.example.project.ui.components.dialog.showErrorDialog
import org.example.project.ui.components.dialog.showSuccessDialog
import org.example.project.ui.components.loading.LoadingComponent
import org.example.project.ui.components.textfield.PasswordValidator
import org.example.project.ui.components.textfield.TextFieldState
import org.example.project.ui.state.rememberUiCommonState
import org.example.project.ui.theme.PoppinsTypography
import org.example.project.ui.utils.TextFieldGeneric
import org.example.project.ui.utils.rememberTextFieldState

@Composable
fun ResetPasswordScreen(viewModel: ResetPasswordViewModel) {
    val uiState = rememberUiCommonState()

    LaunchedEffect(Unit) {
        viewModel.state.collect { result ->
            when (result) {
                is ResetPasswordResult.Loading -> uiState.showCircularProgressBar.value = true
                is ResetPasswordResult.Success -> {
                    uiState.showCircularProgressBar.value = false
                    uiState.isDisplaySuccessDialog.value = true
                }
                is ResetPasswordResult.Error -> {
                    uiState.showCircularProgressBar.value = false
                    uiState.errorMessage.value = result.message
                    uiState.isDisplayDialogError.value = true
                }
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        LoadingComponent(uiState.showCircularProgressBar.value)
        showErrorDialog(uiState.isDisplayDialogError, uiState.errorMessage.value)
        showSuccessDialog(uiState.isDisplaySuccessDialog, "Senha redefinida com sucesso.") {
            viewModel.onPasswordResetSuccess()
        }

        val password = rememberTextFieldState(validators = PasswordValidator(), isPassword = true)
        val confirmPassword = rememberTextFieldState(validators = PasswordValidator(), isPassword = true)

        val isValid = remember {
            derivedStateOf {
                password.textState.value.text == confirmPassword.textState.value.text &&
                        password.fieldState.value is TextFieldState.SUCCESS
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Redefinir Senha", style = PoppinsTypography().h5)
            password.TextFieldGeneric(
                label = "Nova Senha",
                modifier = Modifier.fillMaxWidth()
            )
            confirmPassword.TextFieldGeneric(
                label = "Confirmar Nova Senha",
                modifier = Modifier.fillMaxWidth()
            )
            PrimaryButton(
                buttonText = "Confirmar",
                enable = isValid.value,
                onClick = {
                    viewModel.onEvent(ResetPasswordEvent.ConfirmReset(password.textState.value.text))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(top = 16.dp),
                color = Blue,
                textColor = Color.White,
            )
        }
    }
}
