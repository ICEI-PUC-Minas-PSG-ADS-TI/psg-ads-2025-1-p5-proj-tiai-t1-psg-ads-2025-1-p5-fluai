package org.example.project.data.repository

import dev.gitlive.firebase.auth.FirebaseAuth
import org.example.project.data.database.local.user.UserLocalDataSource
import org.example.project.data.mapper.toAuthData
import org.example.project.domain.model.AuthData
import org.example.project.domain.repository.SessionRepository

class SessionRepositoryImpl(
    private val firebase : FirebaseAuth,
    private val userLocalDataSource: UserLocalDataSource
) : SessionRepository {
    override suspend fun getCurrentUser(): AuthData? {
        return userLocalDataSource.getLoggedUser()?.toAuthData()
    }

    override suspend fun checkSession(): Boolean {
        val firebaseUser = firebase.currentUser
        val localUser = userLocalDataSource.getLoggedUser()
        return firebaseUser != null && localUser != null && localUser.email == firebaseUser.email
    }

    override suspend fun logout() {
        firebase.signOut()
        userLocalDataSource.clearLoggedStatus()
    }
}