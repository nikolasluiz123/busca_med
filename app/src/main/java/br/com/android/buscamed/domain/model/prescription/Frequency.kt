package br.com.android.buscamed.domain.model.prescription

/**
 * Representa a frequência de uso de um medicamento.
 *
 * @property interval Intervalo numérico de tempo.
 * @property unit Unidade de medida do intervalo.
 * @property guidanceText Texto com orientações adicionais.
 */
data class Frequency(
    val interval: Double?,
    val unit: String?,
    val guidanceText: String?
)