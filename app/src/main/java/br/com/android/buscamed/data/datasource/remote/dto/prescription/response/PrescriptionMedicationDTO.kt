package br.com.android.buscamed.data.datasource.remote.dto.prescription.response

import br.com.android.buscamed.data.datasource.remote.dto.generic.response.ValueUnitDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PrescriptionMedicationDTO(
    val nome: String? = null,
    @SerialName("apresentacao_dosagem") val apresentacaoDosagem: ValueUnitDTO? = null,
    val dose: ValueUnitDTO? = null,
    val frequencia: FrequencyDTO? = null,
    val duracao: DurationDTO? = null,
    @SerialName("quantidade_total_prescrita") val quantidadeTotalPrescrita: ValueUnitDTO? = null
)