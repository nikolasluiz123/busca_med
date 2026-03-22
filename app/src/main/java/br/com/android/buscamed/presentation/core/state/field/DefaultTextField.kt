package br.com.android.buscamed.presentation.core.state.field

import br.com.android.buscamed.presentation.core.state.interfaces.field.TextField

data class DefaultTextField(
    override val value: String = "",
    override val onChange: (String) -> Unit = { },
    override val errorMessage: String = ""
): TextField