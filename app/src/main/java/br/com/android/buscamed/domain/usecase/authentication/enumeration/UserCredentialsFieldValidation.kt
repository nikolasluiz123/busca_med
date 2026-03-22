package br.com.android.buscamed.domain.usecase.authentication.enumeration

import br.com.android.buscamed.domain.core.validation.EnumFieldValidation

enum class UserCredentialsFieldValidation(override val maxLength: Int, override val minLength: Int): EnumFieldValidation {
    EMAIL(maxLength = 256, minLength = 0),
    PASSWORD(maxLength = 4096, minLength = 8)
}