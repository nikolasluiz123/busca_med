package br.com.android.buscamed.presentation.viewmodel

import android.content.Context
import androidx.camera.core.ImageProxy
import androidx.lifecycle.viewModelScope
import br.com.android.buscamed.R
import br.com.android.buscamed.domain.analyzer.FrameAnalyzer
import br.com.android.buscamed.domain.core.validation.GeneralValidationError
import br.com.android.buscamed.domain.core.validation.UseCaseResult
import br.com.android.buscamed.domain.core.validation.ValidationError
import br.com.android.buscamed.domain.model.capture.AnalyzerState
import br.com.android.buscamed.domain.usecase.medication.SearchMedicationByBarcodeUseCase
import br.com.android.buscamed.domain.usecase.medication.enumeration.BarcodeGeneralErrorType
import br.com.android.buscamed.injection.BarcodeAnalyzer
import br.com.android.buscamed.presentation.core.components.dialog.showErrorDialog
import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType
import br.com.android.buscamed.presentation.screen.capture.state.BarcodeCaptureUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsável por gerir a lógica de captura e busca de medicamentos por código de barras.
 *
 * @property context Contexto da aplicação utilizado para resolução de strings de recursos.
 * @property frameAnalyzer Analisador contínuo de quadros para extração do código de barras.
 * @property searchMedicationUseCase Caso de uso para consultar o repositório utilizando o EAN.
 */
@HiltViewModel
class BarcodeCaptureViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    @param:BarcodeAnalyzer private val frameAnalyzer: FrameAnalyzer<ImageProxy, String>,
    private val searchMedicationUseCase: SearchMedicationByBarcodeUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(BarcodeCaptureUIState())
    val uiState get() = _uiState.asStateFlow()

    init {
        initialLoadUIState()
        observeAnalyzerState()
    }

    override fun initialLoadUIState() {
        _uiState.value = _uiState.value.copy(
            messageDialogState = createMessageDialogState(
                getCurrentState = { _uiState.value.messageDialogState },
                updateState = { _uiState.value = _uiState.value.copy(messageDialogState = it) }
            ),
        )
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return context.getString(R.string.validation_error_unknown)
    }

    override fun onShowErrorDialog(message: String) {
        _uiState.value.messageDialogState.onShowDialog?.showErrorDialog(message = message)
    }

    /**
     * Envia um quadro da câmara para análise.
     *
     * @param imageProxy O quadro a ser analisado.
     */
    fun analyzeFrame(imageProxy: ImageProxy) {
        if (_uiState.value.isScanning) {
            frameAnalyzer.analyze(imageProxy)
        } else {
            imageProxy.close()
        }
    }

    /**
     * Limpa o estado de busca e retoma o processamento da câmara.
     */
    fun resumeScanning() {
        _uiState.value = _uiState.value.copy(
            isScanning = true,
            isSearching = false,
            capturedBarcode = null,
            searchResult = null,
            analyzerState = AnalyzerState.NOT_DETECTED,
            boundingBox = null
        )
    }

    private fun observeAnalyzerState() {
        viewModelScope.launch {
            frameAnalyzer.state.collect { result ->
                if (!_uiState.value.isScanning) return@collect

                _uiState.value = _uiState.value.copy(
                    analyzerState = result.state,
                    boundingBox = result.boundingBox,
                    sourceDimensions = result.sourceDimensions
                )

                if (result.state == AnalyzerState.ALIGNED && result.payload != null) {
                    processCapturedBarcode(result.payload)
                }
            }
        }
    }

    private fun processCapturedBarcode(barcode: String) {
        _uiState.value = _uiState.value.copy(
            isScanning = false,
            isSearching = true,
            capturedBarcode = barcode
        )

        launch {
            when (val result = searchMedicationUseCase(barcode)) {
                is UseCaseResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isSearching = false,
                        searchResult = result.data
                    )
                }
                is UseCaseResult.Error -> {
                    logValidationWarnings(result.errors)
                    showValidationMessages(result.errors)

                    _uiState.value = _uiState.value.copy(
                        isSearching = false
                    )
                }
            }
        }
    }

    private fun showValidationMessages(errors: List<ValidationError>) {
        errors.forEach { error ->
            if (error is GeneralValidationError<*>) {
                val errorMessage = getGeneralErrorMessage(error.type as BarcodeGeneralErrorType)
                _uiState.value.messageDialogState.onShowDialog?.onShow(
                    EnumDialogType.ERROR,
                    errorMessage,
                    { resumeScanning() },
                    { resumeScanning() }
                )
            }
        }
    }

    private fun getGeneralErrorMessage(type: BarcodeGeneralErrorType): String {
        return when (type) {
            BarcodeGeneralErrorType.INVALID_BARCODE -> {
                context.getString(R.string.barcode_capture_error_invalid_barcode)
            }
            BarcodeGeneralErrorType.MEDICATION_NOT_FOUND -> {
                context.getString(R.string.barcode_capture_error_medication_not_found)
            }
            BarcodeGeneralErrorType.NETWORK_ERROR -> {
                context.getString(R.string.validation_error_network)
            }
            BarcodeGeneralErrorType.UNKNOWN_ERROR -> {
                context.getString(R.string.barcode_capture_error_unknown)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        frameAnalyzer.close()
    }
}