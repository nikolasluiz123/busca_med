package br.com.android.buscamed.presentation.viewmodel

import android.util.Log
import androidx.camera.core.ImageProxy
import androidx.lifecycle.viewModelScope
import br.com.android.buscamed.domain.analyzer.FrameAnalyzer
import br.com.android.buscamed.domain.model.capture.AnalyzerState
import br.com.android.buscamed.presentation.screen.capture.state.CameraCaptureUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraCaptureViewModel @Inject constructor(
    val frameAnalyzer: FrameAnalyzer<ImageProxy>
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CameraCaptureUIState())
    val uiState: StateFlow<CameraCaptureUIState> = _uiState.asStateFlow()

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

    fun onCaptureStarted() {
        _uiState.update { it.copy(isCapturing = true, isCaptureButtonEnabled = false) }
    }

    fun onPictureTaken(imagePath: String) {
        Log.d("CameraCaptureViewModel", "Imagem capturada com sucesso: $imagePath")
    }

    fun onCaptureError(exception: Exception) {
        Log.e("CameraCaptureViewModel", "Falha na captura", exception)
        _uiState.update { it.copy(isCapturing = false, isCaptureButtonEnabled = true) }
    }
}