package br.com.android.buscamed.domain.core.validation

data class FieldValidationError<FIELD : Enum<FIELD>, TYPE : Enum<TYPE>>(
    val field: FIELD,
    val type: TYPE
) : ValidationError

