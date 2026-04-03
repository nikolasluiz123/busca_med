package br.com.android.buscamed.injection

import androidx.camera.core.ImageProxy
import br.com.android.buscamed.data.analyzer.mlkit.MLKitTextFrameAnalyzer
import br.com.android.buscamed.domain.analyzer.FrameAnalyzer
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
    fun provideFrameAnalyzer(): FrameAnalyzer<ImageProxy> {
        return MLKitTextFrameAnalyzer()
    }
}