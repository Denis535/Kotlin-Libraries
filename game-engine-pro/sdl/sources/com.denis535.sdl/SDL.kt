package com.denis535.sdl

import kotlinx.cinterop.*

@OptIn(ExperimentalForeignApi::class)
public inline fun <T> T.SDL_CheckError(): T {
    val error = SDL_GetError()
    if (error != null && error[0] != 0.toByte()) {
        val error = error.toKString()
        SDL_ClearError()
        error(error)
    }
    return this
}
