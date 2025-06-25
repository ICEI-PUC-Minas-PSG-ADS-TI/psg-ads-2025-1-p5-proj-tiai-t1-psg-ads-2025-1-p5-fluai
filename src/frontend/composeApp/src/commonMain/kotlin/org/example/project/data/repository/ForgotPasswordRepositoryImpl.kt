package org.example.project.data.repository

import org.example.project.data.dto.Email
import org.example.project.data.dto.ForgotPasswordResponseDto
import org.example.project.data.networking.ForgotPasswordNetworking
import org.example.project.domain.repository.ForgotPasswordRepository

class ForgotPasswordRepositoryImpl(
    private val networking: ForgotPasswordNetworking
) : ForgotPasswordRepository {
    override suspend fun forgotPassword(request: Email): Result<ForgotPasswordResponseDto> {
        return networking.sendForgotPasswordRequest(request)
    }
}