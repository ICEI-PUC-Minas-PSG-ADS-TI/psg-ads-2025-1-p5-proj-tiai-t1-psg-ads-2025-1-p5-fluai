package org.example.project.domain.usecase

import org.example.project.domain.model.UserLogin

interface AuthUseCase{
    suspend fun sendUserData(user : UserLogin) : Result<Unit>
}