package org.example.project.domain.repository

import org.example.project.domain.model.AuthData


interface AuthRepository {
    suspend fun authenticate(email : String, password: String) : Result<AuthData>
    suspend fun getCurrentUser(): AuthData?
    suspend fun checkSession() : Boolean
}