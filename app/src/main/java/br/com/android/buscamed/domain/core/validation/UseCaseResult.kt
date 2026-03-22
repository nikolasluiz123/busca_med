package br.com.android.buscamed.domain.core.validation

sealed interface UseCaseResult<out T> {

    data class Success<out T>(val data: T? = null) : UseCaseResult<T>
    data class Error(val errors: List<ValidationError>) : UseCaseResult<Nothing>
}