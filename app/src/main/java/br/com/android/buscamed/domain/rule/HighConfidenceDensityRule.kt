package br.com.android.buscamed.domain.rule

import br.com.android.buscamed.domain.model.capture.TextResult

/**
 * Regra que valida se uma percentagem mínima do texto possui alta confiança.
 *
 * @property highConfidenceThreshold O valor a partir do qual um elemento é considerado de "alta confiança".
 * @property minimumDensityPercentage A percentagem mínima (0.0 a 1.0) do texto global que deve atingir a alta confiança.
 */
class HighConfidenceDensityRule(
    private val highConfidenceThreshold: Float = 0.85f,
    private val minimumDensityPercentage: Float = 0.3f
) : ConfidenceRule {
    override fun evaluate(textResult: TextResult): Boolean {
        val allElements = textResult.blocks.flatMap { it.lines }.flatMap { it.elements }
        if (allElements.isEmpty()) return false

        val highConfidenceCount = allElements.count { it.confidence != null && it.confidence >= highConfidenceThreshold }
        val density = highConfidenceCount.toFloat() / allElements.size

        return density >= minimumDensityPercentage
    }
}