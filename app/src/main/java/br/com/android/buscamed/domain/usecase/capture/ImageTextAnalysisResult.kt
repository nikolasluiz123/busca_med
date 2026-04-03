package br.com.android.buscamed.domain.usecase.capture

import br.com.android.buscamed.domain.model.capture.TextResult
import java.io.File

/**
 * Representa os resultados possíveis após o processamento da captura de um documento.
 */
sealed class ImageTextAnalysisResult {
    /**
     * Indica que o texto foi extraído com alta confiança.
     *
     * @property textResult O texto estruturado e pronto para uso.
     */
    data class HighlyConfident(val textResult: TextResult) : ImageTextAnalysisResult()

    /**
     * Indica que a extração falhou nos critérios de confiança e a imagem deve ser enviada diretamente.
     *
     * @property processedImage O ficheiro de imagem (após passar pelo pipeline) que deve ser enviado como contingência.
     */
    data class LowConfidenceFallback(val processedImage: File) : ImageTextAnalysisResult()

    /**
     * Indica que ocorreu um erro crítico durante o processamento.
     *
     * @property exception A exceção lançada.
     */
    data class Error(val exception: Throwable) : ImageTextAnalysisResult()
}