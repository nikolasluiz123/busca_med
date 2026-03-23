package br.com.android.buscamed.domain.core.validation

data class GeneralValidationError<TYPE : Enum<TYPE>>(
    val type: TYPE,
    val cause: Throwable? = null
) : ValidationError