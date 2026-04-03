package br.com.android.buscamed.data.provider

import android.content.Context
import android.net.Uri
import br.com.android.buscamed.data.mapper.toDomain
import br.com.android.buscamed.domain.model.capture.TextResult
import br.com.android.buscamed.domain.provider.TextRecognitionProvider
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.tasks.await
import java.io.File

/**
 * Implementação de [TextRecognitionProvider] utilizando o motor Google ML Kit Vision.
 *
 * @property context Contexto da aplicação necessário para resolver a [Uri] do ficheiro de imagem.
 */
class MLKitTextRecognitionProvider(
    private val context: Context
) : TextRecognitionProvider {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    override suspend fun recognizeText(image: File): TextResult {
        val imageUri = Uri.fromFile(image)
        val inputImage = InputImage.fromFilePath(context, imageUri)
        
        val mlKitText = recognizer.process(inputImage).await()
        
        return mlKitText.toDomain()
    }
}