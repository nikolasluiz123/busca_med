package br.com.android.buscamed.injection

import br.com.android.buscamed.data.datasource.firestore.UserDataSource
import br.com.android.buscamed.data.datasource.firestore.UserFirestoreDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de injeção de dependências para as fontes de dados (DataSources).
 */
@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideUserDataSource(): UserDataSource {
        return UserFirestoreDataSource()
    }
}