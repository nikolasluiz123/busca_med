package br.com.android.buscamed.data.datasource.firestore

import br.com.android.buscamed.data.document.UserDocument

/**
 * Contrato de acesso a dados para a entidade de Usuário.
 *
 * Define as funções necessárias para interagir com a fonte de dados,
 * abstraindo a implementação específica.
 */
interface UserDataSource {
    /**
     * Recupera um usuário pelo seu identificador único.
     *
     * @param id O ID do documento no banco de dados.
     * @return Um [UserDocument] caso seja encontrado, ou null caso não exista.
     */
    suspend fun getById(id: String): UserDocument?

    /**
     * Salva ou atualiza os dados de um usuário na fonte de dados.
     *
     * @param user O objeto [UserDocument] contendo as informações a serem persistidas.
     */
    suspend fun save(user: UserDocument)
}
