package br.com.android.buscamed.injection

import android.content.Context
import br.com.android.buscamed.data.provider.MLKitTextRecognitionProvider
import br.com.android.buscamed.domain.provider.TextRecognitionProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo para prover dependências relacionadas ao processamento e extração de texto de imagens.
 */
@Module
@InstallIn(SingletonComponent::class)
object ProviderModule {

    @Provides
    @Singleton
    fun provideTextRecognitionProvider(
        @ApplicationContext context: Context
    ): TextRecognitionProvider {
        return MLKitTextRecognitionProvider(context)
    }
}