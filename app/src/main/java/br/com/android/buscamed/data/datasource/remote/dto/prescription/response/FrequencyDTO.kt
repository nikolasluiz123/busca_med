package br.com.android.buscamed.data.datasource.remote.dto.prescription.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FrequencyDTO(
    val intervalo: Double? = null,
    val unidade: String? = null,
    @SerialName("texto_orientacao") val textoOrientacao: String? = null
)