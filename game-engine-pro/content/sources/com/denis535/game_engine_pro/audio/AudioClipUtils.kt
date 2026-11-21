package com.denis535.game_engine_pro.audio

import kotlinx.cinterop.*
import opusfile.*

public object AudioClipUtils {

    @OptIn(ExperimentalForeignApi::class)
    public fun Decode(data: ByteArray): AudioClip? { // ogg (opus)
        data.usePinned { data ->
            val dataPtr = data.addressOf(0).reinterpret<UByteVar>()
            val dataSize = data.get().size
            memScoped {
                val error = this.alloc<IntVar>()
                val oggOpus = op_open_memory(dataPtr, dataSize.toULong(), error.ptr)
                try {
                    if (oggOpus != null) {
                        val frameCount = op_pcm_total(oggOpus, -1)
                        val channelCount = op_channel_count(oggOpus, -1)
                        if (frameCount > 0 && channelCount > 0) {
                            val buffer = this.allocArray<ShortVar>(5760 * channelCount)
                            val samples = ShortArray(frameCount.toInt() * channelCount)
                            var index = 0
                            while (true) {
                                val readFrameCount = op_read(oggOpus, buffer, 5760 * channelCount, null)
                                if (readFrameCount == 0) break
                                if (readFrameCount == OP_HOLE) return null
                                if (readFrameCount < 0) return null
                                for (i in 0 until readFrameCount * channelCount) {
                                    samples[index++] = buffer[i]
                                }
                            }
                            return AudioClip(frameCount.toInt(), channelCount, 48000, samples)
                        }
                    }
                } finally {
                    op_free(oggOpus)
                }
            }
        }
        return null
    }

}
