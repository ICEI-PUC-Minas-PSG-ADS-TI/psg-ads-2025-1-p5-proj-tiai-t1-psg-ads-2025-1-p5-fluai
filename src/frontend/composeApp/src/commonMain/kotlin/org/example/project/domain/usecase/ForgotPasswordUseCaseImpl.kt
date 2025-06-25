package org.example.project.domain.usecase

import org.example.project.data.dto.Email
import org.example.project.data.dto.ForgotPasswordResponseDto
import org.example.project.domain.repository.ForgotPasswordRepository

class ForgotPasswordUseCaseImpl(
    private val repository: ForgotPasswordRepository
) : ForgotPasswordUseCase {
    override suspend fun forgotPassword(email: String): Result<ForgotPasswordResponseDto> {
        return repository.forgotPassword(Email(email))
    }
}