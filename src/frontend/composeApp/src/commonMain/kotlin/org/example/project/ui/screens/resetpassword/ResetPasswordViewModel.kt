package org.example.project.ui.screens.resetpassword

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.ResetPasswordUseCase
import org.example.project.ui.extensions.coroutineScope

class ResetPasswordViewModel(
    componentContext: ComponentContext,
    private val oobCode: String,
    val onPasswordResetSuccess: () -> Unit,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ComponentContext by componentContext {

    private val _resetPasswordResult = MutableSharedFlow<ResetPasswordResult>()
    val resetPasswordResult = _resetPasswordResult.asSharedFlow()

    private suspend  fun resetPassword(newPassword : String){
        _resetPasswordResult.emit(ResetPasswordResult.Loading)
        val result = resetPasswordUseCase.resetPassword(oobCode = oobCode, newPassword = newPassword)
        result
            .onSuccess {
                _resetPasswordResult.emit(ResetPasswordResult.Success)
                onPasswordResetSuccess()
            }
            .onFailure {
                _resetPasswordResult.emit(ResetPasswordResult.Error(it.message ?: "Erro desconhecido"))
            }
    }

    fun onEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.ConfirmReset -> {
                coroutineScope.launch {
                   resetPassword(event.newPassword)
                }
            }
        }
    }
}
