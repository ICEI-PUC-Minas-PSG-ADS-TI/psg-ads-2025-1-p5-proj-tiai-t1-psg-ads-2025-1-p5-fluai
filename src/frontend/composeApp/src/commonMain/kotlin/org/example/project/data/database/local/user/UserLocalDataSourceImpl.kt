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

    override suspend fun getUsername(email : String): UserEntity? {
        return userDao.getUserByEmail(email)
    }

    override suspend fun getLoggedUser(): UserEntity? {
        return userDao.getLoggedUser()
    }

    override suspend fun clearLoggedStatus() {
        userDao.clearLoggedStatus()
    }

    override suspend fun setUserLogged(email: String) {
         userDao.setUserLogged(email)
    }

    override suspend fun incrementSmartChallengesCompleted(email: String) {
        userDao.incrementSmartChallengesCompleted(email)
    }

    override suspend fun incrementFluencyBoostCompleted(email: String) {
        userDao.incrementFluencyBoostCompleted(email)
    }

    override suspend fun observeLoggedUser(): Flow<UserEntity?> {
        return userDao.observeLoggedUser()
    }

}