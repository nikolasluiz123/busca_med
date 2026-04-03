package br.com.android.buscamed.domain.model.capture

/**
 * Representa as coordenadas espaciais de um elemento detectado na imagem.
 * As coordenadas são relativas à imagem original processada, não à tela do dispositivo.
 *
 * @property left Posição da borda esquerda no eixo X.
 * @property top Posição da borda superior no eixo Y.
 * @property right Posição da borda direita no eixo X.
 * @property bottom Posição da borda inferior no eixo Y.
 */
data class BoundingBox(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
)