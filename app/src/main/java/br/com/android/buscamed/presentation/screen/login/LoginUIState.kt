package br.com.android.buscamed.presentation.screen.login

import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.field.DefaultTextField
import br.com.android.buscamed.presentation.core.state.interfaces.LoadingUIState
import br.com.android.buscamed.presentation.core.state.interfaces.ThrowableUIState

data class LoginUIState(
    val email: DefaultTextField = DefaultTextField(),
    val password: DefaultTextField = DefaultTextField(),
    override val messageDialogState: MessageDialogState = MessageDialogState(),
    override val showLoading: Boolean = false,
    override val onToggleLoading: () -> Unit = { },
) : LoadingUIState, ThrowableUIState