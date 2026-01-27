package com.denis535.game_engine_pro.input

import com.denis535.sdl.*
import kotlinx.cinterop.*

public class Touchscreen : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    internal val NativeDeviceID: ULong?

    public var OnTouch: ((TouchEvent) -> Unit)? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        set(value) {
            check(!this.IsClosed)
            if (field != null) {
                require(value == null)
            } else {
                require(value != null)
            }
            field = value
        }

    @OptIn(ExperimentalForeignApi::class)
    internal constructor() {
        memScoped {
            val devicesCount = this.alloc<IntVar>()
            val devices = SDL_GetTouchDevices(devicesCount.ptr).SDL_CheckError()
            this@Touchscreen.NativeDeviceID = (0 until devicesCount.value).asSequence().map { devices!![it] }.firstOrNull { SDL_GetTouchDeviceType(it).SDL_CheckError() == SDL_TOUCH_DEVICE_DIRECT }
            SDL_free(devices).SDL_CheckError()
        }
    }

    public override fun close() {
        check(!this.IsClosed)
        this.IsClosed = true
    }

}

public class TouchEvent(
    internal val NativeDeviceID: ULong,
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val ID: ULong,
    public val State: TouchState,
    public val Point: Pair<Float, Float>, // [0..1]
    public val Delta: Pair<Float, Float>, // [-1..1]
    public val Pressure: Float, // [0..1]
)

public enum class TouchState {
    Begin,
    Changed,
    End,
    Canceled;
}
