package org.example.project.domain.usecase

import org.example.project.domain.repository.ResetPasswordRepository

class ResetPasswordUseCaseImpl(
    private val repository: ResetPasswordRepository
) : ResetPasswordUseCase {
    override suspend fun resetPassword(oobCode: String, newPassword: String): Result<Unit> = repository.resetPassword(oobCode, newPassword)
}
