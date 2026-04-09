package br.com.android.buscamed.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import br.com.android.buscamed.R
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.usecase.medication.GetMedicationPatientLeafletUseCase
import br.com.android.buscamed.domain.usecase.medication.GetMedicationProfessionalLeafletUseCase
import br.com.android.buscamed.presentation.core.components.dialog.showErrorDialog
import br.com.android.buscamed.presentation.core.extensions.fromJsonNavParamToArgs
import br.com.android.buscamed.presentation.screen.medication.MedicationLeafletScreenArgs
import br.com.android.buscamed.presentation.screen.medication.medicationLeafletArguments
import br.com.android.buscamed.presentation.screen.medication.state.MedicationLeafletUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.jvm.java

/**
 * ViewModel responsável por gerenciar a exibição das bulas de um medicamento.
 */
@HiltViewModel
class MedicationLeafletViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val getPatientLeafletUseCase: GetMedicationPatientLeafletUseCase,
    private val getProfessionalLeafletUseCase: GetMedicationProfessionalLeafletUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(MedicationLeafletUIState())
    val uiState get() = _uiState.asStateFlow()

    private val jsonArgs: String? = savedStateHandle[medicationLeafletArguments]

    init {
        initialLoadUIState()
        loadLeaflets()
    }

    override fun initialLoadUIState() {
        val args = jsonArgs?.fromJsonNavParamToArgs(MedicationLeafletScreenArgs::class.java)

        _uiState.value = _uiState.value.copy(
            medicationId = args?.medicationId,
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

    private fun loadLeaflets() {
        val medicationId = _uiState.value.medicationId ?: return

        launch {
            _uiState.value.onToggleLoading()

            when (val patientResult = getPatientLeafletUseCase(medicationId)) {
                is UseCaseResult.Success -> {
                    _uiState.value = _uiState.value.copy(patientLeaflet = patientResult.data)
                }
                is UseCaseResult.Error -> logValidationWarnings(patientResult.errors)
            }

            when (val professionalResult = getProfessionalLeafletUseCase(medicationId)) {
                is UseCaseResult.Success -> {
                    _uiState.value = _uiState.value.copy(professionalLeaflet = professionalResult.data)
                }
                is UseCaseResult.Error -> logValidationWarnings(professionalResult.errors)
            }

            _uiState.value.onToggleLoading()
        }
    }
}