package com.denis535.game_engine_pro.io

import cnames.structs.*
import com.denis535.game_engine_pro.video.*
import com.denis535.game_engine_pro.audio.*
import kotlinx.cinterop.*
import nestegg.*
import platform.posix.*

public object WebmDecoder {

    // webm (av1 + opus)
    @OptIn(ExperimentalForeignApi::class)
    public fun DecodeVideoAndAudioClip(data: ByteArray, isAlpha: Boolean = false, isStereo: Boolean = false): Pair<VideoClip?, AudioClip?>? {
        data.usePinned { data ->
            val dataPtr = data.addressOf(0).reinterpret<UByteVar>()
            val dataSize = data.get().size
            val reader = StableRef.create(DataReader(dataPtr, dataSize.toLong()))
            try {
                memScoped {
                    val nestegg = this.alloc<CPointerVar<nestegg>>()
                    val io = cValue<nestegg_io> {
                        this.read = staticCFunction(::ReadCallback)
                        this.seek = staticCFunction(::SeekCallback)
                        this.tell = staticCFunction(::TellCallback)
                        this.userdata = reader.asCPointer()
                    }
                    if (nestegg_init(nestegg.ptr, io, null, -1) == 0) {
                        try {
                            return this@WebmDecoder.ReadVideoAndAudioClip(nestegg.value!!, isAlpha, isStereo)
                        } finally {
                            nestegg_destroy(nestegg.value)
                        }
                    }
                }
            } finally {
                reader.dispose()
            }
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ReadVideoAndAudioClip(nestegg: CPointer<nestegg>, isAlpha: Boolean, isStereo: Boolean): Pair<VideoClip?, AudioClip?>? {
        val videoTrackData = this.FindVideoTrackData(nestegg)
        val audioTrackData = this.FindAudioTrackData(nestegg)
        if (this.ReadPackets(nestegg, videoTrackData, audioTrackData)) {
            // todo
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun FindVideoTrackData(nestegg: CPointer<nestegg>): VideoTrackData? {
        memScoped {
            val count = this.alloc<UIntVar>()
            if (nestegg_track_count(nestegg, count.ptr) != 0) return null
            for (i in 0u until count.value) {
                val type = nestegg_track_type(nestegg, i)
                val codec = nestegg_track_codec_id(nestegg, i)
                if (type == NESTEGG_TRACK_VIDEO) {
                    if (codec == NESTEGG_CODEC_AV1) {
                        return this@WebmDecoder.ReadVideoTrackData(nestegg, i)
                    }
                }
            }
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun FindAudioTrackData(nestegg: CPointer<nestegg>): AudioTrackData? {
        memScoped {
            val count = this.alloc<UIntVar>()
            if (nestegg_track_count(nestegg, count.ptr) != 0) return null
            for (i in 0u until count.value) {
                val type = nestegg_track_type(nestegg, i)
                val codec = nestegg_track_codec_id(nestegg, i)
                if (type == NESTEGG_TRACK_AUDIO) {
                    if (codec == NESTEGG_CODEC_OPUS) {
                        return this@WebmDecoder.ReadAudioTrackData(nestegg, i)
                    }
                }
            }
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ReadVideoTrackData(nestegg: CPointer<nestegg>, track: UInt): VideoTrackData? {
        memScoped {
            val info = this.alloc<nestegg_video_params>()
            if (nestegg_track_video_params(nestegg, track, info.ptr) != 0) return null
            val width = info.width
            val height = info.height
            val isAlpha = info.alpha_mode == 1u
            val codecData = this@WebmDecoder.ReadCodecData(nestegg, track)
            if (codecData != null) return VideoTrackData(track, width, height, codecData)
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ReadAudioTrackData(nestegg: CPointer<nestegg>, track: UInt): AudioTrackData? {
        memScoped {
            val info = this.alloc<nestegg_audio_params>()
            if (nestegg_track_audio_params(nestegg, track, info.ptr) != 0) return null
            val channelCount = info.channels
            val fps = info.rate
            val codecDelay = info.codec_delay
            val seekPreroll = info.seek_preroll
            val codecData = this@WebmDecoder.ReadCodecData(nestegg, track)
            if (codecData != null) return AudioTrackData(track, channelCount, fps, codecData)
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ReadCodecData(nestegg: CPointer<nestegg>, track: UInt): List<ByteArray>? {
        val result = mutableListOf<ByteArray>()
        memScoped {
            val count = this.alloc<UIntVar>()
            if (nestegg_track_codec_data_count(nestegg, track, count.ptr) != 0) return null
            val ptr = this.alloc<CPointerVar<UByteVar>>()
            val size = this.alloc<ULongVar>()
            for (i in 0u until count.value) {
                if (nestegg_track_codec_data(nestegg, track, i, ptr.ptr, size.ptr) != 0) return null
                result += ptr.value!!.readBytes(size.value.toInt())
            }
        }
        return result
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ReadPackets(nestegg: CPointer<nestegg>, videoTrackData: VideoTrackData?, audioTrackData: AudioTrackData?): Boolean {
        while (true) {
            memScoped {
                val packet = this.alloc<CPointerVar<nestegg_packet>>()
                val status = nestegg_read_packet(nestegg, packet.ptr)
                if (status > 0) {
                    try {
                        val track = this.alloc<UIntVar>()
                        if (nestegg_packet_track(packet.value, track.ptr) != 0) return false
                        if (track.value == videoTrackData?.Track) {
                            val timestamp = this.alloc<ULongVar>()
                            val duration = this.alloc<ULongVar>()
                            val count = this.alloc<UIntVar>()
                            val ptr = this.alloc<CPointerVar<UByteVar>>()
                            val size = this.alloc<ULongVar>()
                            if (nestegg_packet_tstamp(packet.value, timestamp.ptr) != 0) timestamp.value = 0UL
                            if (nestegg_packet_duration(packet.value, duration.ptr) != 0) duration.value = 0UL
                            if (nestegg_packet_count(packet.value, count.ptr) != 0) return false
                            val data = mutableListOf<ByteArray>()
                            var additionalData: ByteArray? = null
                            for (i in 0u until count.value) {
                                if (nestegg_packet_data(packet.value, i, ptr.ptr, size.ptr) != 0) return false
                                data += ptr.value!!.readBytes(size.value.toInt())
                            }
                            if (nestegg_packet_additional_data(packet.value, 1u, ptr.ptr, size.ptr) == 0) {
                                additionalData = ptr.value!!.readBytes(size.value.toInt())
                            }
                            videoTrackData.Packets += Packet(track.value, timestamp.value, duration.value, data, additionalData)
                        }
                        if (track.value == audioTrackData?.Track) {
                            val timestamp = this.alloc<ULongVar>()
                            val duration = this.alloc<ULongVar>()
                            val count = this.alloc<UIntVar>()
                            val ptr = this.alloc<CPointerVar<UByteVar>>()
                            val size = this.alloc<ULongVar>()
                            if (nestegg_packet_tstamp(packet.value, timestamp.ptr) != 0) timestamp.value = 0UL
                            if (nestegg_packet_duration(packet.value, duration.ptr) != 0) duration.value = 0UL
                            if (nestegg_packet_count(packet.value, count.ptr) != 0) return false
                            val data = mutableListOf<ByteArray>()
                            var additionalData: ByteArray? = null
                            for (i in 0u until count.value) {
                                if (nestegg_packet_data(packet.value, i, ptr.ptr, size.ptr) != 0) return false
                                data += ptr.value!!.readBytes(size.value.toInt())
                            }
                            if (nestegg_packet_additional_data(packet.value, 1u, ptr.ptr, size.ptr) == 0) {
                                additionalData = ptr.value!!.readBytes(size.value.toInt())
                            }
                            audioTrackData.Packets += Packet(track.value, timestamp.value, duration.value, data, additionalData)
                        }
                    } finally {
                        nestegg_free_packet(packet.value)
                    }
                }
                if (status == 0) {
                    break
                }
                if (status < 0) {
                    return false
                }
            }
        }
        return true
    }

}

@OptIn(ExperimentalForeignApi::class)
private class DataReader(val ptr: CPointer<UByteVar>, val size: Long) {
    public var pos: Long = 0L
}

private class VideoTrackData(val Track: UInt, val Width: UInt, val Height: UInt, val CodecData: List<ByteArray>) {
    public val Packets: MutableList<Packet> = mutableListOf()
}

private class AudioTrackData(val Track: UInt, val ChannelCount: UInt, val Fps: Double, val CodecData: List<ByteArray>) {
    public val Packets: MutableList<Packet> = mutableListOf()
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
