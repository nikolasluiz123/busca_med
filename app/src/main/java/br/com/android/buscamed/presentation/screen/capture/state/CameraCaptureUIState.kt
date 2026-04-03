package br.com.android.buscamed.presentation.screen.capture.state

/**
 * Representa o estado da interface da tela de captura.
 *
 * @property analyzerState Estado atual do analisador de quadros.
 * @property isCaptureButtonEnabled Define se a captura manual está habilitada.
 */
data class CameraCaptureUIState(
    val analyzerState: AnalyzerState = AnalyzerState.NO_DOCUMENT,
    val isCaptureButtonEnabled: Boolean = false
)