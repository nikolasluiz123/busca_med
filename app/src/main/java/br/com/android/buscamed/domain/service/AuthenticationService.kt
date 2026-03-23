package br.com.android.buscamed.domain.service

import br.com.android.buscamed.domain.model.UserCredentials

interface AuthenticationService {
    suspend fun signIn(credentials: UserCredentials)
    suspend fun signUp(credentials: UserCredentials): String
    fun signOut()
}