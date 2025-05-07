package org.example.project.data.database.local.user

import kotlinx.coroutines.flow.Flow
import org.example.project.data.database.dao.UserDao
import org.example.project.data.database.entities.UserEntity

class UserLocalDataSourceImpl(
    private val userDao: UserDao
): UserLocalDataSource {
    override suspend fun saveUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override fun observeUser(): Flow<UserEntity?> = userDao.getUser()

}