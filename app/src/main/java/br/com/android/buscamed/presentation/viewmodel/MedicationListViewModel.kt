package br.com.android.buscamed.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import br.com.android.buscamed.R
import br.com.android.buscamed.presentation.core.extensions.fromJsonNavParamToArgs
import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType
import br.com.android.buscamed.presentation.screen.result.MedicationListArgs
import br.com.android.buscamed.presentation.screen.result.medicationListScreenArguments
import br.com.android.buscamed.presentation.screen.result.state.MedicationListUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MedicationListViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(MedicationListUIState())
    val uiState: StateFlow<MedicationListUIState> = _uiState.asStateFlow()

    private val jsonArgs: String? = savedStateHandle[medicationListScreenArguments]

    init {
        initialLoadUIState()
    }

    override fun initialLoadUIState() {
        val args = jsonArgs?.fromJsonNavParamToArgs(MedicationListArgs::class.java)

        val dialogState = createMessageDialogState(
            getCurrentState = { _uiState.value.messageDialogState },
            updateState = { newState ->
                _uiState.value = _uiState.value.copy(messageDialogState = newState)
            }
        )

        _uiState.value = _uiState.value.copy(
            messageDialogState = dialogState,
            medications = args?.medications ?: emptyList()
        )
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return throwable.message ?: context.getString(R.string.medication_error_list_loading)
    }

    override fun onShowErrorDialog(message: String) {
        _uiState.value.messageDialogState.onShowDialog?.onShow(
            type = EnumDialogType.ERROR,
            message = message,
            onConfirm = { _uiState.value.messageDialogState.onHideDialog() },
            onCancel = { _uiState.value.messageDialogState.onHideDialog() }
        )
    }
}