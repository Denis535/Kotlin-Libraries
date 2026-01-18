package com.denis535.game_engine_pro.windows

import cnames.structs.*
import com.denis535.sdl.*
import kotlinx.cinterop.*

public open class MainWindow : AutoCloseable {
    public sealed class Description(
        public val Title: String,
    ) {
        public class FullScreen(
            title: String,
            public val Width: Int = 1280,
            public val Height: Int = 720,
            public val IsResizable: Boolean = false,
        ) : Description(title)

        public class Window(
            title: String,
            public val Width: Int = 1280,
            public val Height: Int = 720,
            public val IsResizable: Boolean = false,
        ) : Description(title)
    }

    @OptIn(ExperimentalForeignApi::class)
    private var _Native: CPointer<SDL_Window>? = null

    @OptIn(ExperimentalForeignApi::class)
    public val IsClosed: Boolean
        get() {
            return this._Native == null
        }

    @OptIn(ExperimentalForeignApi::class)
    protected val Native: CPointer<SDL_Window>
        get() {
            check(!this.IsClosed)
            return this._Native!!
        }

    @OptIn(ExperimentalForeignApi::class)
    internal val NativeInternal: CPointer<SDL_Window>
        get() {
            check(!this.IsClosed)
            return this._Native!!
        }

    @OptIn(ExperimentalForeignApi::class)
    public val ID: UInt
        get() {
            check(!this.IsClosed)
            return SDL_GetWindowID(this._Native!!).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsFullScreen: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.Native).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_FULLSCREEN != 0UL
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowFullscreen(this.Native, value).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var Title: String
        get() {
            check(!this.IsClosed)
            val title = SDL_GetWindowTitle(this.Native).also { SDL.ThrowErrorIfNeeded() }
            return title!!.toKString()
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowTitle(this.Native, value).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var Position: Pair<Int, Int>
        get() {
            check(!this.IsClosed)
            memScoped {
                val x = this.alloc<IntVar>()
                val y = this.alloc<IntVar>()
                SDL_GetWindowPosition(this@MainWindow.Native, x.ptr, y.ptr).also { SDL.ThrowErrorIfNeeded() }
                return Pair(x.value, y.value)
            }
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowPosition(this.Native, value.first, value.second).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var Size: Pair<Int, Int>
        get() {
            check(!this.IsClosed)
            memScoped {
                val width = this.alloc<IntVar>()
                val height = this.alloc<IntVar>()
                SDL_GetWindowSize(this@MainWindow.Native, width.ptr, height.ptr).also { SDL.ThrowErrorIfNeeded() }
                return Pair(width.value, height.value)
            }
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowSize(this.Native, value.first, value.second).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsResizable: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.Native).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_RESIZABLE != 0UL
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowResizable(this.Native, value).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsShown: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.Native).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_HIDDEN == 0UL
        }
        set(value) {
            check(!this.IsClosed)
            if (value) {
                SDL_ShowWindow(this.Native)
            } else {
                SDL_HideWindow(this.Native)
            }
        }

    @OptIn(ExperimentalForeignApi::class)
    public val IsVisible: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.Native).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_HIDDEN == 0UL && flags and SDL_WINDOW_MINIMIZED == 0UL
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsInputEnabled: Boolean
        get() {
            check(!this.IsClosed)
            return SDL_TextInputActive(this.Native).also { SDL.ThrowErrorIfNeeded() }
        }
        set(value) {
            check(!this.IsClosed)
            if (value) {
                SDL_StartTextInput(this.Native).also { SDL.ThrowErrorIfNeeded() }
            } else {
                SDL_StopTextInput(this.Native).also { SDL.ThrowErrorIfNeeded() }
            }
        }

    @OptIn(ExperimentalForeignApi::class)
    public val HasMouseFocus: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.Native).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_MOUSE_FOCUS == 0UL
        }

    @OptIn(ExperimentalForeignApi::class)
    public val HasInputFocus: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.Native).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_INPUT_FOCUS == 0UL
        }

    public val Cursor: Cursor
        get() {
            check(!this.IsClosed)
            return field
        }

    @OptIn(ExperimentalForeignApi::class)
    public constructor(description: Description) {
        this._Native = run {
            when (description) {
                is Description.FullScreen -> {
                    var flags = SDL_WINDOW_VULKAN or SDL_WINDOW_FULLSCREEN
                    if (description.IsResizable) flags = flags or SDL_WINDOW_RESIZABLE
                    SDL_CreateWindow(description.Title, description.Width, description.Height, flags).also { SDL.ThrowErrorIfNeeded() }.also {
                        SDL_SetWindowPosition(it, SDL_WINDOWPOS_CENTERED.toInt(), SDL_WINDOWPOS_CENTERED.toInt()).also { SDL.ThrowErrorIfNeeded() }
                        SDL_SetWindowMinimumSize(it, 320, 240).also { SDL.ThrowErrorIfNeeded() }
                    }
                }
                is Description.Window -> {
                    var flags = SDL_WINDOW_VULKAN
                    if (description.IsResizable) flags = flags or SDL_WINDOW_RESIZABLE
                    SDL_CreateWindow(description.Title, description.Width, description.Height, flags).also { SDL.ThrowErrorIfNeeded() }.also {
                        SDL_SetWindowPosition(it, SDL_WINDOWPOS_CENTERED.toInt(), SDL_WINDOWPOS_CENTERED.toInt()).also { SDL.ThrowErrorIfNeeded() }
                        SDL_SetWindowMinimumSize(it, 320, 240).also { SDL.ThrowErrorIfNeeded() }
                    }
                }
            }
        }
        this.Cursor = Cursor(this)
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        this.Cursor.close()
        this._Native = run {
            SDL_DestroyWindow(this.Native).also { SDL.ThrowErrorIfNeeded() }
            null
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Raise() {
        SDL_RaiseWindow(this.Native).also { SDL.ThrowErrorIfNeeded() }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun RequestClose() {
        check(!this.IsClosed)
        memScoped {
            val event = this.alloc<SDL_Event>()
            event.type = SDL_EVENT_WINDOW_CLOSE_REQUESTED
            event.window.windowID = this@MainWindow.ID
            SDL_PushEvent(event.ptr).also { SDL.ThrowErrorIfNeeded() }
        }
    }

}
