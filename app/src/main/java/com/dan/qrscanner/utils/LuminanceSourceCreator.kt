package com.dan.qrscanner.utils

import androidx.camera.core.ImageProxy
import com.dan.qrscanner.extension.byteArray
import com.google.zxing.PlanarYUVLuminanceSource

object LuminanceSourceCreator {

    fun from(image: ImageProxy) =
        PlanarYUVLuminanceSource(
            image.byteArray,
            image.width,
            image.height,
            0,
            0,
            image.width,
            image.height,
            false
        )
}