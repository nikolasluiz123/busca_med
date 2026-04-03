package br.com.android.buscamed.presentation.viewmodel

import android.content.Context
import br.com.android.buscamed.R
import br.com.android.buscamed.presentation.state.HomeUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
): BaseViewModel() {
    private val _uiState: MutableStateFlow<HomeUIState> = MutableStateFlow(HomeUIState())
    val uiState get() = _uiState.asStateFlow()

    init {
        initialLoadUIState()
    }

    override fun initialLoadUIState() {
        _uiState.value = _uiState.value.copy(
            messageDialogState = createMessageDialogState(
                getCurrentState = { _uiState.value.messageDialogState },
                updateState = { _uiState.value = _uiState.value.copy(messageDialogState = it) }
            ),
            onToggleLoading = {
                _uiState.value = _uiState.value.copy(showLoading = !_uiState.value.showLoading)
            },
        )
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return context.getString(R.string.validation_error_unknown)
    }

    override fun onShowErrorDialog(message: String) {
        // Implementação básica para exibir erro se necessário
    }
}