package com.denis535.game_engine_pro.input

import com.denis535.game_engine_pro.*
import com.denis535.sdl.*
import kotlinx.cinterop.*

public class Keyboard : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    public var OnKeyPress: ((KeyboardKeyActionEvent) -> Unit)? = null
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
    public var OnKeyRepeat: ((KeyboardKeyActionEvent) -> Unit)? = null
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
    public var OnKeyRelease: ((KeyboardKeyActionEvent) -> Unit)? = null
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
    public fun IsKeyPressed(key: KeyboardKey): Boolean {
        check(!this.IsClosed)
        val state = SDL_GetKeyboardState(null).also { SDL.ThrowErrorIfNeeded() }!!
        return state[key.ToNativeValue().toInt()].value
    }

}
