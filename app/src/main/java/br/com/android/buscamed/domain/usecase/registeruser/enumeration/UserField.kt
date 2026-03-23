package br.com.android.buscamed.domain.usecase.registeruser.enumeration

import br.com.android.buscamed.domain.core.validation.EnumFieldValidation

enum class UserField(override val maxLength: Int, override val minLength: Int): EnumFieldValidation {
    NAME(maxLength = 256, minLength = 0),
    EMAIL(maxLength = 256, minLength = 0),
    PASSWORD(maxLength = 4096, minLength = 6)
}