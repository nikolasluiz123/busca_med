package br.com.android.buscamed.domain.model.capture

/**
 * Representa um elemento de texto, que tipicamente corresponde a uma única palavra, número ou agrupamento contínuo.
 *
 * @property text O texto contido no elemento.
 * @property boundingBox As coordenadas espaciais do elemento na imagem, ou null se não for possível determinar.
 * @property confidence O grau de confiança da extração (0.0 a 1.0), ou null se o motor de OCR não fornecer.
 * @property symbols Lista de símbolos (caracteres) individuais que compõem este elemento.
 */
data class TextElement(
    val text: String,
    val boundingBox: BoundingBox?,
    val confidence: Float?,
    val symbols: List<TextSymbol>
)