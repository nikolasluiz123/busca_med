package br.com.android.buscamed.presentation.screen.capture.state

/**
 * Representa o estado da interface da tela de captura.
 *
 * @property analyzerState Estado atual do analisador de quadros.
 * @property textBoundingBox Coordenadas normalizadas da área de texto detectada.
 * @property isCaptureButtonEnabled Define se a captura manual está habilitada.
 * @property isCapturing Indica se a câmera está no processo de salvar a foto de alta resolução.
 */
data class CameraCaptureUIState(
    val analyzerState: AnalyzerState = AnalyzerState.NO_DOCUMENT,
    val textBoundingBox: BoundingBox? = null,
    val isCaptureButtonEnabled: Boolean = false,
    val isCapturing: Boolean = false
)