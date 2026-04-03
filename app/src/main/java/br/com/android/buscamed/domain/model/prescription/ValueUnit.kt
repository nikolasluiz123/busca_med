package br.com.android.buscamed.domain.model.prescription

/**
 * Representa um valor com sua respectiva unidade de medida.
 *
 * @property value Valor numérico.
 * @property unit Unidade de medida.
 */
data class ValueUnit(
    val value: Double?,
    val unit: String?
)