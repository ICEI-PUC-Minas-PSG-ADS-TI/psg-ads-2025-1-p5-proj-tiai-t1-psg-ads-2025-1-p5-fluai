package org.example.project

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import org.example.project.di.dataModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import kotlinx.coroutines.runBlocking

class MainApplication : Application() {
    companion object {
        var appContext: Context? = null
            private set
    }
    override fun onCreate() {
        super.onCreate()
        appContext = this
        runBlocking {
            FirebaseApp.initializeApp(this@MainApplication) // Android SDK
            Firebase.initialize(this@MainApplication) // KMP
        }

        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(dataModules)
        }

    }
}