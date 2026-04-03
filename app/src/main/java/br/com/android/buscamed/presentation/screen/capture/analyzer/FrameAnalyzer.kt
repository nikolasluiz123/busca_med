package br.com.android.buscamed.presentation.screen.capture.analyzer

import androidx.camera.core.ImageProxy
import br.com.android.buscamed.presentation.screen.capture.state.AnalyzerState
import br.com.android.buscamed.presentation.screen.capture.state.FrameAnalysisResult
import kotlinx.coroutines.flow.StateFlow

/**
 * Contrato para analisadores de quadros oriundos do ImageAnalysis.
 */
interface FrameAnalyzer {

    val state: StateFlow<FrameAnalysisResult>

    /**
     * Processa e analisa um quadro individual da câmera.
     *
     * @param imageProxy O proxy contendo o buffer da imagem e seus metadados.
     * O analisador é responsável por chamar imageProxy.close() ao final do processamento.
     */
    fun analyze(imageProxy: ImageProxy)

    /**
     * Libera os recursos alocados pelo analisador.
     */
    fun clear()
}