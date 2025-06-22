package org.example.project.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.data.database.entities.UserEntity
import org.example.project.data.database.local.user.UserLocalDataSource
import org.example.project.domain.model.AuthData
import org.example.project.domain.model.Email
import org.example.project.domain.usecase.HomeUseCase
import org.example.project.ui.extensions.coroutineScope


class HomeViewModel(
    componentContext: ComponentContext,
    private val homeUseCase : HomeUseCase,
    private val userLocalDataSource: UserLocalDataSource,
    private val authData: AuthData,
    private val navigateToLevelingTest : (AuthData) -> Unit,
    secondsToAdd : Int
    ) : ComponentContext by componentContext {

    private val _showTestDialog = mutableStateOf(false)
    var showTestDialog: MutableState<Boolean> = _showTestDialog

    private val _loggedUserFlow = MutableStateFlow<UserEntity?>(null)
    val loggedUserFlow : StateFlow<UserEntity?> = _loggedUserFlow

    init {
        loadUserData()
        if (secondsToAdd > 0){
            addStudyTime(secondsToAdd)
        }
        verifyLevelingTest()
    }

    private fun loadUserData(){
        coroutineScope.launch {
            userLocalDataSource.observeLoggedUser().collect{ user ->
                _loggedUserFlow.value = user
            }
        }
    }

    fun addStudyTime(seconds: Int) {
        coroutineScope.launch {
            userLocalDataSource.addStudyTime(authData.email, seconds)

            val currentUser = _loggedUserFlow.value
            if (currentUser != null) {
                _loggedUserFlow.value = currentUser.copy(
                    dailyStudyTime = currentUser.dailyStudyTime + seconds
                )
            }
        }
    }

    private fun saveUserGoal(userGoal : Int){
        coroutineScope.launch {
            userLocalDataSource.updateUserDailyGoal(authData.email, userGoal)
        }
    }

     fun onEvent(event : HomeEvent){
         coroutineScope.launch {
             when(event){
                 is HomeEvent.GoToLevelingTest -> navigateToLevelingTest(authData)
                 is HomeEvent.SaveUserGoal -> saveUserGoal(event.goalMinutes)

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
    data class SaveUserGoal(val goalMinutes : Int) : HomeEvent()
}

