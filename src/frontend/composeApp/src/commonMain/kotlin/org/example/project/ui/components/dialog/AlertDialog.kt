package org.example.project.ui.components.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import frontend.composeapp.generated.resources.Res
import frontend.composeapp.generated.resources.success_feedback
import frontend.composeapp.generated.resources.success_feedback_button_continue
import frontend.composeapp.generated.resources.success_feedback_title
import org.example.project.theme.Blue
import org.example.project.ui.components.PrimaryButton
import org.example.project.ui.theme.PoppinsTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AlertDialog(text : String, onDismiss : () -> Unit, onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black.copy(alpha = 0.5f))
        .zIndex(1f)
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    awaitPointerEvent()
                }
            }
        }
    ){
        Dialog(onDismiss) {
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
                        text = text,
                        style = PoppinsTypography().caption,
                        color = Color.Gray,
                        maxLines = 2,
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
                        onClick = onClick
                    )
                }
            }
        }
    }
}