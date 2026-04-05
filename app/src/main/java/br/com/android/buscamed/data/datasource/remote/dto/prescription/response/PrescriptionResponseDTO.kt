package br.com.android.buscamed.data.datasource.remote.dto.prescription.response

import kotlinx.serialization.Serializable

@Serializable
data class PrescriptionResponseDTO(
    val medicamentos: List<PrescriptionMedicationDTO> = emptyList()
)