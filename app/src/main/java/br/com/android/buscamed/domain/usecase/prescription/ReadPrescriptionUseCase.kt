package br.com.android.buscamed.domain.usecase.prescription

import android.util.Log
import br.com.android.buscamed.domain.model.prescription.Prescription
import br.com.android.buscamed.domain.processor.ImageProcessorStep
import br.com.android.buscamed.domain.processor.SpatialReconstructionProcessorStep
import br.com.android.buscamed.domain.repository.PrescriptionRepository
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
 * @property prescriptionRepository O repositório responsável por processar os dados da prescrição.
 */
class ReadPrescriptionUseCase @Inject constructor(
    private val analyzeImageTextUseCase: AnalyzeImageTextUseCase,
    private val prescriptionRepository: PrescriptionRepository
) {
    /**
     * Inicia o processo de leitura da receita médica a partir de uma imagem capturada.
     *
     * @param imagePath O caminho absoluto do ficheiro da imagem no dispositivo.
     * @return Um [Result] contendo a prescrição estruturada em caso de sucesso, ou uma falha.
     */
    suspend operator fun invoke(imagePath: String): Result<Prescription> {
        val rules = listOf(
            MinimumContentRule(),
            GlobalAverageConfidenceRule(),
            HighConfidenceDensityRule(),
            CentralFocusConfidenceRule()
        )

        val imageProcessorSteps = listOf<ImageProcessorStep>()

        val textProcessorSteps = listOf(
            SpatialReconstructionProcessorStep()
        )

        val analysisResult = analyzeImageTextUseCase(
            imagePath = imagePath,
            imageProcessorSteps = imageProcessorSteps,
            textProcessorSteps = textProcessorSteps,
            confidenceRules = rules
        )

        return when (analysisResult) {
            is ImageTextAnalysisResult.HighlyConfident -> {
                Log.d("ReadPrescription", "Leitura validada com sucesso.")

                prescriptionRepository.processText(
                    text = analysisResult.textResult.text,
                    imageFile = analysisResult.processedImage
                )
            }
            is ImageTextAnalysisResult.LowConfidenceFallback -> {
                Log.d("ReadPrescription", "Confiança baixa. Imagem de fallback gerada em: ${analysisResult.processedImage.absolutePath}")

                prescriptionRepository.processImage(
                    text = analysisResult.textResult.text,
                    imageFile = analysisResult.processedImage
                )
            }
            is ImageTextAnalysisResult.Error -> {
                Log.e("ReadPrescription", "Erro ao processar a imagem da receita", analysisResult.exception)
                Result.failure(analysisResult.exception)
            }
        }
    }
}