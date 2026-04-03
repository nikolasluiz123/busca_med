package br.com.android.buscamed.domain.processor

import br.com.android.buscamed.domain.model.capture.TextResult

/**
 * Representa um passo de processamento no texto extraído por um motor de OCR.
 */
interface TextResultProcessorStep {
    
    /**
     * Processa um resultado de texto e retorna uma nova instância modificada.
     *
     * @param textResult O resultado de texto original ou processado pelo passo anterior.
     * @return Uma nova instância de [TextResult] contendo as modificações estruturais ou de conteúdo.
     */
    fun process(textResult: TextResult): TextResult
}