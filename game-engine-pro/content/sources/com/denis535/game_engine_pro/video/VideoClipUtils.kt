package com.denis535.game_engine_pro.video

import com.denis535.game_engine_pro.audio.*
import kotlinx.cinterop.*
import nestegg.*
import dav1d.*

public object VideoClipUtils {

    // webm (av1 + opus)
    @OptIn(ExperimentalForeignApi::class)
    public fun Decode(data: ByteArray, isAlpha: Boolean = false, isStereo: Boolean = false): Pair<VideoClip?, AudioClip?> {
        return Pair(null, null)
    }

}
