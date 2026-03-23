package br.com.android.buscamed.domain.core.validation

/**
 * Representa um erro de validação vinculado a um campo específico.
 *
 * Utiliza Enums para tipar tanto o campo quanto o tipo de erro, garantindo
 * segurança em tempo de compilação para o tratamento de erros na UI.
 *
 * @param FIELD O Enum que identifica o campo (ex: E-mail, Senha).
 * @param TYPE O Enum que identifica o tipo do erro (ex: Vazio, Inválido).
 * @property field O campo que gerou a falha.
 * @property type O tipo específico da validação que falhou.
 */
data class FieldValidationError<FIELD : Enum<FIELD>, TYPE : Enum<TYPE>>(
    val field: FIELD,
    val type: TYPE
) : ValidationError


