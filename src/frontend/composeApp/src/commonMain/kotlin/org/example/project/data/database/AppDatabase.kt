package org.example.project.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import org.example.project.data.database.dao.UserDao
import org.example.project.data.database.entities.UserEntity

@Database(
   entities = [UserEntity::class],
   version = 2,
   exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
   abstract fun userDao() : UserDao
}