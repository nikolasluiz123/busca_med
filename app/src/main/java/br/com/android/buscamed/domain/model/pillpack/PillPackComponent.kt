package br.com.android.buscamed.domain.model.pillpack

/**
 * Representa um componente (princípio ativo) presente na cartela de comprimidos.
 *
 * @property activeIngredient O nome do princípio ativo.
 * @property dosageValue O valor numérico da dosagem.
 * @property dosageUnit A unidade de medida da dosagem (ex: mg, mcg).
 */
data class PillPackComponent(
    val activeIngredient: String,
    val dosageValue: Double?,
    val dosageUnit: String?
)