package br.com.android.buscamed.presentation.core.state.interfaces.field

interface TextField {
    val value: String
    val onChange: (String) -> Unit
    val errorMessage: String
}