package br.com.android.buscamed.domain.model.pillpack

/**
 * Representa os dados estruturados de uma cartela de comprimidos.
 *
 * @property medicationName O nome do medicamento identificado na cartela.
 * @property components Lista de componentes (princípios ativos) presentes.
 * @property usage Informações sobre vias de administração e restrições.
 * @property indications Lista de indicações de uso do medicamento.
 * @property expirationDate Data de validade impressa na cartela.
 * @property batch Identificação do lote do produto.
 */
data class PillPack(
    val medicationName: String?,
    val components: List<PillPackComponent>,
    val usage: PillPackUsage?,
    val indications: List<String>,
    val expirationDate: String?,
    val batch: String?
)