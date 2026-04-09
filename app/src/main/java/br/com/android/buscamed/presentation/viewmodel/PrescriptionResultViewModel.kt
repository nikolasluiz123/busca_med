package br.com.android.buscamed.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import br.com.android.buscamed.presentation.core.extensions.fromJsonNavParamToArgs
import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType
import br.com.android.buscamed.presentation.screen.prescription.PrescriptionResultScreenArgs
import br.com.android.buscamed.presentation.screen.prescription.prescriptionResultArguments
import br.com.android.buscamed.presentation.screen.prescription.PrescriptionResultUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel responsável por gerenciar o estado da tela de resultados da prescrição.
 */
@HiltViewModel
class PrescriptionResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(PrescriptionResultUIState())
    val uiState: StateFlow<PrescriptionResultUIState> = _uiState.asStateFlow()

    private val jsonArgs: String? = savedStateHandle[prescriptionResultArguments]

    init {
        initialLoadUIState()
    }

    override fun initialLoadUIState() {
        val args = jsonArgs?.fromJsonNavParamToArgs(PrescriptionResultScreenArgs::class.java)!!

        val dialogState = createMessageDialogState(
            getCurrentState = { _uiState.value.messageDialogState },
            updateState = { newState ->
                _uiState.value = _uiState.value.copy(messageDialogState = newState)
            }
        )
        _uiState.value = _uiState.value.copy(
            messageDialogState = dialogState,
            prescription = args.prescription
        )

        Log.i("Teste", "initialLoadUIState: ${args.prescription.medications.map { it.name }}")
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return throwable.message ?: "Erro ao carregar os dados da prescrição."
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