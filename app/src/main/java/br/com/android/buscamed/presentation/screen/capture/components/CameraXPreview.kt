package br.com.android.buscamed.presentation.screen.capture.components

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.concurrent.futures.await
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.camera.core.ImageProxy
import java.io.File
import java.util.concurrent.Executor

/**
 * @param onAnalyzeFrame Evento disparado a cada quadro processado pela câmera.
 * @param imageCapture Instância configurada para captura de alta resolução.
 * @param modifier Modificador de layout.
 */
@Composable
fun CameraXPreview(
    onAnalyzeFrame: (ImageProxy) -> Unit,
    imageCapture: ImageCapture,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    LaunchedEffect(Unit) {
        val cameraProvider = context.getCameraProvider()
        val preview = Preview.Builder().build().also {
            it.surfaceProvider = previewView.surfaceProvider
        }

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also { analysis ->
                analysis.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                    onAnalyzeFrame(imageProxy)
                }
            }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture,
                imageAnalysis
            )
        } catch (e: Exception) {
            Log.e("CameraXPreview", "Falha ao vincular CameraX", e)
        }
    }

    AndroidView(
        factory = { previewView },
        modifier = modifier.fillMaxSize()
    )
}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider {
    return ProcessCameraProvider.getInstance(this).await()
}

/**
 * @param context Contexto local.
 * @param imageCapture Instância ativa do ImageCapture vinculada ao lifecycle.
 * @param executor Executor responsável pelo gerenciamento da thread de captura.
 * @param onSuccess Retorno de sucesso contendo o caminho absoluto do arquivo.
 * @param onError Retorno em caso de falha na captura.
 */
fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    executor: Executor,
    onSuccess: (String) -> Unit,
    onError: (Exception) -> Unit
) {
    val photoFile = File(context.cacheDir, "doc_capture_${System.currentTimeMillis()}.jpg")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                onSuccess(photoFile.absolutePath)
            }

            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }
        }
    )
}