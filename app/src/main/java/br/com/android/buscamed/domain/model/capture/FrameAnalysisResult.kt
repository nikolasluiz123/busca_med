package br.com.android.buscamed.domain.model.capture

/**
 * Encapsula o resultado da análise de um quadro.
 *
 * @param T O tipo de dados extraído durante a análise.
 * @property state Estado atual da deteção no quadro.
 * @property boundingBox Área delimitadora do alvo detetado.
 * @property sourceDimensions Dimensões da imagem original.
 * @property payload Dados adicionais extraídos do quadro.
 */
data class FrameAnalysisResult<T>(
    val state: AnalyzerState = AnalyzerState.NOT_DETECTED,
    val boundingBox: BoundingBox? = null,
    val sourceDimensions: ImageDimension? = null,
    val payload: T? = null
)