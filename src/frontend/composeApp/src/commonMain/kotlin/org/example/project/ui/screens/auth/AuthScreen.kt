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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import frontend.composeapp.generated.resources.Res
import frontend.composeapp.generated.resources.auth_button_text
import frontend.composeapp.generated.resources.auth_email_label_text
import frontend.composeapp.generated.resources.auth_password_label_text
import frontend.composeapp.generated.resources.auth_text_button_forget_password
import frontend.composeapp.generated.resources.auth_text_button_signup
import frontend.composeapp.generated.resources.auth_title_login
import org.example.project.theme.Blue
import org.example.project.theme.gray_darker
import org.example.project.ui.components.PrimaryButton
import org.example.project.ui.components.TextFieldComponent
import org.example.project.ui.theme.PoppinsTypography
import org.jetbrains.compose.resources.stringResource



@Composable
fun AuthScreen(
    viewModel: AuthViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    val emailValue = uiState.textEmail
    val passwordValue = uiState.textPassword

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AuthHeader()
            Spacer(modifier = Modifier.height(50.dp))
            LoginForm(emailValue, uiState, viewModel, passwordValue)
            AuthFooter(viewModel)
        }
    }
}

@Composable
private fun ColumnScope.AuthFooter(component: AuthViewModel) {
    TextButton(
        modifier = Modifier.padding(top = 24.dp)
            .align(Alignment.CenterHorizontally),
        onClick = {
            component.onEvent(AuthScreenEvent.GoToSignUp)
        }
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
private fun ColumnScope.LoginForm(
    emailValue: String, uiState: AuthUiState, viewModel: AuthViewModel, passwordValue: String
) {
    TextFieldComponent(
        stringResource(Res.string.auth_email_label_text),
        textFieldValue = emailValue,
        isError = uiState.isErrorEmail,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        visualTransformation = VisualTransformation.None,
        onChange = viewModel::onTextEmailChange
    )

    TextFieldComponent(
        stringResource(Res.string.auth_password_label_text),
        textFieldValue = passwordValue,
        isError = uiState.isErrorPassword,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        onChange = viewModel::onTextPasswordChange
    )

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
        enable = true,
        onClick = {}
    )
}

