package br.com.android.buscamed.presentation.screen.capture.state

import br.com.android.buscamed.domain.model.capture.AnalyzerState
import br.com.android.buscamed.domain.model.capture.BoundingBox
import br.com.android.buscamed.domain.model.capture.ImageDimension

data class PrescriptionCaptureUIState(
    val analyzerState: AnalyzerState = AnalyzerState.NO_DOCUMENT,
    val textBoundingBox: BoundingBox? = null,
    val sourceDimensions: ImageDimension? = null,
    val isCaptureButtonEnabled: Boolean = false,
    val isCapturing: Boolean = false
)