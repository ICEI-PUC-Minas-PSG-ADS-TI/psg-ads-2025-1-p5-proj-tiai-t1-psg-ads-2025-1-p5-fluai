package org.example.project.data.repository

import org.example.project.data.dto.ForgotPasswordRequestDto
import org.example.project.data.networking.ForgotPasswordNetworking
import org.example.project.domain.repository.ForgotPasswordRepository

class ForgotPasswordRepositoryImpl(
    private val networking: ForgotPasswordNetworking
) : ForgotPasswordRepository {
    override suspend fun forgotPassword(request: ForgotPasswordRequestDto): Result<String> {
        return networking.sendForgotPasswordRequest(request)
    }
}