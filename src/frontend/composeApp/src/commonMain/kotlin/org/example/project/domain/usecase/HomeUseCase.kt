package org.example.project.domain.usecase

import org.example.project.domain.model.Email

interface HomeUseCase {
    suspend fun verifyLevelingTest(email : Email) : Result<Boolean>
}