package br.com.android.buscamed.presentation.state

import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.interfaces.LoadingUIState
import br.com.android.buscamed.presentation.core.state.interfaces.ThrowableUIState

data class HomeUIState(
    override val messageDialogState: MessageDialogState = MessageDialogState(),
    override val showLoading: Boolean = false,
    override val onToggleLoading: () -> Unit = { },
) : LoadingUIState, ThrowableUIState