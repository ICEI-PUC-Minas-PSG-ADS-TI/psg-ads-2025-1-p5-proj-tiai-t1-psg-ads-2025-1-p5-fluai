package org.example.project

import android.os.Build
import androidx.room.Room
import org.example.project.data.database.AppDatabase
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await
import org.example.project.domain.model.AuthData
import org.koin.core.Koin


class AndroidPlatform(
    override val isDebug: Boolean? = null,
    override val isIos: Boolean = false,
    override val isAndroid: Boolean = true
) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

internal actual object DatabaseProvider {
    private const val DB_NAME = "app_database.db"

    actual fun createDatabase(): AppDatabase {
        val context = getKoinInstance().get<Context>()
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration(false).build()
    }

    private fun getKoinInstance(): Koin = org.koin.core.context.GlobalContext.get()
}


actual class AuthDataSourceImpl actual constructor() : AuthDataSource  {


    actual override suspend fun authenticate(email: String, password: String) : Result<AuthData> {
        return runCatching {
            val authResult = FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .await()

            val user = authResult.user ?: throw Exception("User not found")
            val token = user.getIdToken(false).await()?.token ?: ""

            AuthData(uuid = user.uid, token = token, email = user.email.orEmpty(), username = "")
        }.fold(
            onSuccess = {Result.success(it)},
            onFailure = {Result.failure(it.toAuthError())}
        )
    }

    private fun Throwable.toAuthError(): Throwable = when (this) {
        is FirebaseAuthException -> when (errorCode) {
            "ERROR_INVALID_EMAIL" -> IllegalArgumentException("E-mail inválido")
            "ERROR_WRONG_PASSWORD" -> IllegalArgumentException("Senha incorreta")
            else -> this
        }
        else -> this
    }
}
