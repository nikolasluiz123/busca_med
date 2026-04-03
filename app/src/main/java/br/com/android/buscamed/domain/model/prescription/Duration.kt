package br.com.android.buscamed.domain.model.prescription

/**
 * Representa a duração do tratamento.
 *
 * @property value Valor numérico do tempo.
 * @property unit Unidade de medida do tempo.
 * @property isContinuousUse Indica se o uso é contínuo.
 */
data class Duration(
    val value: Double?,
    val unit: String?,
    val isContinuousUse: Boolean
)