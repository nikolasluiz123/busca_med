package br.com.android.buscamed.domain.usecase.capture

import android.util.Log
import br.com.android.buscamed.domain.processor.ImageProcessorStep
import br.com.android.buscamed.domain.provider.TextRecognitionProvider
import br.com.android.buscamed.domain.rule.ConfidenceRule
import java.io.File
import javax.inject.Inject

/**
 * Orquestra o fluxo de preparação da imagem, extração de texto e avaliação de confiança.
 *
 * @property textRecognitionProvider O provedor que realizará o OCR.
 */
class AnalyzeImageTextUseCase @Inject constructor(
    private val textRecognitionProvider: TextRecognitionProvider,
) {
    /**
     * Executa o fluxo de processamento num ficheiro de imagem.
     *
     * @param imagePath O caminho absoluto do ficheiro capturado.
     * @param imageProcessorSteps Lista sequencial de passos para tratar a imagem.
     * @param confidenceRules Lista de regras que o texto deve cumprir para ser validado.
     * @return O resultado consolidado da análise.
     */
    suspend operator fun invoke(
        imagePath: String,
        imageProcessorSteps: List<ImageProcessorStep>,
        confidenceRules: List<ConfidenceRule>
    ): ImageTextAnalysisResult {
        return try {
            var currentImage = File(imagePath)
            
            imageProcessorSteps.forEach { step ->
                currentImage = step.process(currentImage)
            }

            val textResult = textRecognitionProvider.recognizeText(currentImage)

            Log.d("AnalyzeImageText", "Texto extraído:\n${textResult.text}")

            val isConfident = confidenceRules.all { rule ->
                val result = rule.evaluate(textResult)
                Log.d("AnalyzeImageText", "Rule ${rule.javaClass.simpleName} evaluated to: $result")
                result
            }

            if (isConfident) {
                ImageTextAnalysisResult.HighlyConfident(textResult)
            } else {
                ImageTextAnalysisResult.LowConfidenceFallback(currentImage)
            }
        } catch (e: Exception) {
            ImageTextAnalysisResult.Error(e)
        }
    }
}