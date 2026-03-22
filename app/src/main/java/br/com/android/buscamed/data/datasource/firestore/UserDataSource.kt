package br.com.android.buscamed.data.datasource.firestore

import br.com.android.buscamed.data.document.UserDocument

interface UserDataSource {
    suspend fun getById(id: String): UserDocument?
    suspend fun save(user: UserDocument)
}