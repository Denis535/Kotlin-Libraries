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
                                val samples = ShortArray(2 * frameCount.toInt())
                                var index = 0
                                while (true) {
                                    val readFrameCount = op_read_stereo(oggOpus, buffer, 2 * 5760)
                                    if (readFrameCount == 0) break
                                    if (readFrameCount == OP_HOLE) return null
                                    if (readFrameCount < 0) return null
                                    for (i in 0 until 2 * readFrameCount) {
                                        samples[index++] = buffer[i]
                                    }
                                }
                                return AudioClip(AudioClip.AudioFormat.Stereo, 48000, frameCount.toInt(), samples)
                            } else {
                                val buffer = this.allocArray<ShortVar>(1 * 5760)
                                val samples = ShortArray(1 * frameCount.toInt())
                                var index = 0
                                while (true) {
                                    val readFrameCount = op_read(oggOpus, buffer, 1 * 5760, null) // todo
                                    if (readFrameCount == 0) break
                                    if (readFrameCount == OP_HOLE) return null
                                    if (readFrameCount < 0) return null
                                    for (i in 0 until 2 * readFrameCount) {
                                        samples[index++] = buffer[i]
                                    }
                                }
                                return AudioClip(AudioClip.AudioFormat.Mono, 48000, frameCount.toInt(), samples)
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
