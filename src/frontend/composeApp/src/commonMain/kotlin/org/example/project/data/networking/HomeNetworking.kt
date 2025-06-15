package org.example.project.data.networking

import org.example.project.domain.model.Email

interface HomeNetworking {
    suspend fun verifyLevelingTest(email : Email): Result<Boolean>
}