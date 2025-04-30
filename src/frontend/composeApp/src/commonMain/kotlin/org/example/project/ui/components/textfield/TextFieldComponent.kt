package org.example.project.ui.components.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.example.project.theme.gray
import org.example.project.theme.gray_darker
import org.example.project.ui.theme.PoppinsTypography
import org.example.project.ui.utils.TextFieldBuilder


@Composable
fun TextFieldComponentGeneric(
    state: TextFieldBuilder,
    modifier: Modifier = Modifier,
    textLabel: String,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation,
    trailingIcon: @Composable (() -> Unit)? = null
){
    val errorMessage = when (val currentState = state.fieldState.value) {
        is TextFieldState.ERROR -> currentState.erroMessage
        else -> null
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(
            text = textLabel,
            fontWeight = FontWeight.W400,
            color = gray_darker,
            style = PoppinsTypography().body2,
            modifier = Modifier
                .align(Alignment.Start)
        )
        OutlinedTextField(
            modifier = modifier
                .width(400.dp)
                .padding(bottom = if (errorMessage == null) 24.dp else 0.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = gray,
                unfocusedBorderColor = gray
            ),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            shape = RoundedCornerShape(12.dp),
            value = state.textState.value,
            isError = errorMessage != null,
            trailingIcon = {
                trailingIcon?.invoke()
            },
            onValueChange = {
                state.textState.value = it
                state.validate()
            }
        )

        errorMessage?.let {
            Text(
                text = it,
                color = Color.Red,
                style = PoppinsTypography().caption,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
fun TextFieldComponent(
    modifier: Modifier,
    textLabel : String,
    textFieldValue : String,
    isError : Boolean,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation,
    onChange: (String) -> Unit
){
    Column(
    modifier = Modifier
    .fillMaxWidth()
    ){
        Text(
            text = textLabel,
            fontWeight = FontWeight.W400,
            color = gray_darker,
            style = PoppinsTypography().body2,
            modifier = Modifier
                .align(Alignment.Start)
        )
        OutlinedTextField(
            modifier = modifier
                .width(400.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = gray,
                unfocusedBorderColor = gray
            ),
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            shape = RoundedCornerShape(12.dp),
            value = textFieldValue,
            isError = isError,
            onValueChange = {
                onChange(it)
            }
        )
    }
}