package br.com.android.buscamed.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import br.com.android.buscamed.data.gson.defaultGSon
import br.com.android.buscamed.presentation.core.extensions.fromJsonNavParamToArgs
import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType
import br.com.android.buscamed.presentation.screen.pillpack.PillPackResultScreenArgs
import br.com.android.buscamed.presentation.screen.pillpack.pillPackResultArguments
import br.com.android.buscamed.presentation.state.PillPackResultUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel responsável por gerenciar o estado da tela de resultados da cartela de comprimidos.
 */
@HiltViewModel
class PillPackResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(PillPackResultUIState())
    val uiState: StateFlow<PillPackResultUIState> = _uiState.asStateFlow()

    private val jsonArgs: String? = savedStateHandle[pillPackResultArguments]

    init {
        initialLoadUIState()
    }

    override fun initialLoadUIState() {
        val gson = GsonBuilder().defaultGSon()
        val args = jsonArgs?.fromJsonNavParamToArgs(PillPackResultScreenArgs::class.java, gson)

        val dialogState = createMessageDialogState(
            getCurrentState = { _uiState.value.messageDialogState },
            updateState = { newState ->
                _uiState.value = _uiState.value.copy(messageDialogState = newState)
            }
        )

        _uiState.value = _uiState.value.copy(
            messageDialogState = dialogState,
            pillPack = args?.pillPack
        )
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return throwable.message ?: "Erro ao carregar os dados da cartela."
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