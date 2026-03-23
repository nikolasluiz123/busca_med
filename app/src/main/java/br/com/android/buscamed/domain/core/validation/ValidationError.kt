package br.com.android.buscamed.domain.core.validation

/**
 * Interface base para todas as falhas de validação no domínio.
 *
 * Serve como um marcador para os diferentes tipos de erros que podem ocorrer
 * durante a execução de regras de negócio.
 */
sealed interface ValidationError
