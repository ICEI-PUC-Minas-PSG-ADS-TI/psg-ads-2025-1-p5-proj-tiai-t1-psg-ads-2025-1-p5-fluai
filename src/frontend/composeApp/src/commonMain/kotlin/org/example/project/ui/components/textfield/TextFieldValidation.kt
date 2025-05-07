package org.example.project.ui.components.textfield

interface TextFieldValidator {
    fun validate(value : String) : TextFieldState
}

class NameValidator : TextFieldValidator {
    override fun validate(value: String): TextFieldState {
        return when{
            value.isEmpty() -> TextFieldState.DEFAULT
            value.length < 3 -> TextFieldState.ERROR(erroMessage = "No mínimo 3 caracteres")
            !value.matches(Regex("^[A-Z].*")) -> TextFieldState.ERROR("Primeira letra deve ser maiúscula")
            else -> TextFieldState.SUCCESS
        }
    }
}

class EmailValidator : TextFieldValidator {
    override fun validate(value: String): TextFieldState {
        return when{
            value.isEmpty() -> TextFieldState.DEFAULT
            !value.matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) -> TextFieldState.ERROR("E-mail inválido")
            else -> TextFieldState.SUCCESS
        }
    }
}

class PasswordValidator : TextFieldValidator {
    override fun validate(value: String): TextFieldState {
        return when{
            value.isEmpty() -> TextFieldState.DEFAULT
            value.length < 6 -> TextFieldState.ERROR(erroMessage = "A senha deve ter no mínimo 6 caracteres")
            else -> TextFieldState.SUCCESS
        }
    }
}