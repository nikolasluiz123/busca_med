package br.com.android.buscamed.domain.repository

import br.com.android.buscamed.domain.model.capture.ExecutionMetadata
import br.com.android.buscamed.domain.model.prescription.Prescription
import java.io.File

/**
 * Contrato para serviços de processamento de prescrições médicas.
 */
interface PrescriptionRepository {
    
    /**
     * Processa o texto extraído de uma prescrição médica.
     *
     * @param text O texto bruto da prescrição.
     * @param imageFile O arquivo de imagem contendo a prescrição.
     * @param metadata Informações de metadados de execução do pipeline.
     * @return Result contendo a prescrição processada ou uma falha.
     */
    suspend fun processText(text: String, imageFile: File, metadata: ExecutionMetadata): Result<Prescription>

    /**
     * Processa a imagem de uma prescrição médica.
     *
     * @param text O texto bruto da prescrição.
     * @param imageFile O arquivo de imagem contendo a prescrição.
     * @param metadata Informações de metadados de execução do pipeline.
     * @return Result contendo a prescrição processada ou uma falha.
     */
    suspend fun processImage(text: String, imageFile: File, metadata: ExecutionMetadata): Result<Prescription>
}