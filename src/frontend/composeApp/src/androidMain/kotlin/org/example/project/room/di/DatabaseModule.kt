package org.example.project.room.di

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidPlatformModule = module {
    single<Any> { androidContext() }
}
