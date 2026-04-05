package br.com.android.buscamed.data.datasource.remote

import br.com.android.buscamed.data.datasource.remote.dto.pillpack.response.PillPackResponseDTO
import br.com.android.buscamed.domain.model.capture.ExecutionMetadata
import java.io.File

/**
 * Contrato para comunicação com a API remota de processamento de cartelas de comprimidos.
 */
interface PillPackRemoteDataSource {

    /**
     * Envia um texto para processamento no backend.
     *
     * @param text O texto a ser processado.
     * @param file Arquivo da imagem.
     * @param metadata Metadados do pipeline de execução.
     * @return DTO com a resposta do processamento.
     */
    suspend fun processPillPackText(text: String, file: File, metadata: ExecutionMetadata): PillPackResponseDTO

    /**
     * Envia uma imagem para processamento no backend via Multipart.
     *
     * @param text Texto a ser processado.
     * @param file Arquivo da imagem.
     * @param metadata Metadados do pipeline de execução.
     * @return DTO com a resposta do processamento.
     */
    suspend fun processPillPackImage(text: String, file: File, metadata: ExecutionMetadata): PillPackResponseDTO
}