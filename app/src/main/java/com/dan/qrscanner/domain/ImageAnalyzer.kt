package com.dan.qrscanner.domain

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.dan.qrscanner.utils.LuminanceSourceCreator
import com.dan.qrscanner.utils.supportedBarCodeFormats
import com.dan.qrscanner.utils.supportedImageFormats
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.common.HybridBinarizer

class ImageAnalyzer(
    private val onQRCodeScanned: (String) -> Unit,
): ImageAnalysis.Analyzer {

    private val qrCodeReader = MultiFormatReader()

    init {
        qrCodeReader.setHints(
            mapOf(DecodeHintType.POSSIBLE_FORMATS to supportedBarCodeFormats)
        )
    }

    override fun analyze(image: ImageProxy) {
        if (image.format in supportedImageFormats) {
            val source = LuminanceSourceCreator.from(image)
            val bitmap = BinaryBitmap(HybridBinarizer(source))

            tryDecodeBitmap(bitmap)
        }
        image.close()
    }

    private fun tryDecodeBitmap(bitmap: BinaryBitmap) {
        try {
            val result = qrCodeReader.decode(bitmap)
            onQRCodeScanned(result.text)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}