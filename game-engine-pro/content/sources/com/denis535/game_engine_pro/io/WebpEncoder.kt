package com.denis535.game_engine_pro.io

import com.denis535.game_engine_pro.video.*
import kotlinx.cinterop.*
import webp.*

public object WebpEncoder {

    @OptIn(ExperimentalForeignApi::class)
    public fun EncodeImage(image: Image, lossless: Boolean = false, quality: Float = 80f): ByteArray? {
        val isAlpha = when (image.Format) {
            ImageFormat.RGB -> false
            ImageFormat.RGBA -> true
        }
        val stride = when (image.Format) {
            ImageFormat.RGB -> image.Width * 3
            ImageFormat.RGBA -> image.Width * 4
        }
        val width = image.Width
        val height = image.Height
        image.Pixels.usePinned { pixels ->
            val pixelsPtr = pixels.addressOf(0).reinterpret<UByteVar>()
            memScoped {
                if (isAlpha) {
                    val dataPtr = this.alloc<CPointerVar<UByteVar>>()
                    try {
                        val dataSize = if (lossless) {
                            WebPEncodeLosslessRGBA(pixelsPtr, width, height, stride, dataPtr.ptr)
                        } else {
                            WebPEncodeRGBA(pixelsPtr, width, height, stride, quality.coerceIn(0f, 100f), dataPtr.ptr)
                        }
                        if (dataPtr.value != null && dataSize > 0uL) {
                            return dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                        }
                    } finally {
                        WebPFree(dataPtr.value)
                    }
                } else {
                    val dataPtr = this.alloc<CPointerVar<UByteVar>>()
                    try {
                        val dataSize = if (lossless) {
                            WebPEncodeLosslessRGB(pixelsPtr, width, height, stride, dataPtr.ptr)
                        } else {
                            WebPEncodeRGB(pixelsPtr, width, height, stride, quality.coerceIn(0f, 100f), dataPtr.ptr)
                        }
                        if (dataPtr.value != null && dataSize > 0uL) {
                            return dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                        }
                    } finally {
                        WebPFree(dataPtr.value)
                    }
                }
            }
        }
        return null
    }

}
