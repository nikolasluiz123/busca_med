package br.com.android.buscamed.domain.core.validation

/**
 * Representa o resultado de uma operação de Use Case.
 *
 * Encapsula o retorno de sucesso com dados opcionais ou um estado de erro
 * contendo uma lista de falhas de validação.
 *
 * @param T O tipo do dado retornado em caso de sucesso.
 */
sealed interface UseCaseResult<out T> {

    /**
     * Representa a conclusão bem-sucedida de um Use Case.
     *
     * @property data O resultado da operação.
     */
    data class Success<out T>(val data: T? = null) : UseCaseResult<T>

    /**
     * Representa uma falha na execução do Use Case devido a erros de validação ou regras de negócio.
     *
     * @property errors Lista de [ValidationError] encontrados.
     */
    data class Error(val errors: List<ValidationError>) : UseCaseResult<Nothing>
}
