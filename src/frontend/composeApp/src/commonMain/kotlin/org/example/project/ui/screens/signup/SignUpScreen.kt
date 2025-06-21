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
import frontend.composeapp.generated.resources.sign_up_email_label_text
import frontend.composeapp.generated.resources.sign_up_name_label_text
import frontend.composeapp.generated.resources.sign_up_password_label_text
import frontend.composeapp.generated.resources.signup_button_text
import frontend.composeapp.generated.resources.signup_subtitle
import frontend.composeapp.generated.resources.signup_text_button_login
import frontend.composeapp.generated.resources.signup_title
import frontend.composeapp.generated.resources.success_feedback_subtitle
import org.example.project.theme.Blue
import org.example.project.theme.gray_darker
import org.example.project.ui.components.PrimaryButton
import org.example.project.ui.components.dialog.showErrorDialog
import org.example.project.ui.components.dialog.showSuccessDialog
import org.example.project.ui.components.loading.LoadingComponent
import org.example.project.ui.theme.PoppinsTypography
import org.example.project.ui.components.textfield.EmailValidator
import org.example.project.ui.components.textfield.NameValidator
import org.example.project.ui.components.textfield.PasswordValidator
import org.example.project.ui.utils.TextFieldGeneric
import org.example.project.ui.components.textfield.TextFieldState
import org.example.project.ui.state.rememberUiCommonState
import org.example.project.ui.utils.rememberTextFieldState
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel
) {

    val uiState = rememberUiCommonState()

    LaunchedEffect(Unit) {
        viewModel.signUpResult.collect{ result ->
            when (result) {
                is SignUpResult.Success -> {
                    uiState.isDisplaySuccessDialog.value = true
                    uiState.showCircularProgressBar.value = false
                }

                is SignUpResult.Loading -> uiState.showCircularProgressBar.value = true

                is SignUpResult.Error -> {
                    uiState.showCircularProgressBar.value = false
                    uiState.isDisplayDialogError.value = true
                    uiState.errorMessage.value = result.message
                }
                else -> Unit
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        showErrorDialog(isDisplayDialogError = uiState.isDisplayDialogError, errorMessage = uiState.errorMessage.value)
        showSuccessDialog(isDisplaySuccessDialog = uiState.isDisplaySuccessDialog, message = stringResource(Res.string.success_feedback_subtitle)){
            viewModel.onEvent(SignUpScreenEvent.GoToAuthBySignUp)
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignUpHeader()
            Spacer(modifier = Modifier.height(50.dp))
            SignUpForm{ name, email, password -> viewModel.onEvent(SignUpScreenEvent.SendUserData(name = name, email = email, password = password)) }
            SignUpFooter(onAuthClick = {viewModel.onEvent(SignUpScreenEvent.GoToAuth)})
        }
        LoadingComponent(uiState.showCircularProgressBar.value)
    }

}

@Composable
private fun ColumnScope.SignUpFooter(onAuthClick : () -> Unit) {
    TextButton(modifier = Modifier.padding(top = 24.dp).align(Alignment.CenterHorizontally),
        onClick = onAuthClick
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
private fun SignUpForm(onClick: (name: String, email: String, password: String) -> Unit ) {

    val name = rememberTextFieldState(validators = NameValidator())
    val email = rememberTextFieldState(validators = EmailValidator())
    val password = rememberTextFieldState(validators = PasswordValidator(), isPassword = true)


    name.TextFieldGeneric(label = stringResource(Res.string.sign_up_name_label_text))
    email.TextFieldGeneric(label = stringResource(Res.string.sign_up_email_label_text), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))
    password.TextFieldGeneric(label = stringResource(Res.string.sign_up_password_label_text), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password))

    val isFormValid = remember {
        derivedStateOf {
            name.fieldState.value is TextFieldState.SUCCESS && email.fieldState.value is TextFieldState.SUCCESS && password.fieldState.value is TextFieldState.SUCCESS
        }
    }

    PrimaryButton(
        color = Blue,
        modifier = Modifier.fillMaxWidth().height(70.dp).padding(top = 16.dp),
        buttonText = stringResource(Res.string.signup_button_text),
        textColor = Color.White,
        enable = isFormValid.value,
        onClick = {
            onClick(name.textState.value.text,password.textState.value.text,email.textState.value.text)
        }
    )
}