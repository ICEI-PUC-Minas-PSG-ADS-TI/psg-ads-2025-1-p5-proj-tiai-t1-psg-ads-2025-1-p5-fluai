package org.example.project.ui.screens.signup.model

import org.example.project.domain.error.Result
import org.example.project.domain.error.EmailError
import org.example.project.domain.error.NameError
import org.example.project.domain.error.PasswordError

class UserDataValidator {
    fun isValidName(name: String): Result<Boolean, NameError> {
        val firstLetterUpperCase = "^[A-Z].*".toRegex()
        if (!firstLetterUpperCase.matches(name)){
            return Result.Error(NameError.NO_UPPERCASE)
        }

        if (name.length < 3){
            return Result.Error(NameError.TOO_SHORT)
        }

        return Result.Success(true)
    }

    fun isValidEmail(email: String): Result<Boolean, EmailError> {
        val emailRegex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        if (!emailRegex.matches(email)){
           return Result.Error(EmailError.INVALID_EMAIL)
        }

        return Result.Success(true)
    }

    fun isValidPassword(password: String): Result<Boolean, PasswordError> {
        return if (password.length < 6){
            Result.Error(PasswordError.TOO_SHORT)
        }else{
            Result.Success(true)
        }

    }

}