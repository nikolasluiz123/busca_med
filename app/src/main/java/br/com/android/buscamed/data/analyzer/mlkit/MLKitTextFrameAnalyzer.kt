package br.com.android.buscamed.data.analyzer.mlkit

import android.media.Image
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import br.com.android.buscamed.domain.analyzer.FrameAnalyzer
import br.com.android.buscamed.domain.model.capture.AnalyzerState
import br.com.android.buscamed.domain.model.capture.BoundingBox
import br.com.android.buscamed.domain.model.capture.FrameAnalysisResult
import br.com.android.buscamed.domain.model.capture.ImageDimension
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class MLKitTextFrameAnalyzer @Inject constructor() : FrameAnalyzer<ImageProxy, Unit> {

    private val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    private val _state = MutableStateFlow(FrameAnalysisResult<Unit>())
    override val state: StateFlow<FrameAnalysisResult<Unit>> = _state.asStateFlow()

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(frame: ImageProxy) {
        val mediaImage: Image? = frame.image
        if (mediaImage != null) {
            val rotationDegrees = frame.imageInfo.rotationDegrees
            val isPortrait = rotationDegrees == 90 || rotationDegrees == 270

            val imageWidth = if (isPortrait) frame.height else frame.width
            val imageHeight = if (isPortrait) frame.width else frame.height
            val imageDimensions = ImageDimension(width = imageWidth, height = imageHeight)

            val image = InputImage.fromMediaImage(mediaImage, rotationDegrees)

            recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    if (visionText.textBlocks.isEmpty()) {
                        _state.value = FrameAnalysisResult(
                            state = AnalyzerState.NOT_DETECTED,
                            boundingBox = null,
                            sourceDimensions = imageDimensions
                        )
                    } else {
                        var minLeft = Float.MAX_VALUE
                        var minTop = Float.MAX_VALUE
                        var maxRight = Float.MIN_VALUE
                        var maxBottom = Float.MIN_VALUE

                        var validBlocksCount = 0

                        for (block in visionText.textBlocks) {
                            block.boundingBox?.let { box ->
                                minLeft = min(minLeft, box.left.toFloat())
                                minTop = min(minTop, box.top.toFloat())
                                maxRight = max(maxRight, box.right.toFloat())
                                maxBottom = max(maxBottom, box.bottom.toFloat())
                                validBlocksCount++
                            }
                        }

                        if (validBlocksCount == 0) {
                            _state.value = FrameAnalysisResult(
                                state = AnalyzerState.NOT_DETECTED,
                                boundingBox = null,
                                sourceDimensions = imageDimensions
                            )
                            return@addOnSuccessListener
                        }

                        val globalBoundingBox = BoundingBox(
                            left = minLeft,
                            top = minTop,
                            right = maxRight,
                            bottom = maxBottom
                        )

                        val boundingBoxArea = (maxRight - minLeft) * (maxBottom - minTop)
                        val totalImageArea = imageDimensions.width * imageDimensions.height
                        val areaRatio = boundingBoxArea / totalImageArea.toFloat()

                        val isAligned = validBlocksCount >= 2 && areaRatio >= 0.10f

                        _state.value = FrameAnalysisResult(
                            state = if (isAligned) AnalyzerState.ALIGNED else AnalyzerState.PARTIAL,
                            boundingBox = globalBoundingBox,
                            sourceDimensions = imageDimensions
                        )
                    }
                }
                .addOnFailureListener {
                    _state.value = FrameAnalysisResult(
                        state = AnalyzerState.NOT_DETECTED,
                        boundingBox = null,
                        sourceDimensions = imageDimensions
                    )
                }
                .addOnCompleteListener {
                    frame.close()
                }
        } else {
            frame.close()
        }
    }

    override fun close() {
        recognizer.close()
    }
}