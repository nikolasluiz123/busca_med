package br.com.android.buscamed.data.datasource.remote.dto.generic.response

import kotlinx.serialization.Serializable

/**
 * Objeto de transferência de dados genérico para valores acompanhados de unidade de medida.
 *
 * @property valor Valor numérico da grandeza.
 * @property unidade Unidade de medida correspondente (ex: mg, ml).
 */
@Serializable
data class ValueUnitDTO(
    val valor: Double? = null,
    val unidade: String? = null
)