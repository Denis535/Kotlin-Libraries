package com.denis535.game_engine_pro.input

import com.denis535.game_engine_pro.*
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
