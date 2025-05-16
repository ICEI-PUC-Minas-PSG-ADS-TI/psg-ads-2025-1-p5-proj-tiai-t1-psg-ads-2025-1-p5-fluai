package org.example.project

import org.example.project.data.database.AppDatabase
import org.example.project.domain.model.AuthData

interface Platform {
    val name: String?
    val isDebug : Boolean?
    val isIos : Boolean?
    val isAndroid : Boolean?
}

expect fun getPlatform(): Platform

expect class AuthDataSourceImpl() : AuthDataSource{
    override suspend fun authenticate(email : String, password: String): Result<AuthData>

}

interface AuthDataSource{
    suspend fun authenticate(email : String, password: String) : Result<AuthData>
}

internal expect object DatabaseProvider {
    fun createDatabase(): AppDatabase
}