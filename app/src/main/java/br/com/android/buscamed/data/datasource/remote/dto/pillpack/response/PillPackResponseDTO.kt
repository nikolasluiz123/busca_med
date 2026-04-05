package br.com.android.buscamed.data.datasource.remote.dto.pillpack.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO que representa a resposta estruturada do processamento de uma cartela de comprimidos.
 *
 * @property nomeMedicamento O nome do medicamento identificado na cartela.
 * @property componentes Lista de componentes (princípios ativos) presentes na cartela.
 * @property uso Informações sobre vias de administração e restrições de idade.
 * @property indicacoes Lista de indicações terapêuticas extraídas.
 * @property dataValidade Data de validade do produto.
 * @property lote Identificação do lote de fabricação.
 */
@Serializable
data class PillPackResponseDTO(
    @SerialName("nome_medicamento") val nomeMedicamento: String? = null,
    val componentes: List<PillPackComponentDTO> = emptyList(),
    val uso: PillPackUsageDTO? = null,
    val indicacoes: List<String> = emptyList(),
    @SerialName("data_validade") val dataValidade: String? = null,
    val lote: String? = null
)