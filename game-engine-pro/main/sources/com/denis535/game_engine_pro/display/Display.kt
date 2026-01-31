package com.denis535.game_engine_pro.display

import com.denis535.internal.sdl.*
import kotlinx.cinterop.*

public data class Display(public val ID: UInt) {

    @OptIn(ExperimentalForeignApi::class)
    public val Name: String?
        get() = SDL_GetDisplayName(this.ID).SDL_CheckError()?.toKString()

    @OptIn(ExperimentalForeignApi::class)
    public val ContentScale: Float
        get() = SDL_GetDisplayContentScale(this.ID).SDL_CheckError()

    @OptIn(ExperimentalForeignApi::class)
    public val Mode: DisplayMode
        get() {
            val mode = SDL_GetCurrentDisplayMode(this@Display.ID).SDL_CheckError()
            return DisplayMode(
                mode!!.pointed.w,
                mode.pointed.h,
                mode.pointed.pixel_density,
                mode.pointed.refresh_rate,
                Pair(mode.pointed.refresh_rate_numerator, mode.pointed.refresh_rate_denominator),
            )
        }

    @OptIn(ExperimentalForeignApi::class)
    public val AvailableModes: List<DisplayMode>
        get() {
            memScoped {
                val count = this.alloc<IntVar>()
                val modes = SDL_GetFullscreenDisplayModes(this@Display.ID, count.ptr).SDL_CheckError()
                return List(count.value) { i ->
                    val mode = modes!![i]
                    DisplayMode(
                        mode!!.pointed.w,
                        mode.pointed.h,
                        mode.pointed.pixel_density,
                        mode.pointed.refresh_rate,
                        Pair(mode.pointed.refresh_rate_numerator, mode.pointed.refresh_rate_denominator),
                    )
                }
            }
        }
}

public data class DisplayMode(
    public val Width: Int,
    public val Height: Int,
    public val Density: Float,
    public val RefreshRate: Float,
    public val RefreshRateRatio: Pair<Int, Int>,
)
