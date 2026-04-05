package br.com.android.buscamed.domain.usecase.pillpack

import android.util.Log
import br.com.android.buscamed.domain.model.capture.ExecutionMetadata
import br.com.android.buscamed.domain.model.pillpack.PillPack
import br.com.android.buscamed.domain.processor.ImageProcessorStep
import br.com.android.buscamed.domain.processor.TextResultProcessorStep
import br.com.android.buscamed.domain.repository.PillPackRepository
import br.com.android.buscamed.domain.rule.CentralFocusConfidenceRule
import br.com.android.buscamed.domain.rule.GlobalAverageConfidenceRule
import br.com.android.buscamed.domain.rule.HighConfidenceDensityRule
import br.com.android.buscamed.domain.rule.MinimumContentRule
import br.com.android.buscamed.domain.usecase.capture.AnalyzeImageTextUseCase
import br.com.android.buscamed.domain.usecase.capture.ImageTextAnalysisResult
import javax.inject.Inject

/**
 * Caso de uso responsável por coordenar a leitura e o processamento de uma cartela de comprimidos.
 *
 * @property analyzeImageTextUseCase O motor genérico de extração e validação de texto em imagens.
 * @property pillPackRepository O repositório responsável por processar os dados da cartela.
 */
class ReadPillPackUseCase @Inject constructor(
    private val analyzeImageTextUseCase: AnalyzeImageTextUseCase,
    private val pillPackRepository: PillPackRepository
) {
    private val currentPipelineVersion = "pill_pack_v1"

    /**
     * Inicia o processo de leitura da cartela de comprimidos a partir de uma imagem capturada.
     *
     * @param imagePath O caminho absoluto do ficheiro da imagem no dispositivo.
     * @return Um [Result] contendo a cartela estruturada em caso de sucesso, ou uma falha.
     */
    suspend operator fun invoke(imagePath: String): Result<PillPack> {
        val rules = listOf(
            MinimumContentRule(),
            GlobalAverageConfidenceRule(),
            HighConfidenceDensityRule(),
            CentralFocusConfidenceRule()
        )

        val imageProcessorSteps = listOf<ImageProcessorStep>()

        val textProcessorSteps = listOf<TextResultProcessorStep>()

        val analysisResult = analyzeImageTextUseCase(
            imagePath = imagePath,
            imageProcessorSteps = imageProcessorSteps,
            textProcessorSteps = textProcessorSteps,
            confidenceRules = rules
        )

        val metadata = ExecutionMetadata(pipelineVersion = currentPipelineVersion)

        return when (analysisResult) {
            is ImageTextAnalysisResult.HighlyConfident -> {
                Log.d("ReadPillPack", "Leitura validada com sucesso.")

                pillPackRepository.processText(
                    text = analysisResult.textResult.text,
                    imageFile = analysisResult.processedImage,
                    metadata = metadata
                )
            }
            is ImageTextAnalysisResult.LowConfidenceFallback -> {
                Log.d("ReadPillPack", "Confiança baixa. Imagem de fallback gerada em: ${analysisResult.processedImage.absolutePath}")

                pillPackRepository.processImage(
                    text = analysisResult.textResult.text,
                    imageFile = analysisResult.processedImage,
                    metadata = metadata
                )
            }
            is ImageTextAnalysisResult.Error -> {
                Log.e("ReadPillPack", "Erro ao processar a imagem da cartela", analysisResult.exception)
                Result.failure(analysisResult.exception)
            }
        }
    }
}