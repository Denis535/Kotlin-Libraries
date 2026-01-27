package com.denis535.game_engine_pro.input

import com.denis535.sdl.*
import kotlinx.cinterop.*

public class Mouse : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    public var OnMove: ((MouseMoveEvent) -> Unit)? = null
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
    public var OnButtonAction: ((MouseButtonActionEvent) -> Unit)? = null
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

    internal constructor()

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
            SDL_GetMouseState(x.ptr, y.ptr).SDL_CheckError()
            return Pair(x.value, y.value)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun GetDelta(): Pair<Float, Float> { // locked cursor only
        check(!this.IsClosed)
        memScoped {
            val deltaX = this.alloc<FloatVar>()
            val deltaY = this.alloc<FloatVar>()
            SDL_GetRelativeMouseState(deltaX.ptr, deltaY.ptr).SDL_CheckError()
            return Pair(deltaX.value, deltaY.value)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun IsButtonPressed(button: MouseButton): Boolean {
        check(!this.IsClosed)
        val state = SDL_GetMouseState(null, null).SDL_CheckError()
        return state and button.ToNativeMask() != 0u
    }

}

public class MouseMoveEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val Cursor: Pair<Float, Float>, // unlocked cursor only
    public val Delta: Pair<Float, Float>, // locked cursor only
)

public class MouseButtonActionEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val Button: MouseButton,
    public val IsPressed: Boolean,
    public val ClickCount: Int,
)

public class MouseWheelScrollEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val Scroll: Pair<Float, Float>,
    public val IntegerScroll: Pair<Int, Int>,
)

public enum class MouseButton {
    Left,
    Right,
    Middle,
    X1,
    X2;

//    @OptIn(ExperimentalForeignApi::class)
//    internal fun ToNativeValue(): Int {
//        return when (this) {
//            Left -> SDL_BUTTON_LEFT
//            Right -> SDL_BUTTON_RIGHT
//            Middle -> SDL_BUTTON_MIDDLE
//            X1 -> SDL_BUTTON_X1
//            X2 -> SDL_BUTTON_X2
//        }
//    }

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
        internal fun FromNativeValue(value: Int): MouseButton? {
            return when (value) {
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
