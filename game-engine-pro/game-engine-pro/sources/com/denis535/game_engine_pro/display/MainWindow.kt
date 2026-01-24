package com.denis535.game_engine_pro.display

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

    public var IsClosed: Boolean = false
        private set

    @OptIn(ExperimentalForeignApi::class)
    private val NativeWindow: CPointer<SDL_Window>
        get() {
            check(!this.IsClosed)
            return field
        }

    @OptIn(ExperimentalForeignApi::class)
    private val NativeWindowID: UInt
        get() {
            check(!this.IsClosed)
            return SDL_GetWindowID(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public val Display: Display
        get() {
            check(!this.IsClosed)
            return Display(SDL_GetDisplayForWindow(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() })
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsFullScreen: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_FULLSCREEN != 0UL
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowFullscreen(this.NativeWindow, value).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var Title: String
        get() {
            check(!this.IsClosed)
            return SDL_GetWindowTitle(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }!!.toKString()
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowTitle(this.NativeWindow, value).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var Position: Pair<Int, Int>
        get() {
            check(!this.IsClosed)
            memScoped {
                val x = this.alloc<IntVar>()
                val y = this.alloc<IntVar>()
                SDL_GetWindowPosition(this@MainWindow.NativeWindow, x.ptr, y.ptr).also { SDL.ThrowErrorIfNeeded() }
                return Pair(x.value, y.value)
            }
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowPosition(this.NativeWindow, value.first, value.second).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var Size: Pair<Int, Int>
        get() {
            check(!this.IsClosed)
            memScoped {
                val width = this.alloc<IntVar>()
                val height = this.alloc<IntVar>()
                SDL_GetWindowSize(this@MainWindow.NativeWindow, width.ptr, height.ptr).also { SDL.ThrowErrorIfNeeded() }
                return Pair(width.value, height.value)
            }
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowSize(this.NativeWindow, value.first, value.second).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsResizable: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_RESIZABLE != 0UL
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowResizable(this.NativeWindow, value).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsShown: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_HIDDEN == 0UL
        }
        set(value) {
            check(!this.IsClosed)
            if (value) {
                SDL_ShowWindow(this.NativeWindow)
            } else {
                SDL_HideWindow(this.NativeWindow)
            }
        }

    @OptIn(ExperimentalForeignApi::class)
    public val IsVisible: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_HIDDEN == 0UL && flags and SDL_WINDOW_MINIMIZED == 0UL
        }

    @OptIn(ExperimentalForeignApi::class)
    public val HasMouseFocus: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_MOUSE_FOCUS != 0UL
        }

    @OptIn(ExperimentalForeignApi::class)
    public val HasKeyboardFocus: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_INPUT_FOCUS != 0UL
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsMouseLocked: Boolean
        get() {
            check(!this.IsClosed)
            return SDL_GetWindowRelativeMouseMode(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowRelativeMouseMode(this.NativeWindow, value).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsMouseGrabbed: Boolean
        get() {
            check(!this.IsClosed)
            return SDL_GetWindowMouseGrab(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowMouseGrab(this.NativeWindow, value).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsMouseCaptured: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
            return flags and SDL_WINDOW_MOUSE_CAPTURE != 0uL
        }
        set(value) {
            check(!this.IsClosed)
            SDL_CaptureMouse(value).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsKeyboardGrabbed: Boolean
        get() {
            check(!this.IsClosed)
            return SDL_GetWindowKeyboardGrab(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowKeyboardGrab(this.NativeWindow, value).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsTextInputEnabled: Boolean
        get() {
            check(!this.IsClosed)
            return SDL_TextInputActive(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
        }
        set(value) {
            check(!this.IsClosed)
            if (value) {
                SDL_StartTextInput(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
            } else {
                SDL_StopTextInput(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
            }
        }

    @OptIn(ExperimentalForeignApi::class)
    public constructor(description: Description) {
        val properties = SDL_CreateProperties()
        if (description is Description.FullScreen) {
            SDL_SetBooleanProperty(properties, SDL_PROP_WINDOW_CREATE_FULLSCREEN_BOOLEAN, true)
            SDL_SetStringProperty(properties, SDL_PROP_WINDOW_CREATE_TITLE_STRING, description.Title)
            SDL_SetNumberProperty(properties, SDL_PROP_WINDOW_CREATE_X_NUMBER, SDL_WINDOWPOS_CENTERED.toLong())
            SDL_SetNumberProperty(properties, SDL_PROP_WINDOW_CREATE_Y_NUMBER, SDL_WINDOWPOS_CENTERED.toLong())
            SDL_SetNumberProperty(properties, SDL_PROP_WINDOW_CREATE_WIDTH_NUMBER, description.Width.toLong())
            SDL_SetNumberProperty(properties, SDL_PROP_WINDOW_CREATE_HEIGHT_NUMBER, description.Height.toLong())
            SDL_SetBooleanProperty(properties, SDL_PROP_WINDOW_CREATE_RESIZABLE_BOOLEAN, description.IsResizable)
            SDL_SetBooleanProperty(properties, SDL_PROP_WINDOW_CREATE_VULKAN_BOOLEAN, true)
        } else if (description is Description.Window) {
            SDL_SetBooleanProperty(properties, SDL_PROP_WINDOW_CREATE_FULLSCREEN_BOOLEAN, false)
            SDL_SetStringProperty(properties, SDL_PROP_WINDOW_CREATE_TITLE_STRING, description.Title)
            SDL_SetNumberProperty(properties, SDL_PROP_WINDOW_CREATE_X_NUMBER, SDL_WINDOWPOS_CENTERED.toLong())
            SDL_SetNumberProperty(properties, SDL_PROP_WINDOW_CREATE_Y_NUMBER, SDL_WINDOWPOS_CENTERED.toLong())
            SDL_SetNumberProperty(properties, SDL_PROP_WINDOW_CREATE_WIDTH_NUMBER, description.Width.toLong())
            SDL_SetNumberProperty(properties, SDL_PROP_WINDOW_CREATE_HEIGHT_NUMBER, description.Height.toLong())
            SDL_SetBooleanProperty(properties, SDL_PROP_WINDOW_CREATE_RESIZABLE_BOOLEAN, description.IsResizable)
            SDL_SetBooleanProperty(properties, SDL_PROP_WINDOW_CREATE_VULKAN_BOOLEAN, true)
        }
        this.NativeWindow = SDL_CreateWindowWithProperties(properties).also { SDL.ThrowErrorIfNeeded() }!!.also {
            SDL_SetWindowMinimumSize(it, 320, 240).also { SDL.ThrowErrorIfNeeded() }
        }
        SDL_DestroyProperties(properties)
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        SDL_DestroyWindow(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
        this.IsClosed = true
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Raise() {
        check(!this.IsClosed)
        SDL_RaiseWindow(this.NativeWindow).also { SDL.ThrowErrorIfNeeded() }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun RequestClose() {
        check(!this.IsClosed)
        memScoped {
            val event = this.alloc<SDL_Event>()
            event.type = SDL_EVENT_WINDOW_CLOSE_REQUESTED
            event.window.windowID = this@MainWindow.NativeWindowID
            SDL_PushEvent(event.ptr).also { SDL.ThrowErrorIfNeeded() }
        }
    }

}
