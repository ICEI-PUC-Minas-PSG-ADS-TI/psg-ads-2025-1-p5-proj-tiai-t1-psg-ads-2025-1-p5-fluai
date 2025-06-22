package org.example.project.domain.usecase

import org.example.project.data.dto.Email
import org.example.project.data.dto.ForgotPasswordResponseDto
import org.example.project.domain.repository.ForgotPasswordRepository

class ForgotPasswordUseCase(
    private val repository: ForgotPasswordRepository
) {
    suspend operator fun invoke(email: String): Result<ForgotPasswordResponseDto> {
        return repository.forgotPassword(Email(email))
    }
}