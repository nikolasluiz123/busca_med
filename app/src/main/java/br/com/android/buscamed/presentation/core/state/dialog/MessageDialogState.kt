package br.com.android.buscamed.presentation.core.state.dialog

import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType
import br.com.android.buscamed.presentation.core.state.interfaces.dialog.ShowMessageDialogCallback

data class MessageDialogState(
    val dialogType: EnumDialogType = EnumDialogType.ERROR,
    val dialogMessage: String = "",
    val showDialog: Boolean = false,
    val onShowDialog: ShowMessageDialogCallback? = null,
    val onHideDialog: () -> Unit = { },
    val onConfirm: () -> Unit = { },
    val onCancel: () -> Unit = { }
)