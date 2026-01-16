package com.denis535.game_engine_pro.input

import kotlinx.cinterop.*
import com.denis535.sdl.*

public class Mouse : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    public var OnMotion: ((MouseMotionEvent) -> Unit)? = null
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
    public var OnButtonPress: ((MouseButtonActionEvent) -> Unit)? = null
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
    public var OnButtonRelease: ((MouseButtonActionEvent) -> Unit)? = null
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
    public var OnWheelScroll: ((MouseWheelScrollEvent) -> Unit)? = null
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

    internal constructor() {
    }

    public override fun close() {
        check(!this.IsClosed)
        this.IsClosed = true
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun GetCursor(): Pair<Float, Float> { // unlocked cursor only
        check(!this.IsClosed)
        memScoped {
            val x = this.alloc<FloatVar>()
            val y = this.alloc<FloatVar>()
            SDL_GetMouseState(x.ptr, y.ptr).also { SDL.ThrowErrorIfNeeded() }
            return Pair(x.value, y.value)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun GetDelta(): Pair<Float, Float> { // locked cursor only
        check(!this.IsClosed)
        memScoped {
            val deltaX = this.alloc<FloatVar>()
            val deltaY = this.alloc<FloatVar>()
            SDL_GetRelativeMouseState(deltaX.ptr, deltaY.ptr).also { SDL.ThrowErrorIfNeeded() }
            return Pair(deltaX.value, deltaY.value)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun IsButtonPressed(button: MouseButton): Boolean {
        check(!this.IsClosed)
        val state = SDL_GetMouseState(null, null).also { SDL.ThrowErrorIfNeeded() }
        return state and button.ToNativeMask() != 0u
    }

}

public class MouseMotionEvent(
    public val Cursor: Pair<Float, Float>, // unlocked cursor only
    public val Delta: Pair<Float, Float>, // locked cursor only
)

public class MouseButtonActionEvent(
    public val Cursor: Pair<Float, Float>, // unlocked cursor only
    public val Button: MouseButton,
    public val Clicks: Int,
)

public class MouseWheelScrollEvent(
    public val Cursor: Pair<Float, Float>, // unlocked cursor only
    public val ScrollX: Float,
    public val ScrollY: Float,
    public val IntegerScrollX: Int,
    public val IntegerScrollY: Int,
)

public enum class MouseButton {
    Left,
    Right,
    Middle,
    X1,
    X2;

    @OptIn(ExperimentalForeignApi::class)
    internal fun ToNativeValue(): Int {
        return when (this) {
            Left -> SDL_BUTTON_LEFT
            Right -> SDL_BUTTON_RIGHT
            Middle -> SDL_BUTTON_MIDDLE
            X1 -> SDL_BUTTON_X1
            X2 -> SDL_BUTTON_X2
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    internal fun ToNativeMask(): UInt {
        return when (this) {
            Left -> SDL_BUTTON_LMASK
            Right -> SDL_BUTTON_RMASK
            Middle -> SDL_BUTTON_MMASK
            X1 -> SDL_BUTTON_X1MASK
            X2 -> SDL_BUTTON_X2MASK
        }
    }

    public companion object {
        @OptIn(ExperimentalForeignApi::class)
        internal fun FromNativeValue(value: UByte): MouseButton? {
            return when (value.toInt()) {
                SDL_BUTTON_LEFT -> Left
                SDL_BUTTON_RIGHT -> Right
                SDL_BUTTON_MIDDLE -> Middle
                SDL_BUTTON_X1 -> X1
                SDL_BUTTON_X2 -> X2
                else -> null
            }
        }
    }
}
