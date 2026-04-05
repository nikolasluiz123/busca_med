package br.com.android.buscamed.injection

import androidx.camera.core.ImageProxy
import br.com.android.buscamed.data.analyzer.mlkit.MLKitBarcodeFrameAnalyzer
import br.com.android.buscamed.data.analyzer.mlkit.MLKitTextFrameAnalyzer
import br.com.android.buscamed.domain.analyzer.FrameAnalyzer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TextAnalyzer

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BarcodeAnalyzer

/**
 * Módulo para prover dependências do fluxo de captura e análise de câmera.
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class CaptureModule {

    @Binds
    @ViewModelScoped
    @TextAnalyzer
    abstract fun bindTextFrameAnalyzer(
        impl: MLKitTextFrameAnalyzer
    ): FrameAnalyzer<ImageProxy, Unit>

    @Binds
    @ViewModelScoped
    @BarcodeAnalyzer
    abstract fun bindBarcodeFrameAnalyzer(
        impl: MLKitBarcodeFrameAnalyzer
    ): FrameAnalyzer<ImageProxy, String>
}