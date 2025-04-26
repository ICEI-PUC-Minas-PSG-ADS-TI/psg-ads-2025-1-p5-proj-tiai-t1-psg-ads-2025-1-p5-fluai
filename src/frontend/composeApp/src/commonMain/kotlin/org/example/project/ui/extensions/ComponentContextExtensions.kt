package org.example.project.ui.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

val coroutineScope: CoroutineScope
    get() = CoroutineScope(SupervisorJob() + Dispatchers.Main)
