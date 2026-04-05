package br.com.android.buscamed.domain.model.generic

/**
 * Representa um valor acompanhado de sua unidade de medida.
 *
 * @property value O valor numérico da grandeza.
 * @property unit A unidade de medida (ex: mg, g, ml).
 */
data class ValueUnit(
    val value: Double?,
    val unit: String?
)