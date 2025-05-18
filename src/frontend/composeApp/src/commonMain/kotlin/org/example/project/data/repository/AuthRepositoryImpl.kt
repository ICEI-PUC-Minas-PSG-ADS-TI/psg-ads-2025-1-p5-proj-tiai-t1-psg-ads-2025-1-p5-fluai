package org.example.project.data.repository


import org.example.project.AuthDataSource
import org.example.project.data.database.entities.UserEntity
import org.example.project.data.database.local.user.UserLocalDataSource
import org.example.project.domain.model.AuthData
import org.example.project.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val auth: AuthDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : AuthRepository {
    override suspend fun authenticate(email: String, password: String): Result<AuthData> {
        return try {
            val authResult = auth.authenticate(email, password).getOrThrow()
            userLocalDataSource.clearLoggedStatus()
            userLocalDataSource.setUserLogged(email)
            val localUser = userLocalDataSource.getUsername(email) ?: UserEntity(
                uid = "",
                email = email,
                username = "Usuário",
                authToken = "",
                isLogged = true
            )
            val authUser = AuthData(
                username = localUser.username,
                uuid = authResult.uuid,
                token = authResult.token,
                email = authResult.email
            )

            userLocalDataSource.saveUser(
                localUser.copy(
                    uid = authUser.uuid,
                    authToken = authUser.token
                )
            )

            Result.success(authUser)

        } catch (e: Exception) {
            Result.failure(Throwable(message = e.message))
        }

    }

}

