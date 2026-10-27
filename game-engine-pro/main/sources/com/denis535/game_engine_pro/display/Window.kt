package com.denis535.game_engine_pro.display

import cnames.structs.*
import com.denis535.game_engine_pro.*
import com.denis535.internal.sdl.*
import kotlinx.cinterop.*

public class Window : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    @OptIn(ExperimentalForeignApi::class)
    private val NativeWindow: CPointer<SDL_Window>
        get() {
            check(!this.IsClosed)
            return field
        }
    @OptIn(ExperimentalForeignApi::class)
    internal val NativeWindowID: UInt
        get() {
            check(!this.IsClosed)
            return SDL_GetWindowID(this.NativeWindow).SDL_CheckError()
        }

    @OptIn(ExperimentalForeignApi::class)
    public val Display: Display
        get() {
            check(!this.IsClosed)
            return Display(SDL_GetDisplayForWindow(this.NativeWindow).SDL_CheckError())
        }

    @OptIn(ExperimentalForeignApi::class)
    public var Title: String
        get() {
            check(!this.IsClosed)
            return SDL_GetWindowTitle(this.NativeWindow).SDL_CheckError()!!.toKString()
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowTitle(this.NativeWindow, value).SDL_CheckError()
        }
    @OptIn(ExperimentalForeignApi::class)
    public var IsFullScreen: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).SDL_CheckError()
            return flags and SDL_WINDOW_FULLSCREEN != 0UL
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowFullscreen(this.NativeWindow, value).SDL_CheckError()
        }
    @OptIn(ExperimentalForeignApi::class)
    public var Position: Vector2I
        get() {
            check(!this.IsClosed)
            memScoped {
                val x = this.alloc<IntVar>()
                val y = this.alloc<IntVar>()
                SDL_GetWindowPosition(this@Window.NativeWindow, x.ptr, y.ptr).SDL_CheckError()
                return Vector2I(x.value, y.value)
            }
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowPosition(this.NativeWindow, value.X, value.Y).SDL_CheckError()
        }
    @OptIn(ExperimentalForeignApi::class)
    public var Size: Vector2I
        get() {
            check(!this.IsClosed)
            memScoped {
                val width = this.alloc<IntVar>()
                val height = this.alloc<IntVar>()
                SDL_GetWindowSize(this@Window.NativeWindow, width.ptr, height.ptr).SDL_CheckError()
                return Vector2I(width.value, height.value)
            }
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowSize(this.NativeWindow, value.X, value.Y).SDL_CheckError()
        }
    @OptIn(ExperimentalForeignApi::class)
    public val SizePhysical: Vector2I
        get() {
            check(!this.IsClosed)
            memScoped {
                val width = this.alloc<IntVar>()
                val height = this.alloc<IntVar>()
                SDL_GetWindowSizeInPixels(this@Window.NativeWindow, width.ptr, height.ptr).SDL_CheckError()
                return Vector2I(width.value, height.value)
            }
        }

    @OptIn(ExperimentalForeignApi::class)
    public val IsShown: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).SDL_CheckError()
            return flags and SDL_WINDOW_HIDDEN == 0UL
        }
    @OptIn(ExperimentalForeignApi::class)
    public val IsVisible: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).SDL_CheckError()
            return flags and SDL_WINDOW_HIDDEN == 0UL && flags and SDL_WINDOW_MINIMIZED == 0UL
        }
    @OptIn(ExperimentalForeignApi::class)
    public var IsResizable: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).SDL_CheckError()
            return flags and SDL_WINDOW_RESIZABLE != 0UL
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowResizable(this.NativeWindow, value).SDL_CheckError()
        }
    @OptIn(ExperimentalForeignApi::class)
    public var IsTextInputEnabled: Boolean
        get() {
            check(!this.IsClosed)
            return SDL_TextInputActive(this.NativeWindow).SDL_CheckError()
        }
        set(value) {
            check(!this.IsClosed)
            if (value) {
                SDL_StartTextInput(this.NativeWindow).SDL_CheckError()
            } else {
                SDL_StopTextInput(this.NativeWindow).SDL_CheckError()
            }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsMouseLocked: Boolean
        get() {
            check(!this.IsClosed)
            return SDL_GetWindowRelativeMouseMode(this.NativeWindow).SDL_CheckError()
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowRelativeMouseMode(this.NativeWindow, value).SDL_CheckError()
        }
    @OptIn(ExperimentalForeignApi::class)
    public var IsMouseCaptured: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).SDL_CheckError()
            return flags and SDL_WINDOW_MOUSE_CAPTURE != 0uL
        }
        set(value) {
            check(!this.IsClosed)
            SDL_CaptureMouse(value).SDL_CheckError()
        }
    @OptIn(ExperimentalForeignApi::class)
    public var IsMouseGrabbed: Boolean
        get() {
            check(!this.IsClosed)
            return SDL_GetWindowMouseGrab(this.NativeWindow).SDL_CheckError()
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowMouseGrab(this.NativeWindow, value).SDL_CheckError()
        }
    @OptIn(ExperimentalForeignApi::class)
    public var IsKeyboardGrabbed: Boolean
        get() {
            check(!this.IsClosed)
            return SDL_GetWindowKeyboardGrab(this.NativeWindow).SDL_CheckError()
        }
        set(value) {
            check(!this.IsClosed)
            SDL_SetWindowKeyboardGrab(this.NativeWindow, value).SDL_CheckError()
        }

    @OptIn(ExperimentalForeignApi::class)
    public val HasMouseFocus: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).SDL_CheckError()
            return flags and SDL_WINDOW_MOUSE_FOCUS != 0UL
        }
    @OptIn(ExperimentalForeignApi::class)
    public val HasKeyboardFocus: Boolean
        get() {
            check(!this.IsClosed)
            val flags = SDL_GetWindowFlags(this.NativeWindow).SDL_CheckError()
            return flags and SDL_WINDOW_INPUT_FOCUS != 0UL
        }

    public var OnMouseFocusCallback: ((MouseFocusEvent) -> Unit)? = null
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
    public var OnKeyboardFocusCallback: ((KeyboardFocusEvent) -> Unit)? = null
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

    public var OnZoomCallback: ((ZoomEvent) -> Unit)? = null
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

    public var OnTextCallback: ((TextEvent) -> Unit)? = null
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

    @OptIn(ExperimentalForeignApi::class)
    public constructor(description: WindowDescription) {
        val properties = SDL_CreateProperties().SDL_CheckError().apply {
            SDL_SetBooleanProperty(this, SDL_PROP_WINDOW_CREATE_HIDDEN_BOOLEAN, true).SDL_CheckError()
            SDL_SetBooleanProperty(this, SDL_PROP_WINDOW_CREATE_VULKAN_BOOLEAN, true).SDL_CheckError()
            if (description is WindowDescription.FullScreen) {
                SDL_SetStringProperty(this, SDL_PROP_WINDOW_CREATE_TITLE_STRING, description.Title).SDL_CheckError()
                SDL_SetBooleanProperty(this, SDL_PROP_WINDOW_CREATE_FULLSCREEN_BOOLEAN, true).SDL_CheckError()
                SDL_SetNumberProperty(this, SDL_PROP_WINDOW_CREATE_X_NUMBER, SDL_WINDOWPOS_CENTERED.toLong()).SDL_CheckError()
                SDL_SetNumberProperty(this, SDL_PROP_WINDOW_CREATE_Y_NUMBER, SDL_WINDOWPOS_CENTERED.toLong()).SDL_CheckError()
                SDL_SetNumberProperty(this, SDL_PROP_WINDOW_CREATE_WIDTH_NUMBER, description.Width.toLong()).SDL_CheckError()
                SDL_SetNumberProperty(this, SDL_PROP_WINDOW_CREATE_HEIGHT_NUMBER, description.Height.toLong()).SDL_CheckError()
                SDL_SetBooleanProperty(this, SDL_PROP_WINDOW_CREATE_RESIZABLE_BOOLEAN, description.IsResizable).SDL_CheckError()
            } else if (description is WindowDescription.Window) {
                SDL_SetStringProperty(this, SDL_PROP_WINDOW_CREATE_TITLE_STRING, description.Title).SDL_CheckError()
                SDL_SetBooleanProperty(this, SDL_PROP_WINDOW_CREATE_FULLSCREEN_BOOLEAN, false).SDL_CheckError()
                SDL_SetNumberProperty(this, SDL_PROP_WINDOW_CREATE_X_NUMBER, SDL_WINDOWPOS_CENTERED.toLong()).SDL_CheckError()
                SDL_SetNumberProperty(this, SDL_PROP_WINDOW_CREATE_Y_NUMBER, SDL_WINDOWPOS_CENTERED.toLong()).SDL_CheckError()
                SDL_SetNumberProperty(this, SDL_PROP_WINDOW_CREATE_WIDTH_NUMBER, description.Width.toLong()).SDL_CheckError()
                SDL_SetNumberProperty(this, SDL_PROP_WINDOW_CREATE_HEIGHT_NUMBER, description.Height.toLong()).SDL_CheckError()
                SDL_SetBooleanProperty(this, SDL_PROP_WINDOW_CREATE_RESIZABLE_BOOLEAN, description.IsResizable).SDL_CheckError()
            }
        }
        this.NativeWindow = SDL_CreateWindowWithProperties(properties).SDL_CheckError()!!.apply {
            SDL_SetWindowMinimumSize(this, 320, 240).SDL_CheckError()
        }
        SDL_DestroyProperties(properties).SDL_CheckError()
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        SDL_DestroyWindow(this.NativeWindow).SDL_CheckError()
        this.IsClosed = true
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Show() {
        check(!this.IsClosed)
        SDL_ShowWindow(this.NativeWindow).SDL_CheckError()
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Hide() {
        check(!this.IsClosed)
        SDL_HideWindow(this.NativeWindow).SDL_CheckError()
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Raise() {
        check(!this.IsClosed)
        SDL_RaiseWindow(this.NativeWindow).SDL_CheckError()
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun RequestClose() {
        check(!this.IsClosed)
        memScoped {
            val event = this.alloc<SDL_Event>()
            event.type = SDL_EVENT_WINDOW_CLOSE_REQUESTED
            event.window.windowID = this@Window.NativeWindowID
            SDL_PushEvent(event.ptr).SDL_CheckError()
        }
    }

}

public sealed class WindowDescription(
    public val Title: String,
) {
    public class FullScreen(
        title: String,
        public val Width: Int = 1280,
        public val Height: Int = 720,
        public val IsResizable: Boolean = false,
    ) : WindowDescription(title)

    public class Window(
        title: String,
        public val Width: Int = 1280,
        public val Height: Int = 720,
        public val IsResizable: Boolean = false,
    ) : WindowDescription(title)
}

public class MouseFocusEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val IsFocusGained: Boolean,
)

public class KeyboardFocusEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val IsFocusGained: Boolean,
)

public class ZoomEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val State: ZoomState,
    public val Zoom: Float, // value <= 1 or value >= 1
)

public class TextEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val Text: String,
)

public enum class ZoomState {
    Begin,
    Changed,
    End;
}
