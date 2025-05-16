package org.example.project.data.database.local.user

import org.example.project.data.database.entities.UserEntity

interface UserLocalDataSource {
    suspend fun saveUser(user : UserEntity)
    suspend fun getCurrentUser() : UserEntity?
    suspend fun getUsername(email : String): UserEntity?
    suspend fun getLoggedUser(): UserEntity?
    suspend fun clearLoggedStatus()
    suspend fun setUserLogged(email: String)
}