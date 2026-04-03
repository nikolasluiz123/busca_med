package br.com.android.buscamed.presentation.screen.capture.state

import androidx.compose.ui.graphics.Color

/**
 * Representa os estados de alinhamento do documento durante a análise de quadros da câmera.
 *
 * @property overlayColor Cor que será refletida no overlay da interface visual.
 */
enum class AnalyzerState(val overlayColor: Color) {
    NO_DOCUMENT(Color.Red),
    PARTIAL_DOCUMENT(Color.Yellow),
    ALIGNED_AND_READY(Color.Green)
}