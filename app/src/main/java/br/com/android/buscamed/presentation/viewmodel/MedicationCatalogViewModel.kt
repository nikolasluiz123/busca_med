package br.com.android.buscamed.presentation.viewmodel

import android.content.Context
import br.com.android.buscamed.R
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.usecase.medication.GetPaginatedMedicationsUseCase
import br.com.android.buscamed.presentation.core.components.dialog.showErrorDialog
import br.com.android.buscamed.presentation.screen.medication.state.MedicationCatalogUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel responsável por gerenciar a lista paginada de medicamentos.
 */
@HiltViewModel
class MedicationCatalogViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val getPaginatedMedicationsUseCase: GetPaginatedMedicationsUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(MedicationCatalogUIState())
    val uiState get() = _uiState.asStateFlow()

    init {
        initialLoadUIState()
        loadMedications()
    }

    override fun initialLoadUIState() {
        _uiState.value = _uiState.value.copy(
            messageDialogState = createMessageDialogState(
                getCurrentState = { _uiState.value.messageDialogState },
                updateState = { _uiState.value = _uiState.value.copy(messageDialogState = it) }
            ),
            onToggleLoading = {
                _uiState.value = _uiState.value.copy(showLoading = !_uiState.value.showLoading)
            }
        )
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return context.getString(R.string.validation_error_unknown)
    }

    override fun onShowErrorDialog(message: String) {
        _uiState.value.messageDialogState.onShowDialog?.showErrorDialog(message)
    }

    /**
     * Carrega a próxima página de medicamentos do repositório.
     */
    fun loadMedications() {
        if (!_uiState.value.hasMoreItems || _uiState.value.showLoading) return

        launch {
            _uiState.value.onToggleLoading()

            val result = getPaginatedMedicationsUseCase(
                limit = 20,
                cursor = _uiState.value.cursor
            )

            when (result) {
                is UseCaseResult.Success -> {
                    val paginatedData = result.data
                    if (paginatedData != null) {
                        _uiState.value = _uiState.value.copy(
                            medications = _uiState.value.medications + paginatedData.items,
                            cursor = paginatedData.cursor,
                            hasMoreItems = paginatedData.cursor != null
                        )
                    }
                }
                is UseCaseResult.Error -> {
                    logValidationWarnings(result.errors)
                    onShowErrorDialog(context.getString(R.string.validation_error_unknown))
                }
            }

            _uiState.value.onToggleLoading()
        }
    }
}