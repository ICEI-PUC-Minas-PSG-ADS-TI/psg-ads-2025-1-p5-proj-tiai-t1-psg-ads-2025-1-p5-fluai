package org.example.project.data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import org.example.project.domain.repository.ResetPasswordRepository

class ResetPasswordRepositoryImpl : ResetPasswordRepository {
    override suspend fun resetPassword(oobCode: String, newPassword: String): Result<Unit> {
        return try {
            Firebase.auth.confirmPasswordReset(code = oobCode, newPassword = newPassword)
            Result.success(Unit)
        }catch (e : Exception){
            Result.failure(e)
        }
    }
}