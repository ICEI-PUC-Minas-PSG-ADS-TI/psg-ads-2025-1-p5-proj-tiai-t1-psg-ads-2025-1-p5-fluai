package org.example.project.domain.repository

import org.example.project.domain.model.AuthData


interface AuthRepository {
    suspend fun authenticate(email : String, password: String) : Result<AuthData>
}