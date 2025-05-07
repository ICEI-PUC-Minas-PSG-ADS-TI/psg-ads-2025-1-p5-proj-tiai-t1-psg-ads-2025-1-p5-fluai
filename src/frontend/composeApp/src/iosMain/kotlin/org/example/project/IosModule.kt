package org.example.project

import org.example.project.di.dataModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import platform.Foundation.NSHomeDirectory

fun initKoin() {
    startKoin {
        modules(
            iosPlatformModule + dataModules
        )
    }
}

val iosPlatformModule = module {
    single<Any> {
        NSHomeDirectory() + "/Documents"
    }
}