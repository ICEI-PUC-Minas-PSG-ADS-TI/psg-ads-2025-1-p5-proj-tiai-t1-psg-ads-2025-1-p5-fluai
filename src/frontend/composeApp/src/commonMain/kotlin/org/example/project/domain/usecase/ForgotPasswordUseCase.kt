package org.example.project.domain.usecase

import org.example.project.data.dto.ForgotPasswordRequestDto
import org.example.project.domain.repository.ForgotPasswordRepository

class ForgotPasswordUseCase(
    private val repository: ForgotPasswordRepository
) {
    suspend operator fun invoke(email: String): Result<String> {
        val request = ForgotPasswordRequestDto(email)
        return repository.forgotPassword(request)
    }
}