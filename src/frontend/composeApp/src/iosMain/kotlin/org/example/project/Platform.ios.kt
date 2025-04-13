package org.example.project

import platform.UIKit.UIDevice

class IOSPlatform(
    override val isDebug: Boolean? = null,
    override val isIos: Boolean? = true,
    override val isAndroid: Boolean? = false
) : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()