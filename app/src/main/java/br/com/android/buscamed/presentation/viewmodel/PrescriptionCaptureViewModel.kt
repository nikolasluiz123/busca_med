package br.com.android.buscamed.presentation.viewmodel

import android.util.Log
import androidx.camera.core.ImageProxy
import br.com.android.buscamed.domain.analyzer.FrameAnalyzer
import br.com.android.buscamed.domain.model.capture.AnalyzerState
import br.com.android.buscamed.domain.usecase.prescription.ReadPrescriptionUseCase
import br.com.android.buscamed.presentation.screen.capture.state.PrescriptionCaptureUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

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
        observeAnalyzerState()
    }

    override fun getErrorMessageFrom(throwable: Throwable): String = ""

    override fun onShowErrorDialog(message: String) {}

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
        _uiState.update { it.copy(isCapturing = true, isCaptureButtonEnabled = false) }
    }

    fun onPictureTaken(imagePath: String) {
        launch {
            readPrescriptionUseCase(imagePath)
        }
    }

    fun onCaptureError(exception: Exception) {
        Log.e("CameraCaptureViewModel", "Falha na captura", exception)
        _uiState.update { it.copy(isCapturing = false, isCaptureButtonEnabled = true) }
    }
}