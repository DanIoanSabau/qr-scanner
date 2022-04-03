package com.dan.qrscanner.presentation.component

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.dan.qrscanner.domain.ImageAnalyzer
import java.lang.Exception

@Composable
fun Camera(
    onQRCodeScanned: (String) -> Unit,
) {
    val cameraContext = LocalContext.current
    val cameraProvider = remember {
        ProcessCameraProvider.getInstance(cameraContext)
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    AndroidView(
        factory = { context ->
            val previewView = PreviewView(context)
            val preview = Preview.Builder().build()
            val selector = getBackCameraSelector()
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            preview.setSurfaceProvider(previewView.surfaceProvider)

            imageAnalyzer.setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    ImageAnalyzer(onQRCodeScanned)
                )

            try {
                cameraProvider.get()
                    .bindToLifecycle(
                        lifecycleOwner,
                        selector,
                        preview,
                        imageAnalyzer
                    )
            } catch (e: Exception) {
                e.printStackTrace()
            }

            previewView
        }
    )
}

private fun getBackCameraSelector() = CameraSelector.Builder()
    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
    .build()