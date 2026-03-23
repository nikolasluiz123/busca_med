package br.com.android.buscamed.domain.model

data class User(
    val id: String?,
    val name: String,
    val email: String,
    val password: String?,
    val normalizedName: String? = null
)
