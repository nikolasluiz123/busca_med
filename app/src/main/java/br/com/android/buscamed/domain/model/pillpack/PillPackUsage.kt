package br.com.android.buscamed.domain.model.pillpack

/**
 * Representa as informações de uso e restrições impressas na cartela.
 *
 * @property administrationRoutes Lista das vias de administração (ex: Oral).
 * @property ageRestrictions Lista de restrições de idade para o uso.
 */
data class PillPackUsage(
    val administrationRoutes: List<String>?,
    val ageRestrictions: List<String>?
)