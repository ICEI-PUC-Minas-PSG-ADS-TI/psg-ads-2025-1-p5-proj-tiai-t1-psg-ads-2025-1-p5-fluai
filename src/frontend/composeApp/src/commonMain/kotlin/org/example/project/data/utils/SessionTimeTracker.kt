package org.example.project.data.utils

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class SessionTimeTracker {
    private var startTime : Long = 0L
    private var elapsedTime : Long = 0L
    private var isTracking = false


    @OptIn(ExperimentalTime::class)
    fun startSession(){
        if (!isTracking){
            startTime = Clock.System.now().toEpochMilliseconds()
            isTracking = true
        }
    }

    @OptIn(ExperimentalTime::class)
    fun endSession(): Int {
        if (isTracking) {
            elapsedTime = Clock.System.now().toEpochMilliseconds() - startTime
            isTracking = false
            val seconds = (elapsedTime / 1000).toInt()
            return seconds
        }
        return 0
    }
}