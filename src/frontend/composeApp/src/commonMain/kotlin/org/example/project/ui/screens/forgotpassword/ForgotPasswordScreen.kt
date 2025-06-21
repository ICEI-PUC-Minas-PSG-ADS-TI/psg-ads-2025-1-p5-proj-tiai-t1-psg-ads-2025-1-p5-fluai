package org.example.project.ui.screens.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.theme.Blue
import org.example.project.theme.gray
import org.example.project.ui.components.PrimaryButton

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel
) {
    Surface {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .size(26.dp)
                    .align(Alignment.TopEnd)
                    .background(Color.LightGray, CircleShape)
                    .clickable {
                        viewModel.onEvent(ForgotPasswordEvent.NavigateBack)
                    }
            ) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Sair",
                    tint = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(20.dp)
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(horizontal = 24.dp)
                    .padding(top = 100.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    text = "Esqueceu sua senha?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )

                Text("Digite seu endereço de email e enviaremos um link para alterar sua senha")

                var email by remember { mutableStateOf("") }

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text("Email")
                    },
                    keyboardOptions = KeyboardOptions.Default,
                    visualTransformation = VisualTransformation.None,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = gray,
                        unfocusedBorderColor = gray
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                PrimaryButton(
                    color = Blue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(top = 16.dp),
                    buttonText = "Enviar",
                    textColor = Color.White,
                    enable = true,
                    onClick = {
                        viewModel.onEvent(ForgotPasswordEvent.SubmitEmail(email))
                    }
                )
            }
        }
    }
}


