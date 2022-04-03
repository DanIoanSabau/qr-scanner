package com.dan.qrscanner.extension

import androidx.camera.core.ImageProxy

val ImageProxy.byteArray: ByteArray
    get() = planes.first().buffer.toByteArray()