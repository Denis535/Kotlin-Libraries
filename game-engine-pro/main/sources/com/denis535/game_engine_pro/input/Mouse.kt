package com.denis535.game_engine_pro.input

import com.denis535.game_engine_pro.*
import com.denis535.internal.sdl.*
import kotlinx.cinterop.*

public class Mouse : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    public var OnMoveCallback: ((MouseMoveEvent) -> Unit)? = null
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
    public var OnButtonActionCallback: ((MouseButtonActionEvent) -> Unit)? = null
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
    public var OnWheelScrollCallback: ((MouseWheelScrollEvent) -> Unit)? = null
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

    public constructor()

    public override fun close() {
        check(!this.IsClosed)
        this.IsClosed = true
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun GetCursor(): Vector2 { // unlocked cursor only
        check(!this.IsClosed)
        memScoped {
            val x = this.alloc<FloatVar>()
            val y = this.alloc<FloatVar>()
            SDL_GetMouseState(x.ptr, y.ptr).SDL_CheckError()
            return Vector2(x.value, y.value)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun GetDelta(): Vector2 { // locked cursor only
        check(!this.IsClosed)
        memScoped {
            val deltaX = this.alloc<FloatVar>()
            val deltaY = this.alloc<FloatVar>()
            SDL_GetRelativeMouseState(deltaX.ptr, deltaY.ptr).SDL_CheckError()
            return Vector2(deltaX.value, deltaY.value)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun IsButtonPressed(button: MouseButton): Boolean {
        check(!this.IsClosed)
        val state = SDL_GetMouseState(null, null).SDL_CheckError()
        return state and button.ToNativeMask() != 0u
    }

}

public data class MouseMoveEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    internal val NativeDeviceID: UInt,
    public val Cursor: Vector2, // unlocked cursor only
    public val Delta: Vector2, // locked cursor only
)

public data class MouseButtonActionEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    internal val NativeDeviceID: UInt,
    public val Button: MouseButton,
    public val IsPressed: Boolean,
    public val ClickCount: Int,
)

public data class MouseWheelScrollEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    internal val NativeDeviceID: UInt,
    public val Scroll: Vector2,
    public val IntegerScroll: Vector2I,
)

public enum class MouseButton {
    Left,
    Right,
    Middle,
    X1,
    X2;
}
