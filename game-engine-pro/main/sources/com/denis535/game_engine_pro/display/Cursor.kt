package com.denis535.game_engine_pro.display

import cnames.structs.*
import com.denis535.game_engine_pro.*
import kotlinx.cinterop.*
import sdl.*

public class Cursor : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    @OptIn(ExperimentalForeignApi::class)
    private var NativeCustomCursor: CPointer<SDL_Cursor>? = null
        set(value) {
            check(!this.IsClosed)
            if (field != null) {
                SDL_SetCursor(SDL_GetDefaultCursor().SDL_CheckError()).SDL_CheckError()
                SDL_DestroyCursor(field).SDL_CheckError()
            }
            field = value
            if (field != null) {
                SDL_SetCursor(field).SDL_CheckError()
            }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsVisible: Boolean
        get() {
            check(!this.IsClosed)
            return SDL_CursorVisible().SDL_CheckError()
        }
        set(value) {
            check(!this.IsClosed)
            if (value) {
                SDL_ShowCursor().SDL_CheckError()
            } else {
                SDL_HideCursor().SDL_CheckError()
            }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var Style: CursorStyle?
        get() {
            check(!this.IsClosed)
            error("Not implemented")
        }
        set(value) {
            check(!this.IsClosed)
            if (value != null) {
                this.NativeCustomCursor = SDL_CreateSystemCursor(value.ToNativeValue()).SDL_CheckError()
            } else {
                this.NativeCustomCursor = null
            }
        }

    internal constructor()

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        this.NativeCustomCursor = null
        this.IsClosed = true
    }

}

public enum class CursorStyle {
    Arrow,
    Text,
    Pointer,
    Crosshair,
    Progress,
    Wait,
    NotAllowed,

    Move,

    SingleArrowResize_N,
    SingleArrowResize_S,
    SingleArrowResize_W,
    SingleArrowResize_E,

    SingleArrowResize_N_W,
    SingleArrowResize_N_E,
    SingleArrowResize_S_W,
    SingleArrowResize_S_E,

    DoubleArrowResize_N_S,
    DoubleArrowResize_W_E,

    DoubleArrowResize_NW_SE,
    DoubleArrowResize_NE_SW;

    public companion object
}
