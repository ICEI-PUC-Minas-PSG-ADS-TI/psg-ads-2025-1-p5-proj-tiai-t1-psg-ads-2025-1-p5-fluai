package org.example.project

import android.os.Build

class AndroidPlatform(
    override val isDebug: Boolean? = null,
    override val isIos: Boolean = false,
    override val isAndroid: Boolean = true
) : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()