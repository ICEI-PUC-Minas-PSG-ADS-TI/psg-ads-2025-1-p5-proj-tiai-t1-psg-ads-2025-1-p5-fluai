package org.example.project.ui.utils

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import org.example.project.ui.components.textfield.TextFieldComponentGeneric
import org.example.project.ui.components.textfield.TextFieldState
import org.example.project.ui.components.textfield.TextFieldValidator

@Composable
fun rememberTextFieldState(
    fieldState: MutableState<TextFieldState> = mutableStateOf(TextFieldState.DEFAULT),
    textState : MutableState<TextFieldValue> = remember { mutableStateOf(TextFieldValue()) },
    validators : TextFieldValidator
) : TextFieldBuilder = remember { TextFieldBuilder(fieldState = fieldState, textState = textState, validators = validators) }

data class TextFieldBuilder(
    val fieldState : MutableState<TextFieldState> = mutableStateOf(TextFieldState.DEFAULT),
    val textState: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue()),
    val validators : TextFieldValidator
){
    fun validate(){
        val validateResult = validators.validate(textState.value.text)
        fieldState.value = validateResult
    }
}


@Composable
fun TextFieldBuilder.TextFieldGeneric(
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextFieldComponentGeneric(
        state = this,
        textLabel = label,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon
    )
}

