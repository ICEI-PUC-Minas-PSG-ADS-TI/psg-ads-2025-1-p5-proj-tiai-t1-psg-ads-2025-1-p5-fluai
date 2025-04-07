package org.example.project.ui.screens.splash

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import frontend.composeapp.generated.resources.Res
import frontend.composeapp.generated.resources.splash_login_button_text
import frontend.composeapp.generated.resources.splash_screen_banner
import frontend.composeapp.generated.resources.splash_signup_button_text
import frontend.composeapp.generated.resources.splash_subtitle
import frontend.composeapp.generated.resources.splash_title
import org.example.project.theme.Blue
import org.example.project.ui.theme.PoppinsTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
            ,horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SplashBanner()
            SplashTexts()
            Spacer(
                modifier = Modifier
                    .weight(1f)
            )
            SplashActionButtons()

        }
    }
}

@Composable
private fun SplashActionButtons() {
    Row(
        modifier = Modifier.padding(bottom = 60.dp)
    ) {
        Button(
            onClick = {},
            modifier = Modifier
                .width(180.dp)
                .height(50.dp)
                .padding(end = 16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Blue),
            shape = RoundedCornerShape(8.dp),
            content = {
                Text(
                    text = stringResource(Res.string.splash_signup_button_text),
                    fontWeight = FontWeight.Bold,
                    style = PoppinsTypography().button,
                    fontFamily = FontFamily.Default,
                    color = Color.White
                )
            }
        )
        Button(
            onClick = {},
            modifier = Modifier
                .height(50.dp)
                .width(180.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(0.5.dp, Blue),
            content = {
                Text(
                    text = stringResource(Res.string.splash_login_button_text),
                    style = PoppinsTypography().button,
                    fontWeight = FontWeight.Bold,
                    color = Blue
                )
            }
        )
    }
}

@Composable
private fun SplashTexts() {
    Text(
        text = stringResource(Res.string.splash_title),
        style = PoppinsTypography().h5,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 60.dp)
    )
    Text(
        text = stringResource(Res.string.splash_subtitle),
        style = PoppinsTypography().body1,
        fontWeight = FontWeight.W300,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(
            top = 32.dp,
            start = 32.dp,
            end = 32.dp
        )
    )
}

@Composable
private fun SplashBanner() {
    Image(
        painter = painterResource(Res.drawable.splash_screen_banner),
        contentDescription = "Login Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(top = 100.dp)
    )
}

