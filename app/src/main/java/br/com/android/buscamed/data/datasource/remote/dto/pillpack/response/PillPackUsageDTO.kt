package br.com.android.buscamed.data.datasource.remote.dto.pillpack.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO que define as vias de administração e restrições de uso impressas na cartela.
 *
 * @property viasAdministracao Lista das vias de administração (ex: Oral).
 * @property restricoesIdade Lista de restrições de idade para o uso.
 */
@Serializable
data class PillPackUsageDTO(
    @SerialName("vias_administracao") val viasAdministracao: List<String> = emptyList(),
    @SerialName("restricoes_idade") val restricoesIdade: List<String> = emptyList()
)