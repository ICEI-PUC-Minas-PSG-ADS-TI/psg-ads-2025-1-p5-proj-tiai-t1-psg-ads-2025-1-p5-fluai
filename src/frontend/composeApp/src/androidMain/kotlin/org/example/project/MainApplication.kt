package org.example.project

import android.app.Application
import org.example.project.di.dataModules
import org.example.project.room.di.androidPlatformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            modules(androidPlatformModule + dataModules)
            androidContext(this@MainApplication)
        }
    }
}