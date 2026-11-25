package com.denis535.game_engine_pro.video

import com.denis535.game_engine_pro.audio.*
import dav1d.*
import kotlinx.cinterop.*
import kotlinx.cinterop.value
import nestegg.*
import platform.posix.*

public object VideoClipUtils {

    // webm (av1 + opus)
    @OptIn(ExperimentalForeignApi::class)
    public fun CreateVideoAndAudioClip(data: ByteArray, isAlpha: Boolean = false, isStereo: Boolean = false): Pair<VideoClip?, AudioClip?> {
        data.usePinned { data ->
            val dataPtr = data.addressOf(0).reinterpret<UByteVar>()
            val dataSize = data.get().size
            val reader = StableRef.create(DataReader(dataPtr, dataSize.toLong()))
            try {
                memScoped {
                    val nestegg = this.alloc<CPointerVar<cnames.structs.nestegg>>()
                    val io = cValue<nestegg_io> {
                        this.read = staticCFunction(::ReadCallback)
                        this.seek = staticCFunction(::SeekCallback)
                        this.tell = staticCFunction(::TellCallback)
                        this.userdata = reader.asCPointer()
                    }
                    if (nestegg_init(nestegg.ptr, io, null, -1) == 0) {
                        try {
                            return this@VideoClipUtils.ReadVideoAndAudioClip(nestegg.value!!, isAlpha, isStereo)
                        } finally {
                            nestegg_destroy(nestegg.value)
                        }
                    }
                }
            } finally {
                reader.dispose()
            }
        }
        return Pair(null, null)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ReadVideoAndAudioClip(nestegg: CPointer<cnames.structs.nestegg>, isAlpha: Boolean, isStereo: Boolean): Pair<VideoClip?, AudioClip?> {
        val packets = this@VideoClipUtils.ReadPackets(nestegg)
        if (packets != null) {
            var videoClip: VideoClip? = null
            var audioClip: AudioClip? = null
            memScoped {
                val count = this.alloc<UIntVar>()
                if (nestegg_track_count(nestegg, count.ptr) == 0) {
                    for (i in 0u until count.value) {
                        when (nestegg_track_type(nestegg, i)) {
                            NESTEGG_TRACK_VIDEO -> {
                                val codec = nestegg_track_codec_id(nestegg, i)
                                if (videoClip == null && codec == NESTEGG_CODEC_AV1) {
                                    videoClip = this@VideoClipUtils.ReadVideoClip(nestegg, i, packets, isAlpha)
                                }
                            }
                            NESTEGG_TRACK_AUDIO -> {
                                val codec = nestegg_track_codec_id(nestegg, i)
                                if (audioClip == null && codec == NESTEGG_CODEC_OPUS) {
                                    audioClip = this@VideoClipUtils.ReadAudioClip(nestegg, i, packets, isStereo)
                                }
                            }
                        }

                    }
                }
            }
            return Pair(videoClip, audioClip)
        }
        return Pair(null, null)
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ReadVideoClip(nestegg: CPointer<cnames.structs.nestegg>, track: UInt, packets: List<Packet>, isAlpha: Boolean): VideoClip? {
        memScoped {
            val videoInfo = this.alloc<nestegg_video_params>()
            if (nestegg_track_video_params(nestegg, track, videoInfo.ptr) == 0) {
                val width = videoInfo.width
                val height = videoInfo.height
                val codecData = this@VideoClipUtils.ReadCodecData(nestegg, track)
            }
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ReadAudioClip(nestegg: CPointer<cnames.structs.nestegg>, track: UInt, packets: List<Packet>, isStereo: Boolean): AudioClip? {
        memScoped {
            val audioInfo = this.alloc<nestegg_audio_params>()
            if (nestegg_track_audio_params(nestegg, track, audioInfo.ptr) == 0) {
                val channelCount = audioInfo.channels
                val fps = audioInfo.rate
                val codecData = this@VideoClipUtils.ReadCodecData(nestegg, track)
            }
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ReadCodecData(nestegg: CPointer<cnames.structs.nestegg>?, track: UInt): List<ByteArray>? {
        val result = mutableListOf<ByteArray>()
        memScoped {
            val count = this.alloc<UIntVar>()
            if (nestegg_track_codec_data_count(nestegg, track, count.ptr) != 0) return null
            for (i in 0u until count.value) {
                memScoped {
                    val ptr = this.alloc<CPointerVar<UByteVar>>()
                    val size = this.alloc<ULongVar>()
                    if (nestegg_track_codec_data(nestegg, track, i, ptr.ptr, size.ptr) != 0) return null
                    result += ptr.value!!.readBytes(size.value.toInt())
                }
            }
        }
        return result
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ReadPackets(nestegg: CPointer<cnames.structs.nestegg>): List<Packet>? {
        val result = mutableListOf<Packet>()
        while (true) {
            memScoped {
                val packet = this.alloc<CPointerVar<cnames.structs.nestegg_packet>>()
                run {
                    val status = nestegg_read_packet(nestegg, packet.ptr)
                    if (status == 0) break
                    if (status < 0) return null
                }
                try {
                    val track = this.alloc<UIntVar>()
                    val timestamp = this.alloc<ULongVar>()
                    val duration = this.alloc<ULongVar>()
                    val count = this.alloc<UIntVar>()
                    if (nestegg_packet_track(packet.value, track.ptr) != 0) return null
                    if (nestegg_packet_tstamp(packet.value, timestamp.ptr) != 0) timestamp.value = 0UL
                    if (nestegg_packet_duration(packet.value, duration.ptr) != 0) duration.value = 0UL
                    if (nestegg_packet_count(packet.value, count.ptr) != 0) return null
                    memScoped {
                        val data = mutableListOf<ByteArray>()
                        var additionalData: ByteArray? = null
                        val ptr = this.alloc<CPointerVar<UByteVar>>()
                        val size = this.alloc<ULongVar>()
                        for (i in 0u until count.value) {
                            if (nestegg_packet_data(packet.value, i, ptr.ptr, size.ptr) != 0) return null
                            data += ptr.value!!.readBytes(size.value.toInt())
                        }
                        if (nestegg_packet_additional_data(packet.value, 1u, ptr.ptr, size.ptr) == 0) {
                            additionalData = ptr.value!!.readBytes(size.value.toInt())
                        }
                        result += Packet(track.value, timestamp.value, duration.value, data, additionalData)
                    }
                } finally {
                    nestegg_free_packet(packet.value)
                }
            }
        }
        return result
    }

}

@OptIn(ExperimentalForeignApi::class)
private class DataReader(val ptr: CPointer<UByteVar>, val size: Long) {
    public var pos: Long = 0L
}

private class Packet(val Track: UInt, val Timestamp: ULong, val Duration: ULong, val Data: List<ByteArray>, val AdditionalData: ByteArray?)

@OptIn(ExperimentalForeignApi::class)
private fun ReadCallback(buffer: COpaquePointer?, length: ULong, userdata: COpaquePointer?): Long {
    val reader = userdata!!.asStableRef<DataReader>().get()
    if (reader.pos + length.toLong() <= reader.size) {
        memcpy(buffer, reader.ptr + reader.pos, length)
        reader.pos += length.toLong()
        return 1
    } else {
        reader.pos = reader.size
        return 0
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun SeekCallback(offset: Long, whence: Int, userdata: COpaquePointer?): Int {
    val reader = userdata!!.asStableRef<DataReader>().get()
    val newPos = when (whence) {
        NESTEGG_SEEK_SET -> {
            offset
        }
        NESTEGG_SEEK_CUR -> {
            reader.pos + offset
        }
        NESTEGG_SEEK_END -> {
            reader.size + offset
        }
        else -> {
            return -1
        }
    }
    if (newPos >= 0 && newPos <= reader.size) {
        reader.pos = newPos
        return 0
    }
    return -1
}

@OptIn(ExperimentalForeignApi::class)
private fun TellCallback(userdata: COpaquePointer?): Long {
    val reader = userdata!!.asStableRef<DataReader>().get()
    return reader.pos
}
