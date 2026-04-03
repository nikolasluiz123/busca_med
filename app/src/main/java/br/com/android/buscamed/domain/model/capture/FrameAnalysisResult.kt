package br.com.android.buscamed.domain.model.capture

/**
 * Encapsula o resultado de uma análise individual de quadro de imagem.
 *
 * @property state O estado de alinhamento detectado.
 * @property boundingBox As coordenadas do bloco de texto detectado, se houver.
 * @property sourceDimensions As dimensões originais do quadro de imagem que foi analisado.
 */
data class FrameAnalysisResult(
    val state: AnalyzerState = AnalyzerState.NO_DOCUMENT,
    val boundingBox: BoundingBox? = null,
    val sourceDimensions: ImageDimension? = null
)