package br.com.android.buscamed.domain.model.capture

/**
 * Representa um bloco de texto, geralmente correspondendo a um parágrafo ou agrupamento lógico de linhas.
 *
 * @property text O texto completo contido no bloco.
 * @property boundingBox As coordenadas espaciais do bloco na imagem, ou null se não for possível determinar.
 * @property lines Lista de linhas de texto contidas neste bloco.
 */
data class TextBlock(
    val text: String,
    val boundingBox: BoundingBox?,
    val lines: List<TextLine>
)