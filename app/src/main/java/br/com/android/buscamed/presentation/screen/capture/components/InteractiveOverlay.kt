package br.com.android.buscamed.presentation.screen.capture.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import br.com.android.buscamed.presentation.screen.capture.state.BoundingBox

/**
 * Desenha o overlay dinâmico para orientação de captura.
 *
 * @param overlayColor Cor atual da borda guia.
 */
@Composable
fun InteractiveOverlay(
    overlayColor: Color,
    dynamicBox: BoundingBox?,
    modifier: Modifier = Modifier
) {
    val defaultLeft = 0.075f
    val defaultTop = 0.325f
    val defaultRight = 0.925f
    val defaultBottom = 0.675f

    val paddingMultiplier = 0.05f

    val targetLeft = (dynamicBox?.left?.minus(paddingMultiplier)) ?: defaultLeft
    val targetTop = (dynamicBox?.top?.minus(paddingMultiplier)) ?: defaultTop
    val targetRight = (dynamicBox?.right?.plus(paddingMultiplier)) ?: defaultRight
    val targetBottom = (dynamicBox?.bottom?.plus(paddingMultiplier)) ?: defaultBottom

    val animationSpec = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    val animatedLeft by animateFloatAsState(targetValue = targetLeft, animationSpec = animationSpec, label = "leftAnim")
    val animatedTop by animateFloatAsState(targetValue = targetTop, animationSpec = animationSpec, label = "topAnim")
    val animatedRight by animateFloatAsState(targetValue = targetRight, animationSpec = animationSpec, label = "rightAnim")
    val animatedBottom by animateFloatAsState(targetValue = targetBottom, animationSpec = animationSpec, label = "bottomAnim")

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val safeLeft = animatedLeft.coerceIn(0f, 1f) * canvasWidth
        val safeTop = animatedTop.coerceIn(0f, 1f) * canvasHeight
        val safeRight = animatedRight.coerceIn(0f, 1f) * canvasWidth
        val safeBottom = animatedBottom.coerceIn(0f, 1f) * canvasHeight

        val rectWidth = safeRight - safeLeft
        val rectHeight = safeBottom - safeTop

        drawRect(
            color = Color.Black.copy(alpha = 0.55f),
            size = size
        )

        drawRoundRect(
            color = Color.Transparent,
            topLeft = Offset(safeLeft, safeTop),
            size = Size(rectWidth, rectHeight),
            cornerRadius = CornerRadius(16f, 16f),
            blendMode = BlendMode.Clear
        )

        drawRoundRect(
            color = overlayColor,
            topLeft = Offset(safeLeft, safeTop),
            size = Size(rectWidth, rectHeight),
            cornerRadius = CornerRadius(16f, 16f),
            style = Stroke(width = 8f)
        )
    }
}