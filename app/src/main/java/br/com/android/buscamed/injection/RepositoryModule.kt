package br.com.android.buscamed.injection

import br.com.android.buscamed.data.repository.MedicationRepositoryImpl
import br.com.android.buscamed.data.repository.PillPackRepositoryImpl
import br.com.android.buscamed.data.repository.PrescriptionRepositoryImpl
import br.com.android.buscamed.data.repository.UserRepositoryImpl
import br.com.android.buscamed.domain.repository.MedicationRepository
import br.com.android.buscamed.domain.repository.PillPackRepository
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

    @Binds
    abstract fun bindPillPackRepository(
        impl: PillPackRepositoryImpl
    ): PillPackRepository

    @Binds
    abstract fun bindMedicationRepository(
        impl: MedicationRepositoryImpl
    ): MedicationRepository
}