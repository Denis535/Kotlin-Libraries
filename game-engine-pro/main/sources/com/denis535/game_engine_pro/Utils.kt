package com.denis535.game_engine_pro

import com.denis535.internal.sdl.SDL_CheckError
import com.denis535.internal.sdl.SDL_Delay
import com.denis535.internal.sdl.SDL_GetBasePath
import com.denis535.internal.sdl.SDL_IsMainThread
import com.denis535.internal.sdl.SDL_MESSAGEBOX_ERROR
import com.denis535.internal.sdl.SDL_MESSAGEBOX_INFORMATION
import com.denis535.internal.sdl.SDL_MESSAGEBOX_WARNING
import com.denis535.internal.sdl.SDL_ShowSimpleMessageBox
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.toKString

public object Utils {

    @OptIn(ExperimentalForeignApi::class)
    public val IsMainThread: Boolean
        get() = SDL_IsMainThread().SDL_CheckError()

    @OptIn(ExperimentalForeignApi::class)
    public val BasePath: String?
        get() = SDL_GetBasePath().SDL_CheckError()?.toKString()

    @OptIn(ExperimentalForeignApi::class)
    public fun ShowInfoMessage(title: String?, message: String?) {
        SDL_ShowSimpleMessageBox(SDL_MESSAGEBOX_INFORMATION, title, message, null).SDL_CheckError()
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun ShowWarningMessage(title: String?, message: String?) {
        SDL_ShowSimpleMessageBox(SDL_MESSAGEBOX_WARNING, title, message, null).SDL_CheckError()
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun ShowErrorMessage(title: String?, message: String?) {
        SDL_ShowSimpleMessageBox(SDL_MESSAGEBOX_ERROR, title, message, null).SDL_CheckError()
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun CheckMainThread() {
        check(SDL_IsMainThread().SDL_CheckError())
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Delay(durationMs: UInt) {
        SDL_Delay(durationMs).SDL_CheckError()
    }

}
