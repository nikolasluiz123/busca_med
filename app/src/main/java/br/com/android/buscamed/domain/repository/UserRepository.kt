package br.com.android.buscamed.domain.repository

import br.com.android.buscamed.domain.model.User

/**
 * Interface que define as operações de persistência e consulta para usuários.
 *
 * Este contrato deve ser implementado pela camada de dados para prover o acesso
 * às informações de usuários, independente da fonte de dados utilizada.
 */
interface UserRepository {
    /**
     * Busca um usuário através do seu identificador.
     *
     * @param id O ID do usuário.
     * @return O objeto [User] caso encontrado, ou null.
     */
    suspend fun getById(id: String): User?

    /**
     * Persiste as informações de um usuário.
     *
     * @param user O objeto de domínio a ser salvo.
     */
    suspend fun save(user: User)
}
