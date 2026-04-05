package br.com.android.buscamed.domain.repository

import br.com.android.buscamed.domain.model.capture.ExecutionMetadata
import br.com.android.buscamed.domain.model.pillpack.PillPack
import java.io.File

/**
 * Contrato para serviços de processamento de cartelas de comprimidos.
 */
interface PillPackRepository {

    /**
     * Processa o texto extraído de uma cartela de comprimidos.
     *
     * @param text O texto bruto da cartela.
     * @param imageFile O arquivo de imagem contendo a cartela.
     * @param metadata Informações de metadados de execução do pipeline.
     * @return Result contendo a cartela processada ou uma falha.
     */
    suspend fun processText(text: String, imageFile: File, metadata: ExecutionMetadata): Result<PillPack>

    /**
     * Processa a imagem de uma cartela de comprimidos.
     *
     * @param text O texto bruto da cartela.
     * @param imageFile O arquivo de imagem contendo a cartela.
     * @param metadata Informações de metadados de execução do pipeline.
     * @return Result contendo a cartela processada ou uma falha.
     */
    suspend fun processImage(text: String, imageFile: File, metadata: ExecutionMetadata): Result<PillPack>
}