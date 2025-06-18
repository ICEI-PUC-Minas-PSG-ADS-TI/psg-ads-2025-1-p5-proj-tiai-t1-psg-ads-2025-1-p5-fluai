package org.example.project.domain.usecase

import org.example.project.domain.model.Email
import org.example.project.domain.model.Response

interface HomeUseCase {
    suspend fun verifyLevelingTest(email : Email) : Result<Response?>
}