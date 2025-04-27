package org.example.project.domain.usecase

import org.example.project.domain.error.DataError
import org.example.project.domain.error.Result
import org.example.project.domain.model.User

interface SignUpUseCase {
    suspend fun addUser(user : User) : Result<Unit, DataError>
}