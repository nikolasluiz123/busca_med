package br.com.android.buscamed.domain.processor

import br.com.android.buscamed.domain.model.capture.TextBlock
import br.com.android.buscamed.domain.model.capture.TextElement
import br.com.android.buscamed.domain.model.capture.TextLine
import br.com.android.buscamed.domain.model.capture.TextResult
import kotlin.math.abs

/**
 * Processador de texto que reconstrói linhas com base na proximidade espacial vertical (Eixo Y).
 *
 * @property verticalTolerancePixels A margem de tolerância em pixels para considerar que
 * dois elementos pertencem à mesma linha física.
 */
class SpatialReconstructionProcessorStep(
    private val verticalTolerancePixels: Int = 30
) : TextResultProcessorStep {

    override fun process(textResult: TextResult): TextResult {
        val allElements = textResult.blocks.flatMap { block ->
            block.lines.flatMap { line -> line.elements }
        }

        if (allElements.isEmpty()) return textResult

        val elementsSortedByTop = allElements.sortedBy { it.boundingBox?.top ?: 0f }
        val clusteredLines = mutableListOf<MutableList<TextElement>>()

        for (element in elementsSortedByTop) {
            val elementTop = element.boundingBox?.top ?: 0f

            val matchingLine = clusteredLines.find { lineGroup ->
                val lineAverageTop = lineGroup.mapNotNull { it.boundingBox?.top }.average().toInt()
                abs(elementTop - lineAverageTop) <= verticalTolerancePixels
            }

            if (matchingLine != null) {
                matchingLine.add(element)
            } else {
                clusteredLines.add(mutableListOf(element))
            }
        }

        val reconstructedTextLines = clusteredLines.map { lineGroup ->
            val sortedLeftToRight = lineGroup.sortedBy { it.boundingBox?.left ?: 0f }
            val fullLineText = sortedLeftToRight.joinToString(" ") { it.text }

            val elementsWithConfidence = sortedLeftToRight.filter { it.confidence != null }
            val lineConfidence = if (elementsWithConfidence.isNotEmpty()) {
                elementsWithConfidence.map { it.confidence!! }.average().toFloat()
            } else {
                null
            }

            TextLine(
                text = fullLineText,
                elements = sortedLeftToRight,
                boundingBox = null,
                confidence = lineConfidence
            )
        }

        val reconstructedBlock = TextBlock(
            text = reconstructedTextLines.joinToString("\n") { it.text },
            lines = reconstructedTextLines,
            boundingBox = null
        )

        return TextResult(
            text = reconstructedBlock.text,
            blocks = listOf(reconstructedBlock)
        )
    }
}