package br.com.android.buscamed.domain.model.capture

/**
 * Representa um símbolo único, que é a menor unidade de texto reconhecível (um caractere, número ou pontuação).
 *
 * @property text A representação em texto do símbolo (normalmente um único caractere).
 * @property boundingBox As coordenadas espaciais do símbolo na imagem, ou null se não for possível determinar.
 * @property confidence O grau de confiança da extração (0.0 a 1.0), ou null se o motor de OCR não fornecer.
 */
data class TextSymbol(
    val text: String,
    val boundingBox: BoundingBox?,
    val confidence: Float?
)