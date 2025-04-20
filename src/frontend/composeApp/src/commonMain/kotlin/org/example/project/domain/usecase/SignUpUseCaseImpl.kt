package org.example.project.domain.usecase

import org.example.project.domain.model.User
import org.example.project.domain.repository.SignUpRepository

class SignUpUseCaseImpl(
    val signUpRepository : SignUpRepository
) : SignUpUseCase {
    override suspend fun addUser(user: User) {
        signUpRepository.postUser(user)
    }
}