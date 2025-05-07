package org.example.project


import platform.UIKit.UIDevice
import androidx.room.Room
import androidx.sqlite.driver.NativeSQLiteDriver
import kotlinx.coroutines.Dispatchers
import org.example.project.data.database.AppDatabase

class IOSPlatform(
    override val isDebug: Boolean? = null,
    override val isIos: Boolean? = true,
    override val isAndroid: Boolean? = false
) : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

internal actual object DatabaseProvider {
    private const val DB_NAME = "app_database.db"

    actual fun createDatabase(platformContext: Any): AppDatabase {
        val dbPath = platformContext as String

        return Room.databaseBuilder<AppDatabase>(
            name = dbPath,
            factory = { AppDatabase::class.instantiateImpl() }
        )
            .setDriver(NativeSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.Default)
            .build()
    }
}

