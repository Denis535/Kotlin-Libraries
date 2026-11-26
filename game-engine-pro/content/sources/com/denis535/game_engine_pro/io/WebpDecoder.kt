package com.denis535.game_engine_pro.io

import com.denis535.game_engine_pro.video.*
import kotlinx.cinterop.*
import webp.*

public object WebpDecoder {

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
                                return Image(ImageFormat.RGBA, width.value, height.value, pixels)
                            }
                        } finally {
                            WebPFree(pixelsPtr)
                        }
                    } else {
                        val pixelsPtr = WebPDecodeRGB(dataPtr, dataSize, width.ptr, height.ptr)
                        try {
                            if (pixelsPtr != null) {
                                val pixels = pixelsPtr.reinterpret<ByteVar>().readBytes(width.value * height.value * 3)
                                return Image(ImageFormat.RGB, width.value, height.value, pixels)
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

}
