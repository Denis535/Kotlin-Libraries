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
                if (isAlpha) {
                    val pixelsPtr = WebPDecodeRGBA(dataPtr, dataSize, width.ptr, height.ptr) ?: return null
                    try {
                        val pixels = pixelsPtr.reinterpret<ByteVar>().readBytes(width.value * height.value * 4)
                        return Image(ImageFormat.RGBA, width.value, height.value, pixels)
                    } finally {
                        WebPFree(pixelsPtr)
                    }
                } else {
                    val pixelsPtr = WebPDecodeRGB(dataPtr, dataSize, width.ptr, height.ptr) ?: return null
                    try {
                        val pixels = pixelsPtr.reinterpret<ByteVar>().readBytes(width.value * height.value * 3)
                        return Image(ImageFormat.RGB, width.value, height.value, pixels)
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
                    val format = if (isAlpha) {
                        ImageFormat.RGBA
                    } else {
                        ImageFormat.RGB
                    }
                    val width = info.canvas_width.toInt()
                    val height = info.canvas_height.toInt()
                    val frames = mutableListOf<AnimatedImage.Frame>()
                    val framePtr = this.alloc<CPointerVar<UByteVar>>()
                    val timestamp = this.alloc<IntVar>()
                    while (WebPAnimDecoderHasMoreFrames(decoder) != 0) {
                        if (WebPAnimDecoderGetNext(decoder, framePtr.ptr, timestamp.ptr) == 0) return null
                        val pixels = framePtr.value!!.reinterpret<ByteVar>().readBytes(width * height * 4).let {
                            if (isAlpha) it else RgbaToRgb(it)
                        }
                        frames += AnimatedImage.Frame(timestamp.value, pixels)
                    }
                    return AnimatedImage(format, width, height, frames)
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
