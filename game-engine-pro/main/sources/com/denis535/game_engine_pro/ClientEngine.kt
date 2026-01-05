package com.denis535.game_engine_pro

import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.windows.*
import com.denis535.sdl.*
import kotlinx.cinterop.*

public abstract class ClientEngine : Engine {

    public val Mouse: Mouse
        get() {
            check(!this.IsClosed)
            return field
        }

    public val Keyboard: Keyboard
        get() {
            check(!this.IsClosed)
            return field
        }

    public var Window: MainWindow? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        protected set(value) {
            check(!this.IsClosed)
            field = value
        }

    @OptIn(ExperimentalForeignApi::class)
    public constructor(manifest: Manifest) {
        SDL_Init(SDL_INIT_VIDEO or SDL_INIT_JOYSTICK).also { SDL.ThrowErrorIfNeeded() }
        SDL_SetAppMetadata(manifest.Name, manifest.Version, manifest.Id).also { SDL.ThrowErrorIfNeeded() }
        SDL_SetAppMetadataProperty(SDL_PROP_APP_METADATA_CREATOR_STRING, manifest.Creator).also { SDL.ThrowErrorIfNeeded() }
        this.Mouse = Mouse()
        this.Keyboard = Keyboard()
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        this.Keyboard.close()
        this.Mouse.close()
        SDL_Quit().also { SDL.ThrowErrorIfNeeded() }
    }

    @OptIn(ExperimentalForeignApi::class)
    internal override fun ProcessEvent(event: CPointer<SDL_Event>): Boolean {
        if (super.ProcessEvent(event)) {
            return true
        }
        when (event.pointed.type) {
            SDL_EVENT_MOUSE_MOTION -> {
                val event = event.pointed.motion
                val cursorX = event.x
                val cursorY = event.y
                val cursorDeltaX = event.xrel
                val cursorDeltaY = event.yrel
                this.OnMouseCursorMove(MouseCursorMoveEvent(cursorX, cursorY, cursorDeltaX, cursorDeltaY))
            }
            SDL_EVENT_MOUSE_BUTTON_DOWN, SDL_EVENT_MOUSE_BUTTON_UP -> {
                val event = event.pointed.button
                val cursorX = event.x
                val cursorY = event.y
                val isPressed = event.down
                val button = MouseButton.FromNativeValue(event.button)
                val clicks = event.clicks.toInt()
                if (button != null) {
                    if (isPressed) {
                        this.OnMouseButtonPress(MouseButtonActionEvent(cursorX, cursorY, button, clicks))
                    } else {
                        this.OnMouseButtonRelease(MouseButtonActionEvent(cursorX, cursorY, button, clicks))
                    }
                }
            }
            SDL_EVENT_MOUSE_WHEEL -> {
                val event = event.pointed.wheel
                val cursorX = event.mouse_x
                val cursorY = event.mouse_y
                val isDirectionNormal = event.direction == SDL_MouseWheelDirection.SDL_MOUSEWHEEL_NORMAL
                val scrollX: Float
                val scrollY: Float
                val scrollIntegerX: Int
                val scrollIntegerY: Int
                if (isDirectionNormal) {
                    scrollX = event.x
                    scrollY = event.y
                    scrollIntegerX = event.integer_x
                    scrollIntegerY = event.integer_y
                } else {
                    scrollX = -event.x
                    scrollY = -event.y
                    scrollIntegerX = -event.integer_x
                    scrollIntegerY = -event.integer_y
                }
                this.OnMouseWheelScroll(MouseWheelScrollEvent(cursorX, cursorY, scrollX, scrollY, scrollIntegerX, scrollIntegerY))
            }
            SDL_EVENT_KEY_DOWN, SDL_EVENT_KEY_UP -> {
                val event = event.pointed.key
                val isPressed = event.down
                val isRepeated = event.repeat
                val key = KeyboardKey.FromNativeValue(event.scancode)
                if (key != null) {
                    if (isPressed) {
                        if (!isRepeated) {
                            this.OnKeyboardKeyPress(KeyboardKeyActionEvent(key))
                        } else {
                            this.OnKeyboardKeyRepeat(KeyboardKeyActionEvent(key))
                        }
                    } else {
                        this.OnKeyboardKeyRelease(KeyboardKeyActionEvent(key))
                    }
                }
            }
            SDL_EVENT_JOYSTICK_ADDED, SDL_EVENT_JOYSTICK_REMOVED, SDL_EVENT_JOYSTICK_UPDATE_COMPLETE -> {

            }
            SDL_EVENT_JOYSTICK_HAT_MOTION -> {
                val event = event.pointed.jhat
                val id = event.which
                val hat = event.hat
                val value = event.value
                when (value.toUInt()) {
                    SDL_HAT_LEFT -> {

                    }
                    SDL_HAT_RIGHT -> {

                    }
                    SDL_HAT_UP -> {

                    }
                    SDL_HAT_DOWN -> {

                    }
                    SDL_HAT_LEFTUP -> {

                    }
                    SDL_HAT_RIGHTUP -> {

                    }
                    SDL_HAT_LEFTDOWN -> {

                    }
                    SDL_HAT_RIGHTDOWN -> {

                    }
                }
            }
            SDL_EVENT_JOYSTICK_BUTTON_DOWN, SDL_EVENT_JOYSTICK_BUTTON_UP -> {
                val event = event.pointed.jbutton
                val id = event.which
                val button = event.button
                val isPressed = event.down
            }
            SDL_EVENT_JOYSTICK_AXIS_MOTION -> {
                val event = event.pointed.jaxis
                val id = event.which
                val axis = event.axis
                val value = event.value.let {
                    Lerp(-1f, 1f, InvLerp(-32768f, 32767f, it.toFloat()))
                }
            }
            SDL_EVENT_TEXT_INPUT -> {
                val event = event.pointed.text
                val text = event.text?.toKStringFromUtf8()
                if (text != null) {
                    this.OnTextInput(text)
                }
            }
            SDL_EVENT_WINDOW_CLOSE_REQUESTED -> {
                return true
            }
        }
        return false
    }

    @OptIn(ExperimentalForeignApi::class)
    internal override fun ProcessFrame(info: FrameInfo, fixedDeltaTime: Float) {
        super.ProcessFrame(info, fixedDeltaTime)
        this.OnDraw(info)
    }

    protected abstract fun OnDraw(info: FrameInfo)

    protected abstract fun OnMouseCursorMove(event: MouseCursorMoveEvent)
    protected abstract fun OnMouseButtonPress(event: MouseButtonActionEvent)
    protected abstract fun OnMouseButtonRelease(event: MouseButtonActionEvent)
    protected abstract fun OnMouseWheelScroll(event: MouseWheelScrollEvent)

    protected abstract fun OnKeyboardKeyPress(event: KeyboardKeyActionEvent)
    protected abstract fun OnKeyboardKeyRepeat(event: KeyboardKeyActionEvent)
    protected abstract fun OnKeyboardKeyRelease(event: KeyboardKeyActionEvent)

    protected abstract fun OnTextInput(text: String)

}

public class MouseCursorMoveEvent(
    public val CursorX: Float,
    public val CursorY: Float,
    public val CursorDeltaX: Float,
    public val CursorDeltaY: Float,
)

public class MouseButtonActionEvent(
    public val CursorX: Float,
    public val CursorY: Float,
    public val Button: MouseButton,
    public val Clicks: Int,
)

public class MouseWheelScrollEvent(
    public val CursorX: Float,
    public val CursorY: Float,
    public val ScrollX: Float,
    public val ScrollY: Float,
    public val ScrollIntegerX: Int,
    public val ScrollIntegerY: Int,
)

public class KeyboardKeyActionEvent(
    public val Key: KeyboardKey
)

private fun Lerp(a: Float, b: Float, t: Float): Float {
    return a + (b - a) * t
}

private fun InvLerp(a: Float, b: Float, value: Float): Float {
    return (value - a) / (b - a)
}
