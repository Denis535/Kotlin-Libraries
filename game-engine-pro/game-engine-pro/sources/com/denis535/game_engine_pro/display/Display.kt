package com.denis535.game_engine_pro.display

import com.denis535.sdl.*
import kotlinx.cinterop.*

public object Display {

    @OptIn(ExperimentalForeignApi::class)
    public val Id: UInt
        get() = SDL_GetPrimaryDisplay().also { SDL.ThrowErrorIfNeeded() }

    @OptIn(ExperimentalForeignApi::class)
    public val Name: String?
        get() = SDL_GetDisplayName(this.Id).also { SDL.ThrowErrorIfNeeded() }?.toKString()

    @OptIn(ExperimentalForeignApi::class)
    public val ContentScale: Float
        get() = SDL_GetDisplayContentScale(this.Id).also { SDL.ThrowErrorIfNeeded() }

    @OptIn(ExperimentalForeignApi::class)
    public val Width: Int
        get() {
            val mode = SDL_GetCurrentDisplayMode(this@Display.Id).also { SDL.ThrowErrorIfNeeded() }
            return mode!!.pointed.w
        }

    @OptIn(ExperimentalForeignApi::class)
    public val Height: Int
        get() {
            val mode = SDL_GetCurrentDisplayMode(this@Display.Id).also { SDL.ThrowErrorIfNeeded() }
            return mode!!.pointed.h
        }

    @OptIn(ExperimentalForeignApi::class)
    public val Density: Float
        get() {
            val mode = SDL_GetCurrentDisplayMode(this@Display.Id).also { SDL.ThrowErrorIfNeeded() }
            return mode!!.pointed.pixel_density
        }

    @OptIn(ExperimentalForeignApi::class)
    public val RefreshRate: Float
        get() {
            val mode = SDL_GetCurrentDisplayMode(this@Display.Id).also { SDL.ThrowErrorIfNeeded() }
            return mode!!.pointed.refresh_rate
        }

    @OptIn(ExperimentalForeignApi::class)
    public val RefreshRateRatio: Pair<Int, Int>
        get() {
            val mode = SDL_GetCurrentDisplayMode(this@Display.Id).also { SDL.ThrowErrorIfNeeded() }
            return Pair(mode!!.pointed.refresh_rate_numerator, mode.pointed.refresh_rate_denominator)
        }

}
