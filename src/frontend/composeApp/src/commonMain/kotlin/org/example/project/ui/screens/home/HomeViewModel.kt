package org.example.project.ui.screens.home

import com.arkivanov.decompose.ComponentContext
import org.example.project.domain.model.AuthData

class HomeViewModel(
    componentContext: ComponentContext,
    private val authData: AuthData,
) : ComponentContext by componentContext {

    fun getName(): String = authData.username
}