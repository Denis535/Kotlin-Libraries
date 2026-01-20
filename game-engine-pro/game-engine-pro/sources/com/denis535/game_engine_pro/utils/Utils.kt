package com.denis535.game_engine_pro.utils

import com.denis535.sdl.*
import kotlinx.cinterop.*

public object Utils {

    @OptIn(ExperimentalForeignApi::class)
    public fun Sleep(time: UInt) {
        SDL_Delay(time).also { SDL.ThrowErrorIfNeeded() }
    }

}
