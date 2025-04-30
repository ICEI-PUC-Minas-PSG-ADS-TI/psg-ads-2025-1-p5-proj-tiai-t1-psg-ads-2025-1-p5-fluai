package org.example.project.ui.components.textfield

sealed class TextFieldState{
    data object SUCCESS : TextFieldState()
    data object DEFAULT : TextFieldState()
    data class ERROR(val erroMessage : String) : TextFieldState()
}
