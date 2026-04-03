package br.com.android.buscamed.injection

import br.com.android.buscamed.data.repository.PrescriptionRepositoryImpl
import br.com.android.buscamed.data.repository.UserRepositoryImpl
import br.com.android.buscamed.domain.repository.PrescriptionRepository
import br.com.android.buscamed.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        impl: UserRepositoryImpl
    ): UserRepository

    @Binds
    abstract fun bindPrescriptionRepository(
        impl: PrescriptionRepositoryImpl
    ): PrescriptionRepository
}