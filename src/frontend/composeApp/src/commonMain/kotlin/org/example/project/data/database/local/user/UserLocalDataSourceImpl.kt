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

    override suspend fun updateUserDailyGoal(email: String, userGoal: Int){
        userDao.updateUserDailyGoal(email, userGoal)
    }

    override suspend fun addStudyTime(email: String, seconds: Int) {
        userDao.addStudyTime(email, seconds)
        val updatedUser = userDao.getLoggedUserSnapshot()?.copy()
        updatedUser?.let { userDao.insertUser(it) }
    }

    override suspend fun getDailyStudyTime(email: String): Int {
        return userDao.getDailyStudyTime(email)
    }

    override suspend fun getLoggedUserSnapshot(): UserEntity? {
        return userDao.getLoggedUserSnapshot()
    }


}