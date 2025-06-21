package org.example.project.ui.screens.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import frontend.composeapp.generated.resources.Res
import frontend.composeapp.generated.resources.auth_button_text
import frontend.composeapp.generated.resources.auth_email_label_text
import frontend.composeapp.generated.resources.auth_password_label_text
import frontend.composeapp.generated.resources.auth_text_button_forget_password
import frontend.composeapp.generated.resources.auth_text_button_signup
import frontend.composeapp.generated.resources.auth_title_login
import frontend.composeapp.generated.resources.success_feedback_subtitle_auth
import org.example.project.theme.Blue
import org.example.project.theme.gray_darker
import org.example.project.ui.components.PrimaryButton
import org.example.project.ui.components.dialog.showErrorDialog
import org.example.project.ui.components.dialog.showSuccessDialog
import org.example.project.ui.components.loading.LoadingComponent
import org.example.project.ui.components.textfield.EmailValidator
import org.example.project.ui.components.textfield.PasswordValidator
import org.example.project.ui.components.textfield.TextFieldState
import org.example.project.ui.state.rememberUiCommonState
import org.example.project.ui.theme.PoppinsTypography
import org.example.project.ui.utils.TextFieldGeneric
import org.example.project.ui.utils.rememberTextFieldState
import org.jetbrains.compose.resources.stringResource


@Composable
fun AuthScreen(
    viewModel: AuthViewModel
) {

    val uiState = rememberUiCommonState()

    LaunchedEffect(Unit){
        viewModel.authStateResult.collect { result ->
            when(result){
                is AuthResult.Loading -> {
                    uiState.showCircularProgressBar.value = true
                }

                is AuthResult.Success -> {
                    uiState.showCircularProgressBar.value = false
                }
                is AuthResult.Error -> {
                    uiState.showCircularProgressBar.value = false
                    uiState.isDisplayDialogError.value = true
                    uiState.errorMessage.value = result.message
                }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        LoadingComponent(uiState.showCircularProgressBar.value)
        showErrorDialog(uiState.isDisplayDialogError, uiState.errorMessage.value)
        showSuccessDialog(uiState.isDisplaySuccessDialog, stringResource(Res.string.success_feedback_subtitle_auth)){
            viewModel.onEvent(AuthScreenEvent.GoToHome)
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthHeader()
            Spacer(modifier = Modifier.height(50.dp))
            LoginForm{ email, password ->
                viewModel.onEvent(event = AuthScreenEvent.SignIn(email, password))
            }
            AuthFooter {
                viewModel.onEvent(AuthScreenEvent.GoToSignUp)
            }
        }
    }
}

@Composable
private fun ColumnScope.AuthFooter(onClick : () -> Unit) {
    TextButton(
        modifier = Modifier.padding(top = 24.dp)
            .align(Alignment.CenterHorizontally),
        onClick = onClick
    ) {
        Text(
            text = stringResource(Res.string.auth_text_button_signup),
            fontWeight = FontWeight.W400,
            color = gray_darker,
            textDecoration = TextDecoration.Underline,
            style = PoppinsTypography().body2,
            modifier = Modifier,
        )
    }

}

@Composable
private fun ColumnScope.AuthHeader() {
    Text(
        text = stringResource(Res.string.auth_title_login),
        style = PoppinsTypography().h4,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 80.dp).align(Alignment.Start)
    )
}

@Composable
private fun ColumnScope.LoginForm(onClick : (email : String, password : String) -> Unit) {

    val email = rememberTextFieldState(validators = EmailValidator())
    val password = rememberTextFieldState(validators = PasswordValidator(), isPassword = true)


    val isValid = remember {
        derivedStateOf {
            email.fieldState.value is TextFieldState.SUCCESS && password.fieldState.value is TextFieldState.SUCCESS
        }
    }

    email.TextFieldGeneric(label = stringResource(Res.string.auth_email_label_text), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
    password.TextFieldGeneric(label = stringResource(Res.string.auth_password_label_text), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))
    TextButton(modifier = Modifier.Companion.align(Alignment.End), onClick = {}) {
        Text(
            text = stringResource(Res.string.auth_text_button_forget_password),
            fontWeight = FontWeight.W400,
            color = gray_darker,
            textDecoration = TextDecoration.Underline,
            style = PoppinsTypography().body2,
            modifier = Modifier
        )
    }

    PrimaryButton(
        color = Blue,
        modifier = Modifier.fillMaxWidth().height(70.dp).padding(top = 16.dp),
        buttonText = stringResource(Res.string.auth_button_text),
        textColor = Color.White,
        enable = isValid.value,
        onClick = {
            onClick.invoke(email.textState.value.text, password.textState.value.text)
        }
    )
}

