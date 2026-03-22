package br.com.android.buscamed.presentation.core.state.enumeration

import br.com.android.buscamed.R

enum class EnumDialogType(val titleResId: Int) {
    ERROR(R.string.error_dialog_title),
    CONFIRMATION(R.string.warning_dialog_title),
    INFORMATION(R.string.information_dialog_title)
}