package org.example.project.ui.screens.signup

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
import frontend.composeapp.generated.resources.auth_email_label_text
import frontend.composeapp.generated.resources.auth_password_label_text
import frontend.composeapp.generated.resources.signup_button_text
import frontend.composeapp.generated.resources.signup_subtitle
import frontend.composeapp.generated.resources.signup_text_button_login
import frontend.composeapp.generated.resources.signup_title
import org.example.project.theme.Blue
import org.example.project.theme.gray_darker
import org.example.project.ui.components.PrimaryButton
import org.example.project.ui.components.TextFieldComponent
import org.example.project.ui.screens.auth.AuthUiState
import org.example.project.ui.screens.auth.AuthViewModel
import org.example.project.ui.theme.PoppinsTypography
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel
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
            SignUpHeader()
            Spacer(modifier = Modifier.height(50.dp))
            SignUpForm(emailValue, uiState, viewModel, passwordValue)
            SignUpFooter(viewModel)
        }
    }
}

@Composable
private fun ColumnScope.SignUpFooter(viewModel: SignUpViewModel) {
    TextButton(modifier = Modifier.padding(top = 24.dp).align(Alignment.CenterHorizontally),
        onClick = {
            viewModel.onEvent(SignUpScreenEvent.GoToAuth)
        }
    ) {
        Text(
            text = stringResource(Res.string.signup_text_button_login),
            fontWeight = FontWeight.W400,
            color = gray_darker,
            textDecoration = TextDecoration.Underline,
            style = PoppinsTypography().body2,
            modifier = Modifier
        )
    }

}

@Composable
private fun ColumnScope.SignUpHeader() {
    Text(
        text = stringResource(Res.string.signup_title),
        style = PoppinsTypography().h4,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(top = 80.dp).align(Alignment.Start)
    )

    Text(
        text = stringResource(Res.string.signup_subtitle),
        style = PoppinsTypography().body2,
        color = Color.Gray,
        fontWeight = FontWeight.W200,
        modifier = Modifier.padding(top = 6.dp).align(Alignment.Start)
    )
}

@Composable
private fun SignUpForm(
    emailValue: String, uiState: SignUpUiState, viewModel: SignUpViewModel, passwordValue: String
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

    PrimaryButton(
        color = Blue,
        modifier = Modifier.fillMaxWidth().height(70.dp).padding(top = 16.dp),
        buttonText = stringResource(Res.string.signup_button_text),
        textColor = Color.White,
        onClick = {}
    )
}