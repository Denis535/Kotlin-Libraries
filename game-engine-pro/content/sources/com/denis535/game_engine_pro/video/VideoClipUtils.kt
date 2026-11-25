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
                            var videoClip: VideoClip? = null
                            var audioClip: AudioClip? = null
                            val count = this.alloc<UIntVar>()
                            for (i in 0u until count.value) {
                                val codec = nestegg_track_codec_id(nestegg.value, i)
                                if (codec >= 0) {
                                    when (nestegg_track_type(nestegg.value, i)) {
                                        NESTEGG_TRACK_VIDEO -> {
                                            if (videoClip == null) {
                                                videoClip = this@VideoClipUtils.ReadVideoClip(nestegg.value!!, i, isAlpha)
                                            }
                                        }
                                        NESTEGG_TRACK_AUDIO -> {
                                            if (audioClip == null) {
                                                audioClip = this@VideoClipUtils.ReadAudioClip(nestegg.value!!, i, isAlpha)
                                            }
                                        }
                                    }
                                }
                            }
                            return Pair(videoClip, audioClip)
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
    private fun ReadVideoClip(nestegg: CValuesRef<cnames.structs.nestegg>, track: UInt, isAlpha: Boolean): VideoClip? {
        memScoped {
            val videoInfo = this.alloc<nestegg_video_params>()
            if (nestegg_track_video_params(nestegg, track, videoInfo.ptr) == 0) {
                // videos += VideoTrackData(i, codec, p.width, p.height, ReadCodecData(ctx, i))
            }
        }
        return null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun ReadAudioClip(nestegg: CValuesRef<cnames.structs.nestegg>, track: UInt, isStereo: Boolean): AudioClip? {
        memScoped {
            val audioInfo = this.alloc<nestegg_audio_params>()
            if (nestegg_track_audio_params(nestegg, track, audioInfo.ptr) == 0) {
                // audios += AudioTrackData(i, codec, p.rate, p.channels, ReadCodecData(ctx, i))
            }
        }
        return null
    }

}

@OptIn(ExperimentalForeignApi::class)
private class DataReader(val ptr: CPointer<UByteVar>, val size: Long) {
    public var pos: Long = 0L
}

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
