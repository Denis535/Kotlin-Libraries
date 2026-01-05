package com.denis535.game_engine_pro.input

import com.denis535.sdl.*
import kotlinx.cinterop.*

public class Mouse : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    public var OnCursorMove: ((MouseCursorMoveEvent) -> Unit)? = null
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

    internal fun OnCursorMove(event: MouseCursorMoveEvent) {
        check(!this.IsClosed)
        this.OnCursorMove?.invoke(event)
    }

    internal fun OnButtonPress(event: MouseButtonActionEvent) {
        check(!this.IsClosed)
        this.OnButtonPress?.invoke(event)
    }

    internal fun OnButtonRelease(event: MouseButtonActionEvent) {
        check(!this.IsClosed)
        this.OnButtonRelease?.invoke(event)
    }

    internal fun OnWheelScroll(event: MouseWheelScrollEvent) {
        check(!this.IsClosed)
        this.OnWheelScroll?.invoke(event)
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun GetCursorPosition(): Pair<Float, Float> {
        check(!this.IsClosed)
        memScoped {
            val cursorX = this.alloc<FloatVar>()
            val cursorY = this.alloc<FloatVar>()
            SDL_GetMouseState(cursorX.ptr, cursorY.ptr).also { SDL.ThrowErrorIfNeeded() }
            return Pair(cursorX.value, cursorY.value)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun GetCursorDelta(): Pair<Float, Float> {
        check(!this.IsClosed)
        memScoped {
            val cursorDeltaX = this.alloc<FloatVar>()
            val cursorDeltaY = this.alloc<FloatVar>()
            SDL_GetRelativeMouseState(cursorDeltaX.ptr, cursorDeltaY.ptr).also { SDL.ThrowErrorIfNeeded() }
            return Pair(cursorDeltaX.value, cursorDeltaY.value)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun IsButtonPressed(button: MouseButton): Boolean {
        check(!this.IsClosed)
        val state = SDL_GetMouseState(null, null).also { SDL.ThrowErrorIfNeeded() }
        return state and button.ToNativeMask() != 0u
    }

}

public class MouseCursorMoveEvent(
    public val CursorX: Float,
    public val CursorY: Float,
    public val CursorDeltaX: Float,
    public val CursorDeltaY: Float,
)

public class MouseButtonActionEvent(
    public val CursorX: Float,
    public val CursorY: Float,
    public val Button: MouseButton,
    public val Clicks: Int,
)

public class MouseWheelScrollEvent(
    public val CursorX: Float,
    public val CursorY: Float,
    public val ScrollX: Float,
    public val ScrollY: Float,
    public val ScrollIntegerX: Int,
    public val ScrollIntegerY: Int,
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
