package br.com.android.buscamed.presentation.viewmodel

import android.util.Log
import androidx.camera.core.ImageProxy
import br.com.android.buscamed.domain.analyzer.FrameAnalyzer
import br.com.android.buscamed.domain.model.capture.AnalyzerState
import br.com.android.buscamed.domain.usecase.prescription.ReadPrescriptionUseCase
import br.com.android.buscamed.presentation.core.state.enumeration.EnumDialogType
import br.com.android.buscamed.presentation.screen.capture.state.PrescriptionCaptureUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File
import javax.inject.Inject

/**
 * ViewModel responsável pela lógica de captura e processamento de prescrições médicas.
 */
@HiltViewModel
class PrescriptionCaptureViewModel @Inject constructor(
    private val frameAnalyzer: FrameAnalyzer<ImageProxy>,
    private val readPrescriptionUseCase: ReadPrescriptionUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(PrescriptionCaptureUIState())
    val uiState: StateFlow<PrescriptionCaptureUIState> = _uiState.asStateFlow()

    init {
        initialLoadUIState()
    }

    override fun initialLoadUIState() {
        val dialogState = createMessageDialogState(
            getCurrentState = { _uiState.value.messageDialogState },
            updateState = { newState ->
                _uiState.value = _uiState.value.copy(messageDialogState = newState)
            }
        )
        _uiState.value = _uiState.value.copy(messageDialogState = dialogState)
        observeAnalyzerState()
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return throwable.message ?: "Ocorreu um erro desconhecido ao processar a imagem."
    }

    override fun onShowErrorDialog(message: String) {
        _uiState.value.messageDialogState.onShowDialog?.onShow(
            type = EnumDialogType.ERROR,
            message = message,
            onConfirm = { _uiState.value.messageDialogState.onHideDialog() },
            onCancel = { _uiState.value.messageDialogState.onHideDialog() }
        )
    }

    /**
     * Limpa o estado da tela, preparando-a para uma nova captura sem vestígios
     * do processamento anterior.
     */
    fun resetState() {
        _uiState.value = _uiState.value.copy(
            isCapturing = false,
            prescription = null
        )
    }

    private fun observeAnalyzerState() {
        launch {
            frameAnalyzer.state.collect { result ->
                _uiState.update { currentState ->
                    if (currentState.isCapturing) {
                        currentState
                    } else {
                        currentState.copy(
                            analyzerState = result.state,
                            textBoundingBox = result.boundingBox,
                            sourceDimensions = result.sourceDimensions,
                            isCaptureButtonEnabled = result.state == AnalyzerState.ALIGNED
                        )
                    }
                }
            }
        }
    }

    fun analyze(imageProxy: ImageProxy) {
        frameAnalyzer.analyze(imageProxy)
    }

    fun onCaptureStarted() {
        _uiState.value = _uiState.value.copy(
            isCapturing = true,
            isCaptureButtonEnabled = false,
            textBoundingBox = null,
            analyzerState = AnalyzerState.NO_DOCUMENT
        )
    }

    fun onPictureTaken(imagePath: String) {
        launch {
            try {
                readPrescriptionUseCase(imagePath).fold(
                    onSuccess = { prescriptionResult ->
                        _uiState.value = _uiState.value.copy(
                            isCapturing = false,
                            prescription = prescriptionResult
                        )
                    },
                    onFailure = { error ->
                        _uiState.value = _uiState.value.copy(
                            isCapturing = false,
                            isCaptureButtonEnabled = true
                        )
                        handleUnexpectedError(error)
                    }
                )
            } finally {
                clearImageCache(imagePath)
            }
        }
    }

    private fun clearImageCache(imagePath: String) {
        try {
            val file = File(imagePath)
            if (file.exists()) {
                file.delete()
            }
        } catch (e: Exception) {
            Log.e("CameraCaptureViewModel", "Falha ao limpar cache de imagem", e)
        }
    }

    fun onCaptureError(exception: Exception) {
        Log.e("CameraCaptureViewModel", "Falha na captura", exception)
        _uiState.value = _uiState.value.copy(
            isCapturing = false,
            isCaptureButtonEnabled = true
        )
        handleUnexpectedError(exception)
    }

    override fun onCleared() {
        super.onCleared()
        frameAnalyzer.close()
    }
}