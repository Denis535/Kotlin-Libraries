package com.denis535.game_engine_pro.input

import com.denis535.game_engine_pro.*
import com.denis535.sdl.*
import kotlinx.cinterop.*

public class Keyboard : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

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
