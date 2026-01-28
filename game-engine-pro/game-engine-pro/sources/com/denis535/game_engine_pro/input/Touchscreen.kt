package com.denis535.game_engine_pro.input

import com.denis535.game_engine_pro.utils.*
import com.denis535.sdl.*
import kotlinx.cinterop.*

public class Touchscreen : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    internal val NativeDeviceID: ULong?

    @OptIn(ExperimentalForeignApi::class)
    public val Touches: List<Touch>
        get() {
            check(!this.IsClosed)
            this.NativeDeviceID?.let {
                memScoped {
                    val count = this.alloc<IntVar>()
                    val touches = SDL_GetTouchFingers(it, count.ptr).SDL_CheckError()
                    try {
                        return List(count.value) { i ->
                            val touch = touches!![i]!!
                            Touch(touch.pointed.id, Point2(touch.pointed.x, touch.pointed.y), touch.pointed.pressure)
                        }
                    } finally {
                        SDL_free(touches)
                    }
                }
            }
            return listOf()
        }

    public var OnTouchCallback: ((TouchEvent) -> Unit)? = null
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
            val count = this.alloc<IntVar>()
            val devices = SDL_GetTouchDevices(count.ptr).SDL_CheckError()
            try {
                this@Touchscreen.NativeDeviceID = (0 until count.value).asSequence().map { devices!![it] }.firstOrNull { SDL_GetTouchDeviceType(it).SDL_CheckError() == SDL_TOUCH_DEVICE_DIRECT }
            } finally {
                SDL_free(devices).SDL_CheckError()
            }
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
    public val Point: Point2, // [0..1]
    public val Delta: Point2, // [-1..1]
    public val Pressure: Float, // [0..1]
)

public class Touch(
    public val ID: ULong,
    public val Point: Point2, // [0..1]
    public val Pressure: Float, // [0..1]
)

public enum class TouchState {
    Begin,
    Changed,
    End,
    Canceled;
}
