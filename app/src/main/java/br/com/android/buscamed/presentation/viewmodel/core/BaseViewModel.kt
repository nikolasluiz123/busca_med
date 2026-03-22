package br.com.android.buscamed.presentation.viewmodel.core

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.field.DefaultTextField
import br.com.android.buscamed.presentation.core.extensions.parseDouble

abstract class BaseViewModel: ViewModel() {

    protected abstract fun initialLoadUIState()

    protected fun createMessageDialogState(
        getCurrentState: () -> MessageDialogState,
        updateState: (newState: MessageDialogState) -> Unit
    ): MessageDialogState {
        return MessageDialogState(
            onShowDialog = { type, message, onConfirm, onCancel ->
                val newState = getCurrentState().copy(
                    dialogType = type,
                    dialogMessage = message,
                    showDialog = true,
                    onConfirm = onConfirm,
                    onCancel = onCancel
                )

                updateState(newState)
            },
            onHideDialog = {
                val newState = getCurrentState().copy(showDialog = false)
                updateState(newState)
            }
        )
    }

    protected fun createTextFieldState(
        getCurrentState: () -> DefaultTextField,
        updateState: (newState: DefaultTextField) -> Unit,
        canWrite: (String) -> Boolean = { true }
    ): DefaultTextField {
        return DefaultTextField(
            onChange = {
                if (canWrite(it)) {
                    val newState = getCurrentState().copy(
                        value = it,
                        errorMessage = ""
                    )

                    updateState(newState)
                }
            }
        )
    }

    protected fun createDoubleValueTextFieldState(
        getCurrentState: () -> DefaultTextField,
        onValueChange: (newState: DefaultTextField, newValue: Double?) -> Unit
    ): DefaultTextField {
        return createTextFieldState(
            getCurrentState = getCurrentState,
            canWrite = { it.isDigitsOnly() },
            updateState = { textField ->
                onValueChange(textField, textField.value.parseDouble())
            }
        )
    }

    protected fun createIntValueTextFieldState(
        getCurrentState: () -> DefaultTextField,
        onValueChange: (newState: DefaultTextField, newValue: Int?) -> Unit
    ): DefaultTextField {
        return createTextFieldState(
            getCurrentState = getCurrentState,
            canWrite = { it.isDigitsOnly() },
            updateState = { textField ->
                onValueChange(textField, textField.value.toIntOrNull())
            }
        )
    }

    protected fun createLongValueTextFieldState(
        getCurrentState: () -> DefaultTextField,
        onValueChange: (newState: DefaultTextField, newValue: Long?) -> Unit
    ): DefaultTextField {
        return createTextFieldState(
            getCurrentState = getCurrentState,
            canWrite = { it.isDigitsOnly() },
            updateState = { textField ->
                onValueChange(textField, textField.value.toLongOrNull())
            }
        )
    }
}