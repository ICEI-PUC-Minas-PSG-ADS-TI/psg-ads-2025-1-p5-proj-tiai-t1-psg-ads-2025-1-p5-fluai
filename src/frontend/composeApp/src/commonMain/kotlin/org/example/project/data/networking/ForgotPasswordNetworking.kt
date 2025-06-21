package org.example.project.data.networking

import org.example.project.data.dto.ForgotPasswordRequestDto

interface ForgotPasswordNetworking {
    suspend fun sendForgotPasswordRequest(request: ForgotPasswordRequestDto): Result<>
}