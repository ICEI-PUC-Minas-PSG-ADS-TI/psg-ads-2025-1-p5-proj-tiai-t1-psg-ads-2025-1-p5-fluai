package org.example.project

import org.example.project.data.database.AppDatabase

interface Platform {
    val name: String?
    val isDebug : Boolean?
    val isIos : Boolean?
    val isAndroid : Boolean?
}

expect fun getPlatform(): Platform

internal expect object DatabaseProvider {
    fun createDatabase(platformContext: Any): AppDatabase
}