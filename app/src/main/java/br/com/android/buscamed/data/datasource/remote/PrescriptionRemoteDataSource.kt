package br.com.android.buscamed.data.datasource.remote

import br.com.android.buscamed.data.datasource.remote.dto.PrescriptionResponseDTO
import java.io.File

/**
 * Contrato para comunicação com a API remota de processamento de prescrições.
 */
interface PrescriptionRemoteDataSource {

    /**
     * Envia um texto para processamento no backend.
     *
     * @param text O texto a ser processado.
     * @return DTO com a resposta do processamento.
     */
    suspend fun processPrescriptionText(text: String): PrescriptionResponseDTO

    /**
     * Envia uma imagem para processamento no backend via Multipart.
     *
     * @param file Arquivo da imagem.
     * @return DTO com a resposta do processamento.
     */
    suspend fun processPrescriptionImage(file: File): PrescriptionResponseDTO
}