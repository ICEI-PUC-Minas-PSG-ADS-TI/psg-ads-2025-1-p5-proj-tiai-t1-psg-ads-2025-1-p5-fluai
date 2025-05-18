package org.example.project.ui.navigation

import androidx.compose.runtime.mutableStateOf

class BottomBarController {
    var selectedIndex = mutableStateOf(1)
        private set

    fun select(index : Int) {
        selectedIndex.value =  index
    }
}