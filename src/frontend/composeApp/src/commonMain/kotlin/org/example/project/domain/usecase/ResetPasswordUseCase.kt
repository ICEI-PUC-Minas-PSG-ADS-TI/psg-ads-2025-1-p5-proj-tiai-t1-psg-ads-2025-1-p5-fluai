package org.example.project.domain.usecase

interface ResetPasswordUseCase {
    suspend fun resetPassword(oobCode: String, newPassword: String) : Result<Unit>
}