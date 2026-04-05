package br.com.android.buscamed.data.datasource.remote.dto.prescription.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DurationDTO(
    val valor: Double? = null,
    val unidade: String? = null,
    @SerialName("uso_continuo") val usoContinuo: Boolean = false
)