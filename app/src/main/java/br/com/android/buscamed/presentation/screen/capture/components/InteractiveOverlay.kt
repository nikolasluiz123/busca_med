package br.com.android.buscamed.presentation.screen.capture.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke

/**
 * Desenha o overlay dinâmico para orientação de captura.
 *
 * @param overlayColor Cor atual da borda guia.
 */
@Composable
fun InteractiveOverlay(
    overlayColor: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val rectWidth = canvasWidth * 0.85f
        val rectHeight = canvasHeight * 0.35f

        val offsetX = (canvasWidth - rectWidth) / 2
        val offsetY = (canvasHeight - rectHeight) / 2

        drawRect(
            color = Color.Black.copy(alpha = 0.55f),
            size = size
        )

        drawRoundRect(
            color = Color.Transparent,
            topLeft = Offset(offsetX, offsetY),
            size = Size(rectWidth, rectHeight),
            cornerRadius = CornerRadius(16f, 16f),
            blendMode = BlendMode.Clear
        )

        drawRoundRect(
            color = overlayColor,
            topLeft = Offset(offsetX, offsetY),
            size = Size(rectWidth, rectHeight),
            cornerRadius = CornerRadius(16f, 16f),
            style = Stroke(width = 8f)
        )
    }
}