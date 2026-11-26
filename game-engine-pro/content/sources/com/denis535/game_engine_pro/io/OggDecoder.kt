package com.denis535.game_engine_pro.io

import com.denis535.game_engine_pro.audio.*
import kotlinx.cinterop.*
import opusfile.*

public object OggDecoder {

    // ogg (opus)
    @OptIn(ExperimentalForeignApi::class)
    public fun DecodeAudioClip(data: ByteArray, isStereo: Boolean = false): AudioClip? {
        data.usePinned { data ->
            val dataPtr = data.addressOf(0).reinterpret<UByteVar>()
            val dataSize = data.get().size
            memScoped {
                val error = this.alloc<IntVar>()
                val opus = op_open_memory(dataPtr, dataSize.toULong(), error.ptr)
                try {
                    if (opus != null) {
                        return this@OggDecoder.ReadAudioClip(opus, isStereo)
                    }
                } finally {
                    op_free(opus)
                }
            }
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ReadAudioClip(opus: CPointer<cnames.structs.OggOpusFile>, isStereo: Boolean): AudioClip? {
        val channelCount = op_channel_count(opus, -1) // mono, stereo, etc
        val frameCount = op_pcm_total(opus, -1)
        if (channelCount > 0 && frameCount > 0) {
            if (isStereo) {
                val frames = ShortArray(2 * frameCount.toInt())
                memScoped {
                    val buffer = this.allocArray<ShortVar>(2 * 5760)
                    var index = 0
                    while (true) {
                        val readFrameCount = op_read_stereo(opus, buffer, 2 * 5760)
                        if (readFrameCount == 0) break
                        if (readFrameCount < 0) return null
                        for (i in 0 until readFrameCount) {
                            frames[index++] = buffer[i * 2]
                            frames[index++] = buffer[i * 2 + 1]
                        }
                    }
                }
                return AudioClip(AudioFormat.Stereo, 48000, frameCount.toInt(), frames)
            } else {
                val frames = ShortArray(frameCount.toInt())
                memScoped {
                    val buffer = this.allocArray<ShortVar>(2 * 5760)
                    var index = 0
                    while (true) {
                        val readFrameCount = op_read_stereo(opus, buffer, 2 * 5760)
                        if (readFrameCount == 0) break
                        if (readFrameCount < 0) return null
                        for (i in 0 until readFrameCount) {
                            frames[index++] = ((buffer[i * 2] + buffer[i * 2 + 1]) / 2).toShort()
                        }
                    }
                }
                return AudioClip(AudioFormat.Mono, 48000, frameCount.toInt(), frames)
            }
        }
        return null
    }

}
