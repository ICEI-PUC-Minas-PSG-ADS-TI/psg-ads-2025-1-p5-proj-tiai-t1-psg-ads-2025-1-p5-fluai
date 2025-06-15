package org.example.project.domain.repository

import org.example.project.domain.model.Email

interface HomeRepository {
    suspend fun verifyLevelingTest(email : Email) : Boolean
}