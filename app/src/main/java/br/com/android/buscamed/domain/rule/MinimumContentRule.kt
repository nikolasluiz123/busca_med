package br.com.android.buscamed.domain.rule

import br.com.android.buscamed.domain.model.capture.TextResult

/**
 * Regra que valida se o texto extraído possui uma quantidade mínima de elementos.
 *
 * @property minimumElements A quantidade mínima de elementos exigida para aprovação.
 */
class MinimumContentRule(
    private val minimumElements: Int = 3
) : ConfidenceRule {
    override fun evaluate(textResult: TextResult): Boolean {
        val totalElements = textResult.blocks.flatMap { it.lines }.flatMap { it.elements }.size
        return totalElements >= minimumElements
    }
}