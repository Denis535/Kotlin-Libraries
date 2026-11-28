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
        val width = image.Width
        val height = image.Height
        val stride = when (image.Format) {
            ImageFormat.RGB -> image.Width * 3
            ImageFormat.RGBA -> image.Width * 4
        }
        memScoped {
            if (isAlpha) {
                val dataPtr = this.alloc<CPointerVar<UByteVar>>()
                try {
                    val dataSize = if (lossless) {
                        image.Pixels.usePinned { pixels ->
                            val pixelsPtr = pixels.addressOf(0).reinterpret<UByteVar>()
                            WebPEncodeLosslessRGBA(pixelsPtr, width, height, stride, dataPtr.ptr)
                        }
                    } else {
                        image.Pixels.usePinned { pixels ->
                            val pixelsPtr = pixels.addressOf(0).reinterpret<UByteVar>()
                            WebPEncodeRGBA(pixelsPtr, width, height, stride, quality.coerceIn(0f, 100f), dataPtr.ptr)
                        }
                    }
                    if (dataPtr.value != null && dataSize > 0uL) {
                        return dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                    }
                    return null
                } finally {
                    WebPFree(dataPtr.value)
                }
            } else {
                val dataPtr = this.alloc<CPointerVar<UByteVar>>()
                try {
                    val dataSize = if (lossless) {
                        image.Pixels.usePinned { pixels ->
                            val pixelsPtr = pixels.addressOf(0).reinterpret<UByteVar>()
                            WebPEncodeLosslessRGB(pixelsPtr, width, height, stride, dataPtr.ptr)
                        }
                    } else {
                        image.Pixels.usePinned { pixels ->
                            val pixelsPtr = pixels.addressOf(0).reinterpret<UByteVar>()
                            WebPEncodeRGB(pixelsPtr, width, height, stride, quality.coerceIn(0f, 100f), dataPtr.ptr)
                        }
                    }
                    if (dataPtr.value != null && dataSize > 0uL) {
                        return dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                    }
                    return null
                } finally {
                    WebPFree(dataPtr.value)
                }
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun EncodeAnimatedImage(image: AnimatedImage, lossless: Boolean = false, quality: Float = 80f): ByteArray? {
        val isAlpha = when (image.Format) {
            ImageFormat.RGB -> false
            ImageFormat.RGBA -> true
        }
        val width = image.Width
        val height = image.Height
        val stride = when (image.Format) {
            ImageFormat.RGB -> image.Width * 3
            ImageFormat.RGBA -> image.Width * 4
        }
        memScoped {
            val config = run {
                val result = this.alloc<WebPConfig>()
                if (WebPConfigInit(result.ptr) == 0) return null
                result.lossless = if (lossless) 1 else 0
                result.quality = quality.coerceIn(0f, 100f)
                if (WebPValidateConfig(result.ptr) == 0) return null
                result
            }
            val encoder = run {
                val options = run {
                    val result = this.alloc<WebPAnimEncoderOptions>()
                    if (WebPAnimEncoderOptionsInit(result.ptr) == 0) return null
                    result.anim_params.loop_count = 0
                    result
                }
                WebPAnimEncoderNew(width, height, options.ptr) ?: return null
            }
            val output = this.alloc<WebPData>()
            WebPDataInit(output.ptr)
            try {
                return null
            } finally {
                WebPDataClear(output.ptr)
                WebPAnimEncoderDelete(encoder)
            }
        }
    }

}
