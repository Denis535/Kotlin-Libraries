package com.denis535.game_engine_pro.audio

import kotlinx.cinterop.*
import opusfile.*

public object AudioClipUtils {

    // ogg (opus)
    @OptIn(ExperimentalForeignApi::class)
    public fun Decode(data: ByteArray, isStereo: Boolean = false): AudioClip? {
        data.usePinned { data ->
            val dataPtr = data.addressOf(0).reinterpret<UByteVar>()
            val dataSize = data.get().size
            memScoped {
                val error = this.alloc<IntVar>()
                val oggOpus = op_open_memory(dataPtr, dataSize.toULong(), error.ptr)
                try {
                    if (oggOpus != null) {
                        val channelCount = op_channel_count(oggOpus, -1) // mono, stereo, etc
                        val frameCount = op_pcm_total(oggOpus, -1)
                        if (channelCount > 0 && frameCount > 0) {
                            if (isStereo) {
                                val buffer = this.allocArray<ShortVar>(2 * 5760)
                                var index = 0
                                val frames = ShortArray(2 * frameCount.toInt())
                                while (true) {
                                    val readFrameCount = op_read_stereo(oggOpus, buffer, 2 * 5760)
                                    if (readFrameCount == 0) break
                                    if (readFrameCount < 0) return null
                                    for (i in 0 until readFrameCount) {
                                        frames[index++] = buffer[i * 2]
                                        frames[index++] = buffer[i * 2 + 1]
                                    }
                                }
                                return AudioClip(AudioClip.AudioFormat.Stereo, 48000, frameCount.toInt(), frames)
                            } else {
                                val buffer = this.allocArray<ShortVar>(2 * 5760)
                                var index = 0
                                val frames = ShortArray(frameCount.toInt())
                                while (true) {
                                    val readFrameCount = op_read_stereo(oggOpus, buffer, 2 * 5760)
                                    if (readFrameCount == 0) break
                                    if (readFrameCount < 0) return null
                                    for (i in 0 until readFrameCount) {
                                        frames[index++] = ((buffer[i * 2] + buffer[i * 2 + 1]) / 2).toShort()
                                    }
                                }
                                return AudioClip(AudioClip.AudioFormat.Mono, 48000, frameCount.toInt(), frames)
                            }
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
