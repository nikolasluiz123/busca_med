package br.com.android.buscamed.domain.usecase.prescription

import android.util.Log
import br.com.android.buscamed.data.processor.PassThroughProcessorStep
import br.com.android.buscamed.domain.rule.CentralFocusConfidenceRule
import br.com.android.buscamed.domain.rule.GlobalAverageConfidenceRule
import br.com.android.buscamed.domain.rule.HighConfidenceDensityRule
import br.com.android.buscamed.domain.rule.MinimumContentRule
import br.com.android.buscamed.domain.usecase.capture.AnalyzeImageTextUseCase
import br.com.android.buscamed.domain.usecase.capture.ImageTextAnalysisResult
import javax.inject.Inject

/**
 * Caso de uso responsável por coordenar a leitura e o processamento de uma receita médica.
 *
 * @property analyzeImageTextUseCase O motor genérico de extração e validação de texto em imagens.
 */
class ReadPrescriptionUseCase @Inject constructor(
    private val analyzeImageTextUseCase: AnalyzeImageTextUseCase
) {
    /**
     * Inicia o processo de leitura da receita médica a partir de uma imagem capturada.
     *
     * @param imagePath O caminho absoluto do ficheiro da imagem no dispositivo.
     */
    suspend operator fun invoke(imagePath: String) {
        val rules = listOf(
            MinimumContentRule(),
            GlobalAverageConfidenceRule(),
            HighConfidenceDensityRule(),
            CentralFocusConfidenceRule()
        )

        val steps = listOf(
            PassThroughProcessorStep()
        )

        val analysisResult = analyzeImageTextUseCase(
            imagePath = imagePath,
            imageProcessorSteps = steps,
            confidenceRules = rules
        )

        when (analysisResult) {
            is ImageTextAnalysisResult.HighlyConfident -> {
                val extractedText = analysisResult.textResult.text
                Log.d("ReadPrescription", "Leitura validada com sucesso.")
            }
            is ImageTextAnalysisResult.LowConfidenceFallback -> {
                val fallbackImage = analysisResult.processedImage
                Log.d("ReadPrescription", "Confiança baixa. Imagem de fallback gerada em: ${fallbackImage.absolutePath}")
            }
            is ImageTextAnalysisResult.Error -> {
                Log.e("ReadPrescription", "Erro ao processar a imagem da receita", analysisResult.exception)
            }
        }
    }
}