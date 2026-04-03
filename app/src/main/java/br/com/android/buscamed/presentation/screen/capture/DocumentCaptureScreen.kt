package br.com.android.buscamed.presentation.screen.capture

import android.content.res.Configuration
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import br.com.android.buscamed.presentation.screen.capture.components.CameraXPreview
import br.com.android.buscamed.presentation.screen.capture.components.InteractiveOverlay
import br.com.android.buscamed.presentation.screen.capture.components.takePhoto
import br.com.android.buscamed.presentation.screen.capture.state.CameraCaptureUIState
import br.com.android.buscamed.presentation.viewmodel.CameraCaptureViewModel

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

@Composable
fun DocumentCaptureScreen(
    state: CameraCaptureUIState,
    onAnalyzeFrame: (ImageProxy) -> Unit,
    onCaptureStarted: () -> Unit,
    onPictureTaken: (String) -> Unit,
    onCaptureError: (Exception) -> Unit
) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current

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
                .background(Color.Black)
        ) {
            CameraXPreview(
                onAnalyzeFrame = onAnalyzeFrame,
                imageCapture = imageCapture,
                modifier = Modifier.fillMaxSize()
            )

            InteractiveOverlay(
                analyzerState = state.analyzerState,
                boundingBox = state.textBoundingBox,
                sourceDimensions = state.sourceDimensions,
                modifier = Modifier.fillMaxSize()
            )

            val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            val buttonAlignment = if (isLandscape) Alignment.CenterEnd else Alignment.BottomCenter
            val buttonPadding = if (isLandscape) Modifier.padding(end = 48.dp) else Modifier.padding(bottom = 48.dp)

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .align(buttonAlignment)
                    .then(buttonPadding)
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(if (state.isCaptureButtonEnabled) Color.White else Color.LightGray)
                    .border(
                        width = 4.dp,
                        color = if (state.isCaptureButtonEnabled) Color.Gray else Color.DarkGray,
                        shape = CircleShape
                    )
                    .clickable(enabled = state.isCaptureButtonEnabled && !state.isCapturing) {
                        onCaptureStarted()
                        takePhoto(
                            context = context,
                            imageCapture = imageCapture,
                            executor = ContextCompat.getMainExecutor(context),
                            onSuccess = onPictureTaken,
                            onError = onCaptureError
                        )
                    }
            ) {
                if (state.isCapturing) {
                    CircularProgressIndicator(
                        color = Color.Black,
                        modifier = Modifier.size(32.dp),
                        strokeWidth = 3.dp
                    )
                }
            }
        }
    }
}