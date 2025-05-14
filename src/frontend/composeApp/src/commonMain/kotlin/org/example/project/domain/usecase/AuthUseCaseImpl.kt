package org.example.project.domain.usecase

import org.example.project.domain.model.AuthData
import org.example.project.domain.repository.AuthRepository

class AuthUseCaseImpl(
    private val authRepository: AuthRepository
) : AuthUseCase {
    override suspend fun authenticate(email: String, password: String): Result<AuthData> = authRepository.authenticate(email, password)
}