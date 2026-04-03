package br.com.android.buscamed.domain.model.capture

/**
 * Representa uma linha de texto estruturada dentro de um bloco.
 *
 * @property text O texto completo contido na linha.
 * @property boundingBox As coordenadas espaciais da linha na imagem, ou null se não for possível determinar.
 * @property confidence O grau de confiança da extração (0.0 a 1.0), ou null se o motor de OCR não fornecer.
 * @property elements Lista de elementos (palavras ou agregações menores) contidos nesta linha.
 */
data class TextLine(
    val text: String,
    val boundingBox: BoundingBox?,
    val confidence: Float?,
    val elements: List<TextElement>
)