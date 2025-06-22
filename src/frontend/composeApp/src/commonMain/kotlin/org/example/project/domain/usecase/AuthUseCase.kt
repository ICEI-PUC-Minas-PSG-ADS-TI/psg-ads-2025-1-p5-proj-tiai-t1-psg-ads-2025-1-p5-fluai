package org.example.project.domain.usecase

import org.example.project.domain.model.AuthData

interface AuthUseCase{
    suspend fun authenticate(email: String, password: String) : Result<AuthData>
}