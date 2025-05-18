package org.example.project.domain.repository

import org.example.project.domain.model.AuthData

interface SessionRepository {
    suspend fun getCurrentUser(): AuthData?
    suspend fun checkSession() : Boolean
    suspend fun logout()
}