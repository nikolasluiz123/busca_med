package br.com.android.buscamed.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Objeto de transferência de dados definindo a duração de um tratamento.
 *
 * @property valor Valor numérico do tempo de tratamento.
 * @property unidade Unidade de medida do tempo.
 * @property usoContinuo Indicador booleano se o medicamento é de uso contínuo.
 */
@Serializable
data class DurationDTO(
    val valor: Double? = null,
    val unidade: String? = null,
    @SerialName("uso_continuo") val usoContinuo: Boolean = false
)