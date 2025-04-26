package org.example.project.ui.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import frontend.composeapp.generated.resources.Res
import frontend.composeapp.generated.resources.auth_email_label_text
import frontend.composeapp.generated.resources.auth_password_label_text
import frontend.composeapp.generated.resources.error_feedback_button
import frontend.composeapp.generated.resources.error_feedback_title
import frontend.composeapp.generated.resources.icon_error
import frontend.composeapp.generated.resources.sign_up_name_label_text
import frontend.composeapp.generated.resources.signup_button_text
import frontend.composeapp.generated.resources.signup_subtitle
import frontend.composeapp.generated.resources.signup_text_button_login
import frontend.composeapp.generated.resources.signup_title
import frontend.composeapp.generated.resources.success_feedback
import frontend.composeapp.generated.resources.success_feedback_button_continue
import frontend.composeapp.generated.resources.success_feedback_subtitle
import frontend.composeapp.generated.resources.success_feedback_title
import org.example.project.theme.Blue
import org.example.project.theme.gray_darker
import org.example.project.ui.components.PrimaryButton
import org.example.project.ui.components.TextFieldComponent
import org.example.project.ui.screens.signup.model.FormCallbacks
import org.example.project.ui.theme.PoppinsTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    val isDisplayDialog = remember { mutableStateOf(false) }
    val isDisplayDialogError = remember { mutableStateOf(false)}
    val showCircularProgressBar = remember { mutableStateOf(false)}
    val errorMessage = remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.signUpResult.collect{ result ->
            when (result) {
                is SignUpResult.Success -> {
                    isDisplayDialog.value = true
                    showCircularProgressBar.value = false
                }

                is SignUpResult.Loading -> showCircularProgressBar.value = true

                is SignUpResult.Error -> {
                    showCircularProgressBar.value = false
                    isDisplayDialogError.value = true
                    errorMessage.value = result.message
                }
                else -> Unit
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isDisplayDialogError.value) {
            ErrorDialog(
                errorMessage = errorMessage.value,
                onDismiss = { isDisplayDialogError.value = false })
        }
        if (isDisplayDialog.value) {
            SuccessDialog(onAuthClick = {viewModel.onEvent(SignUpScreenEvent.GoToAuth)}, onDismiss = { isDisplayDialog.value = false })
        }
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignUpHeader()
            Spacer(modifier = Modifier.height(50.dp))
            SignUpForm(
                uiState = uiState,
                formCallbacks = FormCallbacks(
                    onClickNameChange = viewModel::onTextNameChange,
                    onClickEmailChange = viewModel::onTextEmailChange,
                    onClickPasswordChange = viewModel::onTextPasswordChange
                ),
                onClick = { viewModel.onEvent(SignUpScreenEvent.SendUserData)})
            SignUpFooter(onAuthClick = {viewModel.onEvent(SignUpScreenEvent.GoToAuth)})
        }
        LoadingComponent(showCircularProgressBar.value)
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
private fun SignUpForm(
    uiState: SignUpUiState,
    formCallbacks: FormCallbacks,
    onClick : () -> Unit
) {

    TextFieldComponent(
        stringResource(Res.string.sign_up_name_label_text),
        textFieldValue = uiState.textName,
        isError = uiState.isErrorName,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        visualTransformation = VisualTransformation.None,
        onChange = formCallbacks.onClickNameChange
    )

    TextFieldComponent(
        stringResource(Res.string.auth_email_label_text),
        textFieldValue = uiState.textEmail,
        isError = uiState.isErrorEmail,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        visualTransformation = VisualTransformation.None,
        onChange = formCallbacks.onClickEmailChange
    )

    TextFieldComponent(
        stringResource(Res.string.auth_password_label_text),
        textFieldValue = uiState.textPassword,
        isError = uiState.isErrorPassword,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation(),
        onChange = formCallbacks.onClickPasswordChange
    )

    PrimaryButton(
        color = Blue,
        modifier = Modifier.fillMaxWidth().height(70.dp).padding(top = 16.dp),
        buttonText = stringResource(Res.string.signup_button_text),
        textColor = Color.White,
        enable = uiState.isValid,
        onClick = onClick
    )
}

@Composable
fun ErrorDialog(errorMessage: String, onDismiss: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.5f))
        .zIndex(1f)
    ){
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(Res.drawable.icon_error),
                        contentDescription = "Error",
                        modifier = Modifier
                    )
                    Text(
                        text = stringResource(Res.string.error_feedback_title),
                        style = PoppinsTypography().subtitle2,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = errorMessage,
                        style = PoppinsTypography().caption,
                        color = Color.Gray,
                        maxLines = 1,
                        fontWeight = FontWeight.W300,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    PrimaryButton(
                        color = Blue,
                        modifier = Modifier.fillMaxWidth().height(70.dp).padding(top = 16.dp),
                        buttonText = stringResource(Res.string.error_feedback_button),
                        textColor = Color.White,
                        enable = true,
                        onClick = onDismiss
                    )
                }
            }
        }
    }
}


@Composable
fun SuccessDialog(onAuthClick: () -> Unit, onDismiss: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.5f))
        .zIndex(1f)
    ){
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                modifier = Modifier
                    .height(300.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(Res.drawable.success_feedback),
                        contentDescription = "Sucesso",
                        modifier = Modifier
                    )
                    Text(
                        text = stringResource(Res.string.success_feedback_title),
                        style = PoppinsTypography().subtitle2,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = stringResource(Res.string.success_feedback_subtitle),
                        style = PoppinsTypography().caption,
                        color = Color.Gray,
                        maxLines = 1,
                        fontWeight = FontWeight.W300,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    PrimaryButton(
                        color = Blue,
                        modifier = Modifier.fillMaxWidth().height(70.dp).padding(top = 16.dp),
                        buttonText = stringResource(Res.string.success_feedback_button_continue),
                        textColor = Color.White,
                        enable = true,
                        onClick = onAuthClick
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingComponent(isLoading : Boolean){
    if (isLoading){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray.copy(0.3f))
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent()
                    }
                }
            }
        ){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center),
                color = Blue,
            )
        }
    }
}
