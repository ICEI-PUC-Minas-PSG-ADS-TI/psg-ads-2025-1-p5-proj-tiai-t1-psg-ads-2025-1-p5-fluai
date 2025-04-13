package org.example.project

interface Platform {
    val name: String?
    val isDebug : Boolean?
    val isIos : Boolean?
    val isAndroid : Boolean?
}

expect fun getPlatform(): Platform