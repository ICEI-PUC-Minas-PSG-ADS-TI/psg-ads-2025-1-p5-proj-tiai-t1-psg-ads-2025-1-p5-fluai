package org.example.project.domain.usecase

import org.example.project.data.dto.ForgotPasswordResponseDto

interface ForgotPasswordUseCase {
    suspend fun forgotPassword(email: String): Result<ForgotPasswordResponseDto>
}