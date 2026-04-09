package br.com.android.buscamed.domain.core.pagination

/**
 * @param T O tipo de dado da lista.
 * @property items Os itens retornados na página atual.
 * @property cursor Um identificador opaco para buscar a próxima página. Nulo se for a última página.
 */
data class PaginatedResult<T>(
    val items: List<T>,
    val cursor: Any?
)