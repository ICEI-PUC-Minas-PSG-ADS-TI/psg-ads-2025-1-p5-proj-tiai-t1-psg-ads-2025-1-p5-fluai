package org.example.project.domain.usecase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class ResetPasswordUseCase {
    suspend operator fun invoke(oobCode: String, newPassword: String): Result<Unit> {
        return try {
            Firebase.auth.confirmPasswordReset(oobCode, newPassword)
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
