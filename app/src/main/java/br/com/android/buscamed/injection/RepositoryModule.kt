package br.com.android.buscamed.injection

import br.com.android.buscamed.data.datasource.firestore.UserDataSource
import br.com.android.buscamed.data.repository.UserRepositoryImpl
import br.com.android.buscamed.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de injeção de dependências responsável pelos repositórios da aplicação.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userDataSource: UserDataSource): UserRepository {
        return UserRepositoryImpl(userDataSource)
    }
}