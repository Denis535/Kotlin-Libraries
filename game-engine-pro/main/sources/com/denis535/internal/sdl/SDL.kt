package com.denis535.internal.sdl

import kotlinx.cinterop.*

public object SDL {

    @OptIn(ExperimentalForeignApi::class)
    public fun CheckError() {
        val error = SDL_GetError()
        if (error != null && error[0] != 0.toByte()) {
            val error = error.toKString()
            SDL_ClearError()
            error(error)
        }
    }

}

@OptIn(ExperimentalForeignApi::class)
public fun <T> T.SDL_CheckError(): T {
    val error = SDL_GetError()
    if (error != null && error[0] != 0.toByte()) {
        val error = error.toKString()
        SDL_ClearError()
        error(error)
    }
    return this
}
