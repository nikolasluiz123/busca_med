package br.com.android.buscamed.domain.provider

import br.com.android.buscamed.domain.model.capture.TextResult
import java.io.File

/**
 * Contrato para o provedor responsável por extrair texto de uma imagem.
 */
interface TextRecognitionProvider {
    /**
     * Analisa uma imagem e extrai a sua estrutura de texto.
     *
     * @param image O ficheiro de imagem a ser analisado.
     * @return A estrutura completa do texto extraído.
     */
    suspend fun recognizeText(image: File): TextResult
}