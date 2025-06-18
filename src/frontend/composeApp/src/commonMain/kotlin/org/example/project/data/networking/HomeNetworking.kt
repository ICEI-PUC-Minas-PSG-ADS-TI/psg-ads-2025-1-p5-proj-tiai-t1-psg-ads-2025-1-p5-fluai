package org.example.project.data.networking

import org.example.project.domain.model.Email
import org.example.project.domain.model.Response

interface HomeNetworking {
    suspend fun verifyLevelingTest(email : Email): Result<Response?>
}