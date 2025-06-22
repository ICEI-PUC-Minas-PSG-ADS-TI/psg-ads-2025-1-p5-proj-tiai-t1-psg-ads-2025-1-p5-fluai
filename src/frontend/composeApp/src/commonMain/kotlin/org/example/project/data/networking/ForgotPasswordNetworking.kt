package org.example.project.data.networking

import org.example.project.data.dto.Email
import org.example.project.data.dto.ForgotPasswordResponseDto

interface ForgotPasswordNetworking {
    suspend fun sendForgotPasswordRequest(request: Email): Result<ForgotPasswordResponseDto>
}