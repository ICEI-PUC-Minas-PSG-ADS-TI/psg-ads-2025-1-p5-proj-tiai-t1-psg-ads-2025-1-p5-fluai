package org.example.project.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val email : String,
    val uid : String = "",
    val username : String = "Usuário",
    val authToken : String = "",
    val isLogged: Boolean = false,
    val smartChallengesCompleted : Int = 0,
    val fluencyBoostCompleted : Int = 0,
    val dailyGoal : Int = 15,
    val dailyStudyTime: Int = 0
)


