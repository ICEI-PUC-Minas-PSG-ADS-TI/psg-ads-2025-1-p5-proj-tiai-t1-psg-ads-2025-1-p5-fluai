package org.example.project.data.database.local.user

import kotlinx.coroutines.flow.Flow
import org.example.project.data.database.entities.UserEntity

interface UserLocalDataSource {
    suspend fun saveUser(user : UserEntity)
    suspend fun getUsername(email : String): UserEntity?
    suspend fun getLoggedUser(): UserEntity?
    suspend fun clearLoggedStatus()
    suspend fun setUserLogged(email: String)
    suspend fun incrementSmartChallengesCompleted(email: String)
    suspend fun incrementFluencyBoostCompleted(email: String)
    suspend fun observeLoggedUser(): Flow<UserEntity?>
    suspend fun updateUserDailyGoal(email: String, userGoal: Int)
    suspend fun addStudyTime(email: String, seconds: Int)
    suspend fun getDailyStudyTime(email: String): Int
    suspend fun getLoggedUserSnapshot(): UserEntity?
}