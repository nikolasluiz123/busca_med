package br.com.android.buscamed.data.datasource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Objeto de transferência de dados contendo as informações detalhadas de um medicamento.
 *
 * @property nome Nome do medicamento identificado.
 * @property apresentacaoDosagem Apresentação e dosagem do medicamento.
 * @property dose Quantidade a ser ingerida por vez.
 * @property frequencia Frequência de uso estipulada.
 * @property duracao Duração total do tratamento.
 * @property quantidadeTotalPrescrita Quantidade total do medicamento que deve ser adquirida.
 */
@Serializable
data class PrescriptionMedicationDTO(
    val nome: String? = null,
    @SerialName("apresentacao_dosagem") val apresentacaoDosagem: ValueUnitDTO? = null,
    val dose: ValueUnitDTO? = null,
    val frequencia: FrequencyDTO? = null,
    val duracao: DurationDTO? = null,
    @SerialName("quantidade_total_prescrita") val quantidadeTotalPrescrita: ValueUnitDTO? = null
)