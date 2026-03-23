package br.com.android.buscamed.data.repository

import br.com.android.buscamed.data.datasource.firestore.UserDataSource
import br.com.android.buscamed.data.mapper.toUser
import br.com.android.buscamed.data.mapper.toUserDocument
import br.com.android.buscamed.domain.model.User
import br.com.android.buscamed.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource
): UserRepository {

    override suspend fun getById(id: String): User? {
        return dataSource.getById(id)?.toUser()
    }

    override suspend fun save(user: User) {
        dataSource.save(user.toUserDocument())
    }
}