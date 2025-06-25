package org.example.project.domain.repository

interface ResetPasswordRepository {
    suspend fun resetPassword(oobCode: String, newPassword: String) : Result<Unit>
}