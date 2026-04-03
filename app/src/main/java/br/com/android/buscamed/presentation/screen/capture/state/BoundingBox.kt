package br.com.android.buscamed.presentation.screen.capture.state

/**
 * Representa uma área demarcada utilizando coordenadas normalizadas (0.0f a 1.0f).
 */
data class BoundingBox(
    val left: Float,
    val top: Float,
    val right: Float,
    val bottom: Float
)