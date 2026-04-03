package br.com.android.buscamed.presentation.screen.capture.analyzer

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import br.com.android.buscamed.presentation.screen.capture.state.AnalyzerState
import br.com.android.buscamed.presentation.screen.capture.state.BoundingBox
import br.com.android.buscamed.presentation.screen.capture.state.FrameAnalysisResult
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MLKitTextFrameAnalyzer : FrameAnalyzer {

    private val _state = MutableStateFlow(FrameAnalysisResult())
    override val state: StateFlow<FrameAnalysisResult> = _state.asStateFlow()

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
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

        val isPortrait = imageProxy.imageInfo.rotationDegrees == 90 || imageProxy.imageInfo.rotationDegrees == 270
        val imageWidth = if (isPortrait) imageProxy.height else imageProxy.width
        val imageHeight = if (isPortrait) imageProxy.width else imageProxy.height

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val blocks = visionText.textBlocks

                if (blocks.isEmpty()) {
                    _state.value = FrameAnalysisResult(AnalyzerState.NO_DOCUMENT, null)
                } else {
                    var minLeft = Float.MAX_VALUE
                    var minTop = Float.MAX_VALUE
                    var maxRight = Float.MIN_VALUE
                    var maxBottom = Float.MIN_VALUE

                    val defaultLeftBound = imageWidth * 0.075f
                    val defaultRightBound = imageWidth * 0.925f
                    val defaultTopBound = imageHeight * 0.325f
                    val defaultBottomBound = imageHeight * 0.675f

                    var blocksInZone = 0

                    for (block in blocks) {
                        block.boundingBox?.let { box ->
                            minLeft = minOf(minLeft, box.left.toFloat())
                            minTop = minOf(minTop, box.top.toFloat())
                            maxRight = maxOf(maxRight, box.right.toFloat())
                            maxBottom = maxOf(maxBottom, box.bottom.toFloat())

                            if (box.exactCenterX() in defaultLeftBound..defaultRightBound &&
                                box.exactCenterY() in defaultTopBound..defaultBottomBound) {
                                blocksInZone++
                            }
                        }
                    }

                    val boundingBox = BoundingBox(
                        left = minLeft / imageWidth,
                        top = minTop / imageHeight,
                        right = maxRight / imageWidth,
                        bottom = maxBottom / imageHeight
                    )

                    val analyzerState = when {
                        blocksInZone >= 2 -> AnalyzerState.ALIGNED_AND_READY
                        else -> AnalyzerState.PARTIAL_DOCUMENT
                    }

                    _state.value = FrameAnalysisResult(analyzerState, boundingBox)
                }
            }
            .addOnFailureListener { e ->
                Log.e("MLKitTextFrameAnalyzer", "Falha na análise de texto", e)
                _state.value = FrameAnalysisResult(AnalyzerState.NO_DOCUMENT, null)
            }
            .addOnCompleteListener {
                isAnalyzing = false
                imageProxy.close()
            }
    }

    override fun clear() {
        recognizer.close()
    }
}