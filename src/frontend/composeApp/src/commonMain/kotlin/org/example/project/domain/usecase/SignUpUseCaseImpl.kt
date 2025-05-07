package org.example.project.domain.usecase

import org.example.project.domain.model.User
import org.example.project.domain.repository.SignUpRepository

class SignUpUseCaseImpl(
    private val signUpRepository: SignUpRepository
) : SignUpUseCase {
    override suspend fun addUser(user: User): Result<Unit> = signUpRepository.postUser(user)
}