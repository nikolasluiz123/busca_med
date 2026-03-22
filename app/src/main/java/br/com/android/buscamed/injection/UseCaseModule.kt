package br.com.android.buscamed.injection

import br.com.android.buscamed.domain.repository.UserRepository
import br.com.android.buscamed.domain.usecase.authentication.DefaultAuthenticationUseCase
import br.com.android.buscamed.domain.usecase.registeruser.RegisterUserUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Módulo de injeção de dependências encarregado de prover os Casos de Uso (UseCases) do domínio.
 */
@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideDefaultAuthenticationUseCase(
        firebaseAuth: FirebaseAuth
    ): DefaultAuthenticationUseCase {
        return DefaultAuthenticationUseCase(firebaseAuth)
    }

    @Provides
    @ViewModelScoped
    fun provideRegisterUserUseCase(
        userRepository: UserRepository,
        firebaseAuth: FirebaseAuth
    ): RegisterUserUseCase {
        return RegisterUserUseCase(userRepository, firebaseAuth)
    }
}