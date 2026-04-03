package br.com.android.buscamed.domain.analyzer

import br.com.android.buscamed.domain.model.capture.FrameAnalysisResult
import kotlinx.coroutines.flow.StateFlow

/**
 * Contrato para analisadores de quadros de imagem em tempo real.
 *
 * @param T O tipo de representação de imagem suportado pelo analisador (ex: ImageProxy).
 */
interface FrameAnalyzer<T> {
    
    /**
     * Fluxo de estado contendo o resultado da análise mais recente.
     */
    val state: StateFlow<FrameAnalysisResult>

    /**
     * Processa um quadro de imagem de forma síncrona ou assíncrona para extrair informações.
     *
     * @param frame O quadro de imagem a ser analisado.
     */
    fun analyze(frame: T)
}