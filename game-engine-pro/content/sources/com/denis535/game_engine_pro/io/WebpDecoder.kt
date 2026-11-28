package com.denis535.game_engine_pro.io

import com.denis535.game_engine_pro.video.*
import kotlinx.cinterop.*
import webp.*

public object WebpDecoder {

    @OptIn(ExperimentalForeignApi::class)
    public fun DecodeImage(data: ByteArray, isAlpha: Boolean = false): Image? {
        data.usePinned { data ->
            val dataPtr = data.addressOf(0).reinterpret<UByteVar>()
            val dataSize = data.get().size.toULong()
            memScoped {
                val width = this.alloc<IntVar>()
                val height = this.alloc<IntVar>()
                if (WebPGetInfo(dataPtr, dataSize, width.ptr, height.ptr) == 0) return null
                run {
                    val pixelsPtr = if (isAlpha) {
                        WebPDecodeRGBA(dataPtr, dataSize, width.ptr, height.ptr) ?: return null
                    } else {
                        WebPDecodeRGB(dataPtr, dataSize, width.ptr, height.ptr) ?: return null
                    }
                    try {
                        if (isAlpha) {
                            val pixels = pixelsPtr.reinterpret<ByteVar>().readBytes(width.value * height.value * 4)
                            return Image(width.value, height.value, ImageFormat.RGBA, pixels)
                        } else {
                            val pixels = pixelsPtr.reinterpret<ByteVar>().readBytes(width.value * height.value * 3)
                            return Image(width.value, height.value, ImageFormat.RGB, pixels)
                        }
                    } finally {
                        WebPFree(pixelsPtr)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun DecodeAnimatedImage(data: ByteArray, isAlpha: Boolean = false): AnimatedImage? {
        data.usePinned { data ->
            val dataPtr = data.addressOf(0).reinterpret<UByteVar>()
            val dataSize = data.get().size.toULong()
            memScoped {
                val decoder = run {
                    val webpData = run {
                        val result = this.alloc<WebPData>()
                        result.bytes = dataPtr
                        result.size = dataSize
                        result
                    }
                    val options = run {
                        val result = this.alloc<WebPAnimDecoderOptions>()
                        if (WebPAnimDecoderOptionsInit(result.ptr) == 0) return null
                        result.color_mode = MODE_RGBA
                        result
                    }
                    WebPAnimDecoderNew(webpData.ptr, options.ptr) ?: return null
                }
                try {
                    val info = this.alloc<WebPAnimInfo>()
                    if (WebPAnimDecoderGetInfo(decoder, info.ptr) == 0) return null
                    val width = info.canvas_width.toInt()
                    val height = info.canvas_height.toInt()
                    run {
                        val frames = run {
                            val result = mutableListOf<AnimatedImage.Frame>()
                            val framePtr = this.alloc<CPointerVar<UByteVar>>()
                            val timestamp = this.alloc<IntVar>()
                            while (WebPAnimDecoderHasMoreFrames(decoder) != 0) {
                                if (WebPAnimDecoderGetNext(decoder, framePtr.ptr, timestamp.ptr) == 0) return null
                                val pixels = framePtr.value!!.reinterpret<ByteVar>().readBytes(width * height * 4).let {
                                    if (isAlpha) it else RgbaToRgb(it)
                                }
                                result += AnimatedImage.Frame(timestamp.value, pixels)
                            }
                            result
                        }
                        if (isAlpha) {
                            return AnimatedImage(width, height, ImageFormat.RGBA, frames)
                        } else {
                            return AnimatedImage(width, height, ImageFormat.RGB, frames)
                        }
                    }
                } finally {
                    WebPAnimDecoderDelete(decoder)
                }
            }
        }
    }

}

private fun RgbaToRgb(rgba: ByteArray): ByteArray {
    val rgb = ByteArray(rgba.size / 4 * 3)
    for (i in 0 until rgba.size / 4) {
        rgb[i * 3] = rgba[i * 4]
        rgb[i * 3 + 1] = rgba[i * 4 + 1]
        rgb[i * 3 + 2] = rgba[i * 4 + 2]
    }
    return rgb
}
