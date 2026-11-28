package com.denis535.game_engine_pro.io

import com.denis535.game_engine_pro.video.*
import kotlinx.cinterop.*
import webp.*

public object WebpEncoder {

    @OptIn(ExperimentalForeignApi::class)
    public fun EncodeImage(image: Image, lossless: Boolean = false, quality: Float = 80f): ByteArray? {
        val width = image.Width
        val height = image.Height
        val stride = when (image.Format) {
            ImageFormat.RGB -> image.Width * 3
            ImageFormat.RGBA -> image.Width * 4
        }
        val isAlpha = when (image.Format) {
            ImageFormat.RGB -> false
            ImageFormat.RGBA -> true
        }
        val pixels = image.Pixels
        memScoped {
            val dataPtr = this.alloc<CPointerVar<UByteVar>>()
            try {
                pixels.usePinned { pixels ->
                    val pixelsPtr = pixels.addressOf(0).reinterpret<UByteVar>()
                    val dataSize = if (lossless) {
                        if (isAlpha) {
                            WebPEncodeLosslessRGBA(pixelsPtr, width, height, stride, dataPtr.ptr)
                        } else {
                            WebPEncodeLosslessRGB(pixelsPtr, width, height, stride, dataPtr.ptr)
                        }
                    } else {
                        if (isAlpha) {
                            WebPEncodeRGBA(pixelsPtr, width, height, stride, quality.coerceIn(0f, 100f), dataPtr.ptr)
                        } else {
                            WebPEncodeRGB(pixelsPtr, width, height, stride, quality.coerceIn(0f, 100f), dataPtr.ptr)
                        }
                    }
                    if (dataSize > 0uL) {
                        return dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                    }
                    return null
                }
            } finally {
                WebPFree(dataPtr.value)
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun EncodeAnimatedImage(image: AnimatedImage, lossless: Boolean = false, quality: Float = 80f): ByteArray? {
        val width = image.Width
        val height = image.Height
        val stride = when (image.Format) {
            ImageFormat.RGB -> image.Width * 3
            ImageFormat.RGBA -> image.Width * 4
        }
        val isAlpha = when (image.Format) {
            ImageFormat.RGB -> false
            ImageFormat.RGBA -> true
        }
        val frames = image.Frames
        memScoped {
            val encoder = run {
                val options = run {
                    val result = this.alloc<WebPAnimEncoderOptions>()
                    if (WebPAnimEncoderOptionsInit(result.ptr) == 0) return null
                    result.anim_params.loop_count = 0
                    result
                }
                WebPAnimEncoderNew(width, height, options.ptr) ?: return null
            }
            val picture = run {
                val result = this.alloc<WebPPicture>()
                if (WebPPictureInit(result.ptr) == 0) return null
                result.use_argb = 1
                result.width = width
                result.height = height
                result
            }
            val config = run {
                val result = this.alloc<WebPConfig>()
                if (WebPConfigInit(result.ptr) == 0) return null
                result.lossless = if (lossless) 1 else 0
                result.quality = quality.coerceIn(0f, 100f)
                if (WebPValidateConfig(result.ptr) == 0) return null
                result
            }
            val output = run {
                val result = this.alloc<WebPData>()
                WebPDataInit(result.ptr)
                result
            }
            try {
                var prevTimestampMs = 0
                for (frame in frames) {
                    frame.Pixels.usePinned { pixels ->
                        val pixelsPtr = pixels.addressOf(0).reinterpret<UByteVar>()
                        try {
                            if (isAlpha) {
                                if (WebPPictureImportRGBA(picture.ptr, pixelsPtr, stride) == 0) return null
                            } else {
                                if (WebPPictureImportRGB(picture.ptr, pixelsPtr, stride) == 0) return null
                            }
                            if (WebPAnimEncoderAdd(encoder, picture.ptr, prevTimestampMs, config.ptr) == 0) return null
                        } finally {
                            WebPPictureFree(picture.ptr)
                        }
                    }
                    prevTimestampMs = frame.TimestampMs
                }
                if (WebPAnimEncoderAdd(encoder, null, prevTimestampMs, null) == 0) return null
                if (WebPAnimEncoderAssemble(encoder, output.ptr) == 0) return null
                return output.bytes!!.reinterpret<ByteVar>().readBytes(output.size.toInt())
            } finally {
                WebPDataClear(output.ptr)
                WebPAnimEncoderDelete(encoder)
            }
        }
    }

}
