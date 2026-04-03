package br.com.android.buscamed.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Objeto de transferência de dados definindo a frequência de uso de um medicamento.
 *
 * @property intervalo Valor numérico do intervalo de tempo.
 * @property unidade Unidade de medida do intervalo.
 * @property textoOrientacao Instruções adicionais descritivas sobre o uso.
 */
@Serializable
data class FrequencyDTO(
    val intervalo: Double? = null,
    val unidade: String? = null,
    @SerialName("texto_orientacao") val textoOrientacao: String? = null
)