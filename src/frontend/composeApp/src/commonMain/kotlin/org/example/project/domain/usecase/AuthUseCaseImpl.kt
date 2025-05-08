package org.example.project.domain.usecase

import org.example.project.domain.model.UserLogin


class AuthUseCaseImpl() : AuthUseCase {
    override suspend fun sendUserData(user: UserLogin): Result<Unit> {
        TODO("Not yet implemented")
    }
}