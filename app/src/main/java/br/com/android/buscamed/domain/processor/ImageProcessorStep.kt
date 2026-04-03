package br.com.android.buscamed.domain.processor

import java.io.File

/**
 * Define um passo isolado no pipeline de processamento de imagem.
 */
interface ImageProcessorStep {
    /**
     * Processa a imagem fornecida e devolve o ficheiro resultante.
     *
     * @param image O ficheiro de imagem original ou processado pelo passo anterior.
     * @return O ficheiro de imagem após a aplicação do processamento específico deste passo.
     */
    fun process(image: File): File
}