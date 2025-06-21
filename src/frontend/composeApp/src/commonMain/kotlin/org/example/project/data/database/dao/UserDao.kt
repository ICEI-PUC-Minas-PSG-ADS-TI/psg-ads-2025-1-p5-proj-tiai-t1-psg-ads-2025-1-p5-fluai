package org.example.project.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.example.project.data.database.entities.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users LIMIT 1")
    fun getCurrentUser(): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE isLogged = 1 LIMIT 1")
    suspend fun getLoggedUser(): UserEntity?

    @Query("UPDATE users SET isLogged = 0")
    suspend fun clearLoggedStatus()

    @Query("UPDATE users SET isLogged = 1 WHERE email = :email")
    suspend fun setUserLogged(email: String)

    @Query("UPDATE users SET smartChallengesCompleted = smartChallengesCompleted + 1 WHERE email = :email")
    suspend fun incrementSmartChallengesCompleted(email: String)

    @Query("UPDATE users SET fluencyBoostCompleted = fluencyBoostCompleted + 1 WHERE email = :email")
    suspend fun incrementFluencyBoostCompleted(email: String)

    @Query("SELECT * FROM users WHERE isLogged = 1 LIMIT 1")
    fun observeLoggedUser(): Flow<UserEntity?>

}