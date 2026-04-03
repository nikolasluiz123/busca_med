package br.com.android.buscamed.data.datasource.remote.dto

import kotlinx.serialization.Serializable

/**
 * Objeto de transferência de dados representando a resposta estruturada de uma prescrição médica.
 *
 * @property medicamentos Lista de medicamentos identificados no processamento.
 */
@Serializable
data class PrescriptionResponseDTO(
    val medicamentos: List<PrescriptionMedicationDTO> = emptyList()
)