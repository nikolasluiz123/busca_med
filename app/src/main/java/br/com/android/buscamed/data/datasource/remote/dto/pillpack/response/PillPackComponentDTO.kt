package br.com.android.buscamed.data.datasource.remote.dto.pillpack.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * DTO que encapsula a composição de um princípio ativo presente na cartela de comprimidos.
 *
 * @property principioAtivo O nome do princípio ativo.
 * @property dosagemValor O valor numérico da dosagem.
 * @property dosagemUnidade A unidade de medida da dosagem (ex: mg, mcg).
 */
@Serializable
data class PillPackComponentDTO(
    @SerialName("principio_ativo") val principioAtivo: String,
    @SerialName("dosagem_valor") val dosagemValor: Double? = null,
    @SerialName("dosagem_unidade") val dosagemUnidade: String? = null
)