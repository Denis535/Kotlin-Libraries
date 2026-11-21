package com.denis535.game_engine_pro.video

import kotlinx.cinterop.*
import webp.*

public object ImageUtils {

    // webp
    @OptIn(ExperimentalForeignApi::class)
    public fun Decode(data: ByteArray, isAlpha: Boolean = false): Image? {
        data.usePinned { data ->
            val dataPtr = data.addressOf(0).reinterpret<UByteVar>()
            val dataSize = data.get().size.toULong()
            memScoped {
                val width = this.alloc<IntVar>()
                val height = this.alloc<IntVar>()
                if (WebPGetInfo(dataPtr, dataSize, width.ptr, height.ptr) != 0) {
                    if (isAlpha) {
                        val pixelsPtr = WebPDecodeRGBA(dataPtr, dataSize, width.ptr, height.ptr)
                        try {
                            if (pixelsPtr != null) {
                                val pixels = pixelsPtr.reinterpret<ByteVar>().readBytes(width.value * height.value * 4)
                                return Image(Image.ImageFormat.RGBA, width.value, height.value, pixels)
                            }
                        } finally {
                            WebPFree(pixelsPtr)
                        }
                    } else {
                        val pixelsPtr = WebPDecodeRGB(dataPtr, dataSize, width.ptr, height.ptr)
                        try {
                            if (pixelsPtr != null) {
                                val pixels = pixelsPtr.reinterpret<ByteVar>().readBytes(width.value * height.value * 3)
                                return Image(Image.ImageFormat.RGB, width.value, height.value, pixels)
                            }
                        } finally {
                            WebPFree(pixelsPtr)
                        }
                    }
                }
            }
        }
        return null
    }

    // webp
    @OptIn(ExperimentalForeignApi::class)
    public fun Encode(image: Image, lossless: Boolean = false, quality: Float = 80f): ByteArray? {
        val isAlpha = when (image.Format) {
            Image.ImageFormat.RGB -> false
            Image.ImageFormat.RGBA -> true
        }
        val stride = when (image.Format) {
            Image.ImageFormat.RGB -> image.Width * 3
            Image.ImageFormat.RGBA -> image.Width * 4
        }
        val width = image.Width
        val height = image.Height
        image.Pixels.usePinned { pixels ->
            val pixelsPtr = pixels.addressOf(0).reinterpret<UByteVar>()
            memScoped {
                if (isAlpha) {
                    if (lossless) {
                        val dataPtr = this.alloc<CPointerVar<UByteVar>>()
                        try {
                            val dataSize = WebPEncodeLosslessRGBA(pixelsPtr, width, height, stride, dataPtr.ptr)
                            if (dataPtr.value != null && dataSize != 0uL) {
                                return dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                            }
                        } finally {
                            WebPFree(dataPtr.value)
                        }
                    } else {
                        val dataPtr = this.alloc<CPointerVar<UByteVar>>()
                        try {
                            val dataSize = WebPEncodeRGBA(pixelsPtr, width, height, stride, quality.coerceIn(0f, 100f), dataPtr.ptr)
                            if (dataPtr.value != null && dataSize != 0uL) {
                                return dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                            }
                        } finally {
                            WebPFree(dataPtr.value)
                        }
                    }
                } else {
                    if (lossless) {
                        val dataPtr = this.alloc<CPointerVar<UByteVar>>()
                        try {
                            val dataSize = WebPEncodeLosslessRGB(pixelsPtr, width, height, stride, dataPtr.ptr)
                            if (dataPtr.value != null && dataSize != 0uL) {
                                return dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                            }
                        } finally {
                            WebPFree(dataPtr.value)
                        }
                    } else {
                        val dataPtr = this.alloc<CPointerVar<UByteVar>>()
                        try {
                            val dataSize = WebPEncodeRGB(pixelsPtr, width, height, stride, quality.coerceIn(0f, 100f), dataPtr.ptr)
                            if (dataPtr.value != null && dataSize != 0uL) {
                                return dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                            }
                        } finally {
                            WebPFree(dataPtr.value)
                        }
                    }
                }
            }
        }
        return null
    }

}