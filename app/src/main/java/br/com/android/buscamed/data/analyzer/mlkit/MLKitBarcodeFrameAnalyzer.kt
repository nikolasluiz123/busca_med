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
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MLKitBarcodeFrameAnalyzer @Inject constructor() : FrameAnalyzer<ImageProxy, String> {

    private val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_13, Barcode.FORMAT_EAN_8)
        .build()

    private val scanner = BarcodeScanning.getClient(options)

    private val _state = MutableStateFlow(FrameAnalysisResult<String>())
    override val state: StateFlow<FrameAnalysisResult<String>> = _state.asStateFlow()

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

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isEmpty()) {
                        _state.value = FrameAnalysisResult(
                            state = AnalyzerState.NOT_DETECTED,
                            boundingBox = null,
                            sourceDimensions = imageDimensions,
                            payload = null
                        )
                        return@addOnSuccessListener
                    }

                    val barcode = barcodes.first()
                    val rawValue = barcode.rawValue

                    val boundingBox = barcode.boundingBox?.let { box ->
                        BoundingBox(
                            left = box.left.toFloat(),
                            top = box.top.toFloat(),
                            right = box.right.toFloat(),
                            bottom = box.bottom.toFloat()
                        )
                    }

                    val currentState = if (rawValue != null && boundingBox != null) {
                        AnalyzerState.ALIGNED
                    } else if (boundingBox != null) {
                        AnalyzerState.PARTIAL
                    } else {
                        AnalyzerState.NOT_DETECTED
                    }

                    _state.value = FrameAnalysisResult(
                        state = currentState,
                        boundingBox = boundingBox,
                        sourceDimensions = imageDimensions,
                        payload = rawValue
                    )
                }
                .addOnFailureListener {
                    _state.value = FrameAnalysisResult(
                        state = AnalyzerState.NOT_DETECTED,
                        boundingBox = null,
                        sourceDimensions = imageDimensions,
                        payload = null
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
        scanner.close()
    }
}