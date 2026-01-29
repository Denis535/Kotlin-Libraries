package com.denis535.game_engine_pro.utils

import com.denis535.sdl.*
import kotlinx.cinterop.*

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
    public fun Delay(durationMs: UInt) {
        SDL_Delay(durationMs).SDL_CheckError()
    }

}
