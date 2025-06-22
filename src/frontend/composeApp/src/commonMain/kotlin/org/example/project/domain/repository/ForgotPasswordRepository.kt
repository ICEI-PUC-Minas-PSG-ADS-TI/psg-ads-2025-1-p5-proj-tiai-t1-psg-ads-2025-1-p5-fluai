package org.example.project.domain.repository

import org.example.project.data.dto.Email
import org.example.project.data.dto.ForgotPasswordResponseDto

interface ForgotPasswordRepository {
    suspend fun forgotPassword(request: Email): Result<ForgotPasswordResponseDto>
}