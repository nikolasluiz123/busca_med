package br.com.android.buscamed.presentation.screen.capture.state

/**
 * Encapsula o resultado completo de uma análise de frame.
 */
data class FrameAnalysisResult(
    val analyzerState: AnalyzerState = AnalyzerState.NO_DOCUMENT,
    val boundingBox: BoundingBox? = null
)