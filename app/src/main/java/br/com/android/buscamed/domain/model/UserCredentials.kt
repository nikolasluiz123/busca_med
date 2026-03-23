package br.com.android.buscamed.domain.model

/**
 * Representa as credenciais necessárias para autenticação.
 *
 * @property email E-mail de login do usuário.
 * @property password Senha de acesso.
 */
data class UserCredentials(
    val email: String,
    val password: String
)
