package br.com.android.buscamed.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.android.buscamed.presentation.screen.capture.analyzer.FrameAnalyzer
import br.com.android.buscamed.presentation.screen.capture.state.AnalyzerState
import br.com.android.buscamed.presentation.screen.capture.state.CameraCaptureUIState
import br.com.android.buscamed.presentation.viewmodel.core.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsável pela lógica de apresentação da tela de captura.
 */
@HiltViewModel
class CameraCaptureViewModel @Inject constructor(
    val frameAnalyzer: FrameAnalyzer
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(CameraCaptureUIState())
    val uiState: StateFlow<CameraCaptureUIState> = _uiState.asStateFlow()

    init {
        initialLoadUIState()
    }

    override fun initialLoadUIState() {
        observeAnalyzerState()
    }

    override fun getErrorMessageFrom(throwable: Throwable): String {
        return ""
    }

    override fun onShowErrorDialog(message: String) {

    }

    private fun observeAnalyzerState() {
        viewModelScope.launch {
            frameAnalyzer.state.collect { result ->
                _uiState.update { currentState ->
                    if (currentState.isCapturing) {
                        currentState
                    } else {
                        currentState.copy(
                            analyzerState = result.analyzerState,
                            textBoundingBox = result.boundingBox,
                            isCaptureButtonEnabled = result.analyzerState == AnalyzerState.ALIGNED_AND_READY
                        )
                    }
                }
            }
        }
    }

    /**
     * Sinaliza o início do processo de captura da imagem, congelando a interface.
     */
    fun onCaptureStarted() {
        _uiState.update { currentState ->
            currentState.copy(
                isCapturing = true,
                isCaptureButtonEnabled = false
            )
        }
    }

    /**
     * Acionado após a captura bem-sucedida da imagem em alta resolução.
     *
     * @param imagePath Caminho absoluto do arquivo salvo no dispositivo.
     */
    fun onPictureTaken(imagePath: String) {
        Log.d("CameraCaptureViewModel", "Imagem capturada com sucesso: $imagePath")
        Log.d("CameraCaptureViewModel", "Iniciando chamada para o pipeline do domínio (OCR)...")

        // Aqui o estado isCapturing permanece true, pois a tela deve continuar congelada
        // enquanto navega para a próxima tela ou inicia o processamento do domínio.
    }

    /**
     * Acionado em caso de falha no ImageCapture.
     */
    fun onCaptureError(exception: Exception) {
        Log.e("CameraCaptureViewModel", "Falha na captura da imagem", exception)
        _uiState.update { currentState ->
            currentState.copy(
                isCapturing = false,
                isCaptureButtonEnabled = true
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        frameAnalyzer.clear()
    }

}