package br.com.android.buscamed.domain.repository

import br.com.android.buscamed.domain.model.User

interface UserRepository {
    suspend fun getById(id: String): User?
    suspend fun save(user: User)
}