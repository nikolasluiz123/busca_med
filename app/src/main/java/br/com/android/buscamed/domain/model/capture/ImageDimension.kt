package br.com.android.buscamed.domain.model.capture

/**
 * Representa as dimensões originais de uma imagem processada.
 *
 * @property width Largura original da imagem em pixels.
 * @property height Altura original da imagem em pixels.
 */
data class ImageDimension(
    val width: Int,
    val height: Int
)