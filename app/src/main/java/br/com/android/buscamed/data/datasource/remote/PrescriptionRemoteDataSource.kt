package br.com.android.buscamed.data.datasource.remote

import br.com.android.buscamed.data.datasource.remote.dto.PrescriptionResponseDTO
import br.com.android.buscamed.domain.model.capture.ExecutionMetadata
import java.io.File

/**
 * Contrato para comunicação com a API remota de processamento de prescrições.
 */
interface PrescriptionRemoteDataSource {

    /**
     * Envia um texto para processamento no backend.
     *
     * @param text O texto a ser processado.
     * @param file Arquivo da imagem.
     * @param metadata Metadados do pipeline de execução.
     * @return DTO com a resposta do processamento.
     */
    suspend fun processPrescriptionText(text: String, file: File, metadata: ExecutionMetadata): PrescriptionResponseDTO

    /**
     * Envia uma imagem para processamento no backend via Multipart.
     *
     * @param text Texto a ser processado.
     * @param file Arquivo da imagem.
     * @param metadata Metadados do pipeline de execução.
     * @return DTO com a resposta do processamento.
     */
    suspend fun processPrescriptionImage(text: String, file: File, metadata: ExecutionMetadata): PrescriptionResponseDTO
}