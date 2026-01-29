package com.denis535.game_engine_pro.display

import cnames.structs.*
import com.denis535.sdl.*
import kotlinx.cinterop.*

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

    @OptIn(ExperimentalForeignApi::class)
    internal fun ToNativeValue(): SDL_SystemCursor {
        return when (this) {
            Arrow -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_DEFAULT
            Text -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_TEXT
            Pointer -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_POINTER
            Crosshair -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_CROSSHAIR
            Progress -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_PROGRESS
            Wait -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_WAIT
            NotAllowed -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_NOT_ALLOWED

            Move -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_MOVE

            SingleArrowResize_N -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_N_RESIZE
            SingleArrowResize_S -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_S_RESIZE
            SingleArrowResize_W -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_W_RESIZE
            SingleArrowResize_E -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_E_RESIZE

            SingleArrowResize_N_W -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_NW_RESIZE
            SingleArrowResize_N_E -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_NE_RESIZE
            SingleArrowResize_S_W -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_SW_RESIZE
            SingleArrowResize_S_E -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_SE_RESIZE

            DoubleArrowResize_N_S -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_NS_RESIZE
            DoubleArrowResize_W_E -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_EW_RESIZE

            DoubleArrowResize_NW_SE -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_NWSE_RESIZE
            DoubleArrowResize_NE_SW -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_NESW_RESIZE
        }
    }

    public companion object {
        @OptIn(ExperimentalForeignApi::class)
        internal fun FromNativeValue(value: SDL_SystemCursor): CursorStyle? {
            return when (value) {
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_DEFAULT -> Arrow
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_TEXT -> Text
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_POINTER -> Pointer
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_CROSSHAIR -> Crosshair
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_PROGRESS -> Progress
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_WAIT -> Wait
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_NOT_ALLOWED -> NotAllowed

                SDL_SystemCursor.SDL_SYSTEM_CURSOR_MOVE -> Move

                SDL_SystemCursor.SDL_SYSTEM_CURSOR_N_RESIZE -> SingleArrowResize_N
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_S_RESIZE -> SingleArrowResize_S
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_W_RESIZE -> SingleArrowResize_W
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_E_RESIZE -> SingleArrowResize_E

                SDL_SystemCursor.SDL_SYSTEM_CURSOR_NW_RESIZE -> SingleArrowResize_N_W
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_NE_RESIZE -> SingleArrowResize_N_E
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_SW_RESIZE -> SingleArrowResize_S_W
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_SE_RESIZE -> SingleArrowResize_S_E

                SDL_SystemCursor.SDL_SYSTEM_CURSOR_NS_RESIZE -> DoubleArrowResize_N_S
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_EW_RESIZE -> DoubleArrowResize_W_E

                SDL_SystemCursor.SDL_SYSTEM_CURSOR_NWSE_RESIZE -> DoubleArrowResize_NW_SE
                SDL_SystemCursor.SDL_SYSTEM_CURSOR_NESW_RESIZE -> DoubleArrowResize_NE_SW

                else -> null
            }
        }
    }
}
