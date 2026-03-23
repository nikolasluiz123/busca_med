package br.com.android.buscamed.domain.service

import br.com.android.buscamed.domain.model.UserCredentials

/**
 * Contrato para serviços de autenticação de usuários.
 *
 * Define as funções necessárias para realizar o controle de acesso,
 * como login, cadastro e logout.
 */
interface AuthenticationService {
    /**
     * Realiza a autenticação do usuário.
     *
     * @param credentials Credenciais de acesso (e-mail e senha).
     */
    suspend fun signIn(credentials: UserCredentials)

    /**
     * Cria uma nova conta de usuário.
     *
     * @param credentials Dados para o novo cadastro.
     * @return O identificador único do usuário criado.
     */
    suspend fun signUp(credentials: UserCredentials): String

    /**
     * Encerra a sessão do usuário atual.
     */
    fun signOut()
}
