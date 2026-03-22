package br.com.android.buscamed.presentation.core.state.interfaces.dialog

import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType

fun interface ShowMessageDialogCallback {
    fun onShow(type: EnumDialogType, message: String, onConfirm: () -> Unit, onCancel: () -> Unit)
}