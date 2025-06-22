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

    private val scope = coroutineScope
    private val _state = MutableSharedFlow<ResetPasswordResult>()
    val state = _state.asSharedFlow()

    fun onEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.ConfirmReset -> {
                scope.launch {
                    _state.emit(ResetPasswordResult.Loading)
                    val result = resetPasswordUseCase(oobCode, event.newPassword)
                    result
                        .onSuccess {
                            _state.emit(ResetPasswordResult.Success)
                            onPasswordResetSuccess()
                        }
                        .onFailure {
                            _state.emit(ResetPasswordResult.Error(it.message ?: "Erro desconhecido"))
                        }
                }
            }
        }
    }
}
