package org.example.project.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.theme.Blue
import org.example.project.ui.theme.PoppinsTypography

@Composable
fun PrimaryButton(
    color: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    buttonText : String,
    enable : Boolean,
    onClick : () -> Unit
){
    val border = when(color){
        Color.White -> BorderStroke(0.5.dp, Blue)
        Blue -> null
        else -> null
    }

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enable,
        colors = ButtonDefaults.buttonColors(backgroundColor = color),
        border = border,
        shape = RoundedCornerShape(8.dp),
        content = {
            Text(
                text = buttonText,
                fontWeight = FontWeight.Bold,
                style = PoppinsTypography().button,
                fontFamily = FontFamily.Default,
                color = textColor
            )
        }
    )
}