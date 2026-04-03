package br.com.android.buscamed.domain.rule

import br.com.android.buscamed.domain.model.capture.TextResult

/**
 * Regra que avalia a confiança média apenas dos elementos localizados na região geométrica central
 * da área total de texto detetado.
 *
 * @property minimumCenterAverage A média mínima de confiança exigida para a zona central.
 * @property centerAreaRatio A proporção da área considerada como "centro" (ex: 0.5 representa os 50% centrais).
 */
class CentralFocusConfidenceRule(
    private val minimumCenterAverage: Float = 0.7f,
    private val centerAreaRatio: Float = 0.5f
) : ConfidenceRule {
    override fun evaluate(textResult: TextResult): Boolean {
        val allElements = textResult.blocks.flatMap { it.lines }.flatMap { it.elements }
        if (allElements.isEmpty()) return false

        val lefts = allElements.mapNotNull { it.boundingBox?.left }
        val tops = allElements.mapNotNull { it.boundingBox?.top }
        val rights = allElements.mapNotNull { it.boundingBox?.right }
        val bottoms = allElements.mapNotNull { it.boundingBox?.bottom }

        if (lefts.isEmpty() || tops.isEmpty() || rights.isEmpty() || bottoms.isEmpty()) return true

        val minLeft = lefts.minOrNull() ?: 0f
        val minTop = tops.minOrNull() ?: 0f
        val maxRight = rights.maxOrNull() ?: 0f
        val maxBottom = bottoms.maxOrNull() ?: 0f

        val width = maxRight - minLeft
        val height = maxBottom - minTop

        val marginX = (width * (1f - centerAreaRatio)) / 2f
        val marginY = (height * (1f - centerAreaRatio)) / 2f

        val centerLeft = minLeft + marginX
        val centerTop = minTop + marginY
        val centerRight = maxRight - marginX
        val centerBottom = maxBottom - marginY

        val centralElements = allElements.filter { element ->
            val box = element.boundingBox ?: return@filter false
            
            val elementCenterX = box.left + (box.right - box.left) / 2f
            val elementCenterY = box.top + (box.bottom - box.top) / 2f
            
            elementCenterX in centerLeft..centerRight && elementCenterY in centerTop..centerBottom
        }

        if (centralElements.isEmpty()) return false

        val elementsWithConfidence = centralElements.filter { it.confidence != null }
        if (elementsWithConfidence.isEmpty()) return true

        val average = elementsWithConfidence.map { it.confidence!! }.average()
        return average >= minimumCenterAverage
    }
}