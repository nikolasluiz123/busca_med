package br.com.android.buscamed.injection

import br.com.android.buscamed.presentation.screen.capture.analyzer.FrameAnalyzer
import br.com.android.buscamed.presentation.screen.capture.analyzer.MLKitTextFrameAnalyzer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Módulo para prover dependências do fluxo de captura e análise de câmera.
 */
@Module
@InstallIn(ViewModelComponent::class)
object CaptureModule {

    @Provides
    @ViewModelScoped
    fun provideFrameAnalyzer(): FrameAnalyzer {
        return MLKitTextFrameAnalyzer()
    }
}