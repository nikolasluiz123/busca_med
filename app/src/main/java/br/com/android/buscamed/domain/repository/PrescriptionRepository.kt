package br.com.android.buscamed.domain.repository

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
     * @return Result contendo a prescrição processada ou uma falha.
     */
    suspend fun processText(text: String): Result<Prescription>

    /**
     * Processa a imagem de uma prescrição médica.
     *
     * @param imageFile O arquivo de imagem contendo a prescrição.
     * @return Result contendo a prescrição processada ou uma falha.
     */
    suspend fun processImage(imageFile: File): Result<Prescription>
}