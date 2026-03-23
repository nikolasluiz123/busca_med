package br.com.android.buscamed.injection

import br.com.android.buscamed.data.service.FirebaseAuthenticationServiceImpl
import br.com.android.buscamed.domain.service.AuthenticationService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {

    @Binds
    abstract fun bindAuthenticationService(
        impl: FirebaseAuthenticationServiceImpl
    ): AuthenticationService
}