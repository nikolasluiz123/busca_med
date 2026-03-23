package br.com.android.buscamed.presentation.viewmodel.core

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.android.buscamed.domain.core.validation.FieldValidationError
import br.com.android.buscamed.domain.core.validation.GeneralValidationError
import br.com.android.buscamed.domain.core.validation.ValidationError
import br.com.android.buscamed.presentation.core.state.dialog.MessageDialogState
import br.com.android.buscamed.presentation.core.state.field.DefaultTextField
import br.com.android.buscamed.presentation.core.extensions.parseDouble
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

abstract class BaseViewModel: ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleUnexpectedError(throwable)
    }

    protected abstract fun initialLoadUIState()
    protected abstract fun getErrorMessageFrom(throwable: Throwable): String
    protected abstract fun onShowErrorDialog(message: String)

    protected fun handleUnexpectedError(throwable: Throwable) {
        val message = getErrorMessageFrom(throwable)
        onShowErrorDialog(message)
        Log.e(javaClass.simpleName, "Erro inesperado capturado: ${throwable.message}", throwable)
    }

    protected fun logValidationWarnings(errors: List<ValidationError>) {
        errors.forEach { error ->
            when (error) {
                is GeneralValidationError<*> if error.cause != null -> {
                    Log.w(javaClass.simpleName, "Aviso de regra de negócio/domínio: ${error.type}", error.cause)
                }

                is FieldValidationError<*, *> -> {
                    Log.w(javaClass.simpleName, "Aviso de validação de campo: $error")
                }

                else -> {
                    Log.w(javaClass.simpleName, "Aviso de validação desconhecido: $error")
                }
            }
        }
    }

    protected fun launch(block: suspend () -> Unit) = viewModelScope.launch(exceptionHandler) {
        block()
    }

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