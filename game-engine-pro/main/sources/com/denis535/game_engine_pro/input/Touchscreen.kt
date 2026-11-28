package com.denis535.game_engine_pro.input

import com.denis535.game_engine_pro.*
import kotlinx.cinterop.*
import sdl.*

public class Touchscreen : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    internal val NativeDeviceID: ULong?

    @OptIn(ExperimentalForeignApi::class)
    public val Touches: List<Touch>
        get() {
            check(!this.IsClosed)
            memScoped {
                this@Touchscreen.NativeDeviceID?.let {
                    val count = this.alloc<IntVar>()
                    val touches = SDL_GetTouchFingers(it, count.ptr).SDL_CheckError()!!
                    val result = List(count.value) { i ->
                        val touch = touches[i]!!
                        Touch(touch.pointed.id, Vector2(touch.pointed.x, touch.pointed.y), touch.pointed.pressure)
                    }
                    SDL_free(touches)
                    return result
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

    public var OnZoomCallback: ((ZoomEvent) -> Unit)? = null
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
    public constructor() {
        memScoped {
            val count = this.alloc<IntVar>()
            val devices = SDL_GetTouchDevices(count.ptr).SDL_CheckError()!!
            this@Touchscreen.NativeDeviceID = (0 until count.value).asSequence()
                .map { devices[it] }
                .firstOrNull { SDL_GetTouchDeviceType(it) == SDL_TOUCH_DEVICE_DIRECT }
            SDL_free(devices)
        }
    }

    public override fun close() {
        check(!this.IsClosed)
        this.IsClosed = true
    }

}

public data class TouchEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    internal val NativeDeviceID: ULong,
    public val ID: ULong,
    public val State: TouchState,
    public val Point: Vector2, // [0..1]
    public val Delta: Vector2, // [-1..1]
    public val Pressure: Float, // [0..1]
)

public data class ZoomEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val State: ZoomState,
    public val Value: Float, // value <= 1 or value >= 1
)

public data class Touch(
    public val ID: ULong,
    public val Point: Vector2, // [0..1]
    public val Pressure: Float, // [0..1]
)

public enum class TouchState {
    Begin,
    Changed,
    End,
    Canceled;
}

public enum class ZoomState {
    Begin,
    Changed,
    End;
}
