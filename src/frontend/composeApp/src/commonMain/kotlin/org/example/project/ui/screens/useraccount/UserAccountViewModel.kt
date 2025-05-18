package org.example.project.ui.screens.useraccount

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import org.example.project.domain.repository.SessionRepository
import org.example.project.ui.extensions.coroutineScope

class UserAccountViewModel(
    componentContext: ComponentContext,
    private val onLogout : () -> Unit,
    private val sessionRepository: SessionRepository
) : ComponentContext by componentContext{

    private suspend fun logoutUser(){
        sessionRepository.logout()
        onLogout()
    }

    fun onEvent(event : UserAccountEvent){
        when(event){
            UserAccountEvent.Logout -> {
                coroutineScope.launch {
                    logoutUser()
                }
            }
        }
    }
}

