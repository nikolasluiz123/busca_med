package br.com.android.buscamed.domain.rule

import br.com.android.buscamed.domain.model.capture.TextResult

/**
 * Regra que valida se a média global de confiança de todos os elementos atinge um valor mínimo.
 *
 * @property minimumAverage A média mínima de confiança exigida (entre 0.0 e 1.0).
 */
class GlobalAverageConfidenceRule(
    private val minimumAverage: Float = 0.6f
) : ConfidenceRule {
    override fun evaluate(textResult: TextResult): Boolean {
        val allElements = textResult.blocks.flatMap { it.lines }.flatMap { it.elements }
        if (allElements.isEmpty()) return false

        val elementsWithConfidence = allElements.filter { it.confidence != null }
        if (elementsWithConfidence.isEmpty()) return true

        val average = elementsWithConfidence.map { it.confidence!! }.average()
        return average >= minimumAverage
    }
}