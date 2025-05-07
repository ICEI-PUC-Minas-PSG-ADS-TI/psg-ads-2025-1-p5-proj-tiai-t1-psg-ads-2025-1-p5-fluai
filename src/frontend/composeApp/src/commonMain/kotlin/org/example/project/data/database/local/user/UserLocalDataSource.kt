package org.example.project.data.database.local.user

import kotlinx.coroutines.flow.Flow
import org.example.project.data.database.entities.UserEntity

interface UserLocalDataSource {
    suspend fun saveUser(user : UserEntity)
    fun observeUser() : Flow<UserEntity?>
}