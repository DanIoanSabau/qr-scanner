package com.dan.qrscanner.utils

import android.graphics.ImageFormat
import com.google.zxing.BarcodeFormat

val supportedImageFormats = listOf(
    ImageFormat.YUV_420_888,
    ImageFormat.YUV_422_888,
    ImageFormat.YUV_444_888,
)

val supportedBarCodeFormats = listOf(
    BarcodeFormat.QR_CODE,
)