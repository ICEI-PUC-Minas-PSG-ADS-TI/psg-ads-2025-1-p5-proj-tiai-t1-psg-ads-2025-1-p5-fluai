package org.example.project

import android.os.Build
import androidx.room.Room
import org.example.project.data.database.AppDatabase
import android.content.Context


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

    actual fun createDatabase(platformContext: Any): AppDatabase {
        val context = platformContext as Context
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration(false).build()
    }
}