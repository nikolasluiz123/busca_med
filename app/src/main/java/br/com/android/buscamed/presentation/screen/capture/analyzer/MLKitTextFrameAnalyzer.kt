package br.com.android.buscamed.presentation.screen.capture.analyzer

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import br.com.android.buscamed.presentation.screen.capture.state.AnalyzerState
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MLKitTextFrameAnalyzer : FrameAnalyzer {

    private val _state = MutableStateFlow(AnalyzerState.NO_DOCUMENT)
    override val state: StateFlow<AnalyzerState> = _state.asStateFlow()

    // Instancia o cliente nativo do ML Kit
    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    
    // Flag para descartar quadros enquanto um já está sendo processado (evita gargalos)
    private var isAnalyzing = false

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        
        if (mediaImage == null || isAnalyzing) {
            imageProxy.close()
            return
        }

        isAnalyzing = true
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

        // O ML Kit mapeia as coordenadas com base na imagem já rotacionada para cima.
        // Precisamos saber as dimensões reais de quem está "em pé".
        val isPortrait = imageProxy.imageInfo.rotationDegrees == 90 || imageProxy.imageInfo.rotationDegrees == 270
        val imageWidth = if (isPortrait) imageProxy.height else imageProxy.width
        val imageHeight = if (isPortrait) imageProxy.width else imageProxy.height

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val blocks = visionText.textBlocks
                
                if (blocks.isEmpty()) {
                    _state.value = AnalyzerState.NO_DOCUMENT
                } else {
                    // Mapeia a "Zona de Interesse" (ROI) equivalente ao overlay visual
                    // Nosso InteractiveOverlay tem 85% de largura e 35% de altura no centro.
                    val leftBound = imageWidth * 0.075f  // (100% - 85%) / 2
                    val rightBound = imageWidth * 0.925f
                    val topBound = imageHeight * 0.325f  // (100% - 35%) / 2
                    val bottomBound = imageHeight * 0.675f

                    var blocksInZone = 0

                    for (block in blocks) {
                        block.boundingBox?.let { box ->
                            // Avalia se o centro do bloco de texto detectado está dentro do retângulo
                            if (box.exactCenterX() in leftBound..rightBound && 
                                box.exactCenterY() in topBound..bottomBound) {
                                blocksInZone++
                            }
                        }
                    }

                    // Transição de estados baseada na quantidade de texto útil no centro
                    _state.value = when {
                        blocksInZone >= 2 -> AnalyzerState.ALIGNED_AND_READY // Exige ao menos 2 blocos de texto centralizados
                        blocks.isNotEmpty() -> AnalyzerState.PARTIAL_DOCUMENT // Achou texto, mas está fora do quadro
                        else -> AnalyzerState.NO_DOCUMENT
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("MLKitTextFrameAnalyzer", "Falha na análise de texto", e)
                _state.value = AnalyzerState.NO_DOCUMENT
            }
            .addOnCompleteListener {
                isAnalyzing = false
                imageProxy.close() // CRÍTICO: Libera o buffer para o CameraX enviar o próximo quadro
            }
    }

    override fun clear() {
        recognizer.close()
    }
}