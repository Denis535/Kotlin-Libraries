package com.denis535.game_engine_pro.video

import kotlinx.cinterop.*
import webp.*

public object ImageUtils {

    @OptIn(ExperimentalForeignApi::class)
    public fun Decode(data: ByteArray, isAlpha: Boolean = false): Image? {
        memScoped {
            data.usePinned { data ->
                val dataPtr = data.addressOf(0).reinterpret<UByteVar>()
                val dataSize = data.get().size.toULong()
                val width = this.alloc<IntVar>()
                val height = this.alloc<IntVar>()
                if (WebPGetInfo(dataPtr, dataSize, width.ptr, height.ptr) != 0) {
                    if (isAlpha) {
                        val pixelsPtr = WebPDecodeRGBA(dataPtr, dataSize, width.ptr, height.ptr)
                        if (pixelsPtr != null) {
                            val pixels = pixelsPtr.reinterpret<ByteVar>().readBytes(width.value * height.value * 4)
                            WebPFree(pixelsPtr)
                            return Image(width.value, height.value, Image.PixelFormat.RGBA, pixels)
                        }
                    } else {
                        val pixelsPtr = WebPDecodeRGB(dataPtr, dataSize, width.ptr, height.ptr)
                        if (pixelsPtr != null) {
                            val pixels = pixelsPtr.reinterpret<ByteVar>().readBytes(width.value * height.value * 3)
                            WebPFree(pixelsPtr)
                            return Image(width.value, height.value, Image.PixelFormat.RGB, pixels)
                        }
                    }
                }
            }
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Encode(image: Image, lossless: Boolean = false, quality: Float = 80f): ByteArray? {
        val width = image.Width
        val height = image.Height
        val isAlpha = when (image.Format) {
            Image.PixelFormat.RGB -> false
            Image.PixelFormat.RGBA -> true
        }
        val stride = when (image.Format) {
            Image.PixelFormat.RGB -> image.Width * 3
            Image.PixelFormat.RGBA -> image.Width * 4
        }
        memScoped {
            image.Pixels.usePinned { pixels ->
                val pixelsPtr = pixels.addressOf(0).reinterpret<UByteVar>()
                if (isAlpha) {
                    if (lossless) {
                        val dataPtr = this.alloc<CPointerVar<UByteVar>>()
                        val dataSize = WebPEncodeLosslessRGBA(pixelsPtr, width, height, stride, dataPtr.ptr)
                        if (dataPtr.value != null && dataSize != 0uL) {
                            val data = dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                            WebPFree(dataPtr.value)
                            return data
                        }
                    } else {
                        val dataPtr = this.alloc<CPointerVar<UByteVar>>()
                        val dataSize = WebPEncodeRGBA(pixelsPtr, width, height, stride, quality.coerceIn(0f, 100f), dataPtr.ptr)
                        if (dataPtr.value != null && dataSize != 0uL) {
                            val data = dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                            WebPFree(dataPtr.value)
                            return data
                        }
                    }
                } else {
                    if (lossless) {
                        val dataPtr = this.alloc<CPointerVar<UByteVar>>()
                        val dataSize = WebPEncodeLosslessRGB(pixelsPtr, width, height, stride, dataPtr.ptr)
                        if (dataPtr.value != null && dataSize != 0uL) {
                            val data = dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                            WebPFree(dataPtr.value)
                            return data
                        }
                    } else {
                        val dataPtr = this.alloc<CPointerVar<UByteVar>>()
                        val dataSize = WebPEncodeRGB(pixelsPtr, width, height, stride, quality.coerceIn(0f, 100f), dataPtr.ptr)
                        if (dataPtr.value != null && dataSize != 0uL) {
                            val data = dataPtr.value!!.reinterpret<ByteVar>().readBytes(dataSize.toInt())
                            WebPFree(dataPtr.value)
                            return data
                        }
                    }
                }
            }
        }
        return null
    }

}