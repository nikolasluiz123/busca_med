package br.com.android.buscamed.presentation.screen.capture

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import br.com.android.buscamed.presentation.screen.capture.components.CameraXPreview
import br.com.android.buscamed.presentation.screen.capture.components.InteractiveOverlay
import br.com.android.buscamed.presentation.screen.capture.components.takePhoto
import br.com.android.buscamed.presentation.screen.capture.state.CameraCaptureUIState
import br.com.android.buscamed.presentation.viewmodel.CameraCaptureViewModel

/**
 * Tela orquestradora para o fluxo de captura.
 */
@Composable
fun DocumentCaptureScreen(viewModel: CameraCaptureViewModel) {
    val state by viewModel.uiState.collectAsState()

    DocumentCaptureScreen(
        state = state,
        onAnalyzeFrame = viewModel.frameAnalyzer::analyze,
        onCaptureStarted = viewModel::onCaptureStarted,
        onPictureTaken = viewModel::onPictureTaken,
        onCaptureError = viewModel::onCaptureError
    )
}

/**
 * @param state Estado puramente de dados da tela.
 * @param onAnalyzeFrame Evento de entrega do frame para análise externa.
 * @param onPictureTaken Evento de sucesso após disparo da câmera.
 * @param onCaptureError Evento de falha no disparo da câmera.
 */
@Composable
fun DocumentCaptureScreen(
    state: CameraCaptureUIState,
    onAnalyzeFrame: (ImageProxy) -> Unit,
    onCaptureStarted: () -> Unit,
    onPictureTaken: (String) -> Unit,
    onCaptureError: (Exception) -> Unit
) {
    val context = LocalContext.current
    val imageCapture = remember {
        ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .build()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            CameraXPreview(
                onAnalyzeFrame = onAnalyzeFrame,
                imageCapture = imageCapture,
                modifier = Modifier.fillMaxSize()
            )

            InteractiveOverlay(
                overlayColor = state.analyzerState.overlayColor,
                dynamicBox = state.textBoundingBox,
                modifier = Modifier.fillMaxSize()
            )

            Button(
                onClick = {
                    onCaptureStarted()
                    takePhoto(
                        context = context,
                        imageCapture = imageCapture,
                        executor = ContextCompat.getMainExecutor(context),
                        onSuccess = onPictureTaken,
                        onError = onCaptureError
                    )
                },
                enabled = state.isCaptureButtonEnabled && !state.isCapturing,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            ) {
                Text(
                    text = if (state.isCapturing) "Processando..." else "Capturar"
                )
            }
        }
    }
}