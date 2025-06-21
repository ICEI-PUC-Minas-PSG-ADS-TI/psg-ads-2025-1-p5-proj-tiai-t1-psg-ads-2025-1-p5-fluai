package org.example.project.ui.screens.resetpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import org.example.project.theme.Blue
import org.example.project.ui.components.PrimaryButton

@Composable
fun ResetPasswordScreen(viewModel: ResetPasswordViewModel) {
    ResetPasswordScreen(
        resetLink = viewModel.resetLink,
        onBackToLogin = viewModel.onNavigateBackToLogin
    )
}
@Composable
fun ResetPasswordScreen(
    resetLink: String,
    onBackToLogin: () -> Unit
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Redefinição de senha", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Clique no botão abaixo para redefinir sua senha em seu navegador.")
        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(top = 16.dp),
            buttonText = "Clique para redefinir",
            onClick = {
                uriHandler.openUri(resetLink)
            },
            color = Blue,
            textColor = Color.White,
            enable = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(onClick = onBackToLogin) {
            Text("Voltar para login")
        }
    }
}