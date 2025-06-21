package org.example.project.domain.usecase

import org.example.project.data.dto.ForgotPasswordRequestDto
import org.example.project.domain.repository.ForgotPasswordRepository

class ForgotPasswordUseCase(
    private val repository: ForgotPasswordRepository
) {
    suspend operator fun invoke(email: String): Result<String> {
        return try {
            val request = ForgotPasswordRequestDto(email)
            repository.forgotPassword(request)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}