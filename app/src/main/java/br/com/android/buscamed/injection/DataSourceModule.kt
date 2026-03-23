package br.com.android.buscamed.injection

import br.com.android.buscamed.data.datasource.firestore.UserDataSource
import br.com.android.buscamed.data.datasource.firestore.UserFirestoreDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindUserDataSource(
        impl: UserFirestoreDataSource
    ): UserDataSource
}