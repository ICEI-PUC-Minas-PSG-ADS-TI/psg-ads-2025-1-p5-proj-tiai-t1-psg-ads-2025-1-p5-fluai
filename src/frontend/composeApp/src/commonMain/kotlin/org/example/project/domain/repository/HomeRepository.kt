package org.example.project.domain.repository

import org.example.project.domain.model.Email
import org.example.project.domain.model.Response

interface HomeRepository {
    suspend fun verifyLevelingTest(email : Email) : Result<Response?>
}