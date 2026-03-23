package br.com.android.buscamed.domain.core.validation

/**
 * Representa um erro de validação genérico não atrelado a um campo específico.
 *
 * Utilizado para erros que afetam a operação, como falhas de rede,
 * credenciais inválidas ou erros inesperados.
 *
 * @param TYPE O Enum que identifica o tipo do erro geral.
 * @property type O tipo específico da falha ocorrida.
 * @property cause A exceção original que motivou o erro, se disponível.
 */
data class GeneralValidationError<TYPE : Enum<TYPE>>(
    val type: TYPE,
    val cause: Throwable? = null
) : ValidationError
