package org.example.project.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.launch
import org.example.project.domain.model.AuthData
import org.example.project.domain.model.Email
import org.example.project.domain.usecase.HomeUseCase
import org.example.project.ui.extensions.coroutineScope


class HomeViewModel(
    componentContext: ComponentContext,
    private val homeUseCase : HomeUseCase,
    private val authData: AuthData,
    private val navigateToLevelingTest : (AuthData) -> Unit,
    ) : ComponentContext by componentContext {

    private val _showTestDialog = mutableStateOf(false)
    var showTestDialog: MutableState<Boolean> = _showTestDialog

    init {
        verifyLevelingTest()
    }

     fun onEvent(event : HomeEvent){
         coroutineScope.launch {
             when(event){
                 is HomeEvent.GoToLevelingTest -> navigateToLevelingTest(authData)
             }
         }

    }

     private fun verifyLevelingTest(){
        coroutineScope.launch {
            val response = homeUseCase.verifyLevelingTest(Email(authData.email))
            response.onSuccess {
                _showTestDialog.value = false
            }.onFailure {
                _showTestDialog.value = true
            }
        }
    }

    fun getName(): String = authData.username
}

sealed class HomeEvent {
    data object GoToLevelingTest : HomeEvent()
}

