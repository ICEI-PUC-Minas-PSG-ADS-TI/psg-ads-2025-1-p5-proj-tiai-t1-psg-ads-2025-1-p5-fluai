package org.example.project.domain.repository

import org.example.project.data.dto.ForgotPasswordRequestDto

interface ForgotPasswordRepository {
    suspend fun forgotPassword(request: ForgotPasswordRequestDto): Result<String>
}