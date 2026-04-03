package br.com.android.buscamed.domain.rule

import br.com.android.buscamed.domain.model.capture.TextResult

/**
 * Contrato para uma regra que avalia se a qualidade da extração de texto é aceitável.
 */
interface ConfidenceRule {
    /**
     * Avalia o resultado da extração.
     *
     * @param textResult O texto extraído a ser avaliado.
     * @return Verdadeiro se o texto cumprir os critérios da regra, Falso caso contrário.
     */
    fun evaluate(textResult: TextResult): Boolean
}