package org.example.project.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import org.example.project.domain.model.User
import org.example.project.domain.repository.SignUpRepository

class SignUpUseCaseImpl(
    private val signUpRepository : SignUpRepository
) : SignUpUseCase {
    override suspend fun addUser(user: User): Result<Unit> =
        withContext(Dispatchers.IO) { signUpRepository.postUser(user) }
}