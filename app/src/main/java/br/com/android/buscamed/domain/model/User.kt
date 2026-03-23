package br.com.android.buscamed.domain.model

/**
 * Entidade de domínio que representa um usuário do sistema.
 *
 * @property id Identificador único do usuário.
 * @property name Nome completo do usuário.
 * @property email Endereço de e-mail associado à conta.
 * @property password Senha do usuário (utilizada apenas em contextos de criação/alteração).
 * @property normalizedName Versão simplificada do nome para fins de ordenação e busca.
 */
data class User(
    val id: String?,
    val name: String,
    val email: String,
    val password: String?,
    val normalizedName: String? = null
)
