package org.example.project.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val username : String,
    val password: String,
    val email: String
)