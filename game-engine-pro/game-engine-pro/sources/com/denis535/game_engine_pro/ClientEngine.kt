package com.denis535.game_engine_pro

import com.denis535.game_engine_pro.display.*
import com.denis535.game_engine_pro.input.*
import com.denis535.sdl.*
import kotlinx.cinterop.*

public abstract class ClientEngine : Engine {

    public val Window: MainWindow
        get() {
            check(!this.IsClosed)
            return field
        }

    public val Cursor: Cursor
        get() {
            check(!this.IsClosed)
            return field
        }

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

    @OptIn(ExperimentalForeignApi::class)
    public constructor(manifest: Manifest, windowProvider: () -> MainWindow) : super(manifest) {
        SDL_Init(SDL_INIT_VIDEO or SDL_INIT_AUDIO).also { SDL.ThrowErrorIfNeeded() }
        this.Window = windowProvider()
        this.Cursor = Cursor()
        this.Mouse = Mouse()
        this.Keyboard = Keyboard()
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        this.Keyboard.close()
        this.Mouse.close()
        this.Cursor.close()
        this.Window.close()
        super.close()
    }

    @OptIn(ExperimentalForeignApi::class)
    internal override fun ProcessEvent(event: CPointer<SDL_Event>) {
        super.ProcessEvent(event)
        when (event.pointed.type) {
            SDL_EVENT_WINDOW_MOUSE_ENTER -> {
                val evt = event.pointed.window
                val timestamp = this.Time.Time
                val windowID = evt.windowID
                val event = MouseFocusEvent(timestamp, windowID)
                this.OnMouseFocus(event)
            }
            SDL_EVENT_WINDOW_MOUSE_LEAVE -> {
                val evt = event.pointed.window
                val timestamp = this.Time.Time
                val windowID = evt.windowID
                val event = MouseFocusLostEvent(timestamp, windowID)
                this.OnMouseFocusLost(event)
            }

            SDL_EVENT_WINDOW_FOCUS_GAINED -> {
                val evt = event.pointed.window
                val timestamp = this.Time.Time
                val windowID = evt.windowID
                val event = KeyboardFocusEvent(timestamp, windowID)
                this.OnKeyboardFocus(event)
            }
            SDL_EVENT_WINDOW_FOCUS_LOST -> {
                val evt = event.pointed.window
                val timestamp = this.Time.Time
                val windowID = evt.windowID
                val event = KeyboardFocusLostEvent(timestamp, windowID)
                this.OnKeyboardFocusLost(event)
            }

            SDL_EVENT_TEXT_INPUT -> {
                val evt = event.pointed.text
                val timestamp = this.Time.Time
                val windowID = evt.windowID
                val text = evt.text?.toKStringFromUtf8()
                if (text != null) {
                    val event = TextInputEvent(timestamp, windowID, text)
                    this.OnTextInput(event)
                }
            }

            SDL_EVENT_MOUSE_MOTION -> {
                val evt = event.pointed.motion
                val timestamp = this.Time.Time
                val windowID = evt.windowID
                val x = evt.x
                val y = evt.y
                val deltaX = evt.xrel
                val deltaY = evt.yrel
                val event = MouseMoveEvent(timestamp, windowID, Pair(x, y), Pair(deltaX, deltaY))
                this.OnMouseMove(event)
                this.Mouse.OnMove?.invoke(event)
            }
            SDL_EVENT_MOUSE_BUTTON_DOWN, SDL_EVENT_MOUSE_BUTTON_UP -> {
                val evt = event.pointed.button
                val timestamp = this.Time.Time
                val windowID = evt.windowID
                val x = evt.x
                val y = evt.y
                val isPressed = evt.down
                val button = MouseButton.FromNativeValue(evt.button)
                val clickCount = evt.clicks.toInt()
                if (button != null) {
                    val event = MouseButtonEvent(timestamp, windowID, Pair(x, y), button, clickCount)
                    if (isPressed) {
                        this.OnMouseButtonPress(event)
                        this.Mouse.OnButtonPress?.invoke(event)
                    } else {
                        this.OnMouseButtonRelease(event)
                        this.Mouse.OnButtonRelease?.invoke(event)
                    }
                }
            }
            SDL_EVENT_MOUSE_WHEEL -> {
                val evt = event.pointed.wheel
                val timestamp = this.Time.Time
                val windowID = evt.windowID
                val x = evt.mouse_x
                val y = evt.mouse_y
                val isDirectionNormal = evt.direction == SDL_MouseWheelDirection.SDL_MOUSEWHEEL_NORMAL
                val scrollX: Float
                val scrollY: Float
                val integerScrollX: Int
                val integerScrollY: Int
                if (isDirectionNormal) {
                    scrollX = evt.x
                    scrollY = evt.y
                    integerScrollX = evt.integer_x
                    integerScrollY = evt.integer_y
                } else {
                    scrollX = -evt.x
                    scrollY = -evt.y
                    integerScrollX = -evt.integer_x
                    integerScrollY = -evt.integer_y
                }
                val event = MouseWheelScrollEvent(timestamp, windowID, Pair(x, y), Pair(scrollX, scrollY), Pair(integerScrollX, integerScrollY))
                this.OnMouseWheelScroll(event)
                this.Mouse.OnWheelScroll?.invoke(event)
            }

//            SDL_EVENT_FINGER_DOWN -> {
//                val evt = event.pointed.tfinger
//                val timestamp = time.Time
//                val windowID = evt.windowID
//                val fingerID = evt.fingerID
//                val x = evt.x
//                val y = evt.y
//                val pressure = evt.pressure
//            }
//            SDL_EVENT_FINGER_UP, SDL_EVENT_FINGER_CANCELED -> {
//                val evt = evt.pointed.tfinger
//                val timestamp = time.Time
//                val windowID = evt.windowID
//                val fingerID = evt.fingerID
//                val x = evt.x
//                val y = evt.y
//            }
//            SDL_EVENT_FINGER_MOTION -> {
//                val evt = event.pointed.tfinger
//                val timestamp = time.Time
//                val windowID = evt.windowID
//                val fingerID = evt.fingerID
//                val x = evt.x
//                val y = evt.y
//                val deltaX = evt.dx
//                val deltaY = evt.dy
//                val pressure = evt.pressure
//            }

            SDL_EVENT_KEY_DOWN, SDL_EVENT_KEY_UP -> {
                val evt = event.pointed.key
                val timestamp = this.Time.Time
                val windowID = evt.windowID
                val isPressed = evt.down
                val isRepeated = evt.repeat
                val key = KeyboardKey.FromNativeValue(evt.scancode)
                if (key != null) {
                    val event = KeyboardKeyEvent(timestamp, windowID, key)
                    if (isPressed) {
                        if (!isRepeated) {
                            this.OnKeyboardKeyPress(event)
                            this.Keyboard.OnKeyPress?.invoke(event)
                        } else {
                            this.OnKeyboardKeyRepeat(event)
                            this.Keyboard.OnKeyRepeat?.invoke(event)
                        }
                    } else {
                        this.OnKeyboardKeyRelease(event)
                        this.Keyboard.OnKeyRelease?.invoke(event)
                    }
                }
            }

//            SDL_EVENT_GAMEPAD_BUTTON_DOWN, SDL_EVENT_GAMEPAD_BUTTON_UP -> {
//                val evt = event.pointed.gbutton
//                val timestamp = this.Time.Time
//                val gamepadID = evt.which
//                val button = evt.button // https://wiki.libsdl.org/SDL3/SDL_GamepadButton
//                val isPressed = evt.down
//            }
//            SDL_EVENT_GAMEPAD_AXIS_MOTION -> {
//                val evt = event.pointed.gaxis
//                val timestamp = this.Time.Time
//                val gamepadID = evt.which
//                val axis = evt.axis // https://wiki.libsdl.org/SDL3/SDL_GamepadAxis
//                val value = evt.value.let {
//                    Math.Lerp(-1f, 1f, Math.InverseLerp(SDL_JOYSTICK_AXIS_MIN.toFloat(), SDL_JOYSTICK_AXIS_MAX.toFloat(), it.toFloat()))
//                }
//            }

//            SDL_EVENT_JOYSTICK_HAT_MOTION -> {
//                val evt = event.pointed.jhat
//                val timestamp = this.Time.Time
//                val windowID = evt.windowID
//                val joystickID = evt.which
//                val hat = evt.hat
//                val value = evt.value
//                when (value.toUInt()) {
//                    SDL_HAT_LEFT -> {
//
//                    }
//                    SDL_HAT_RIGHT -> {
//
//                    }
//                    SDL_HAT_UP -> {
//
//                    }
//                    SDL_HAT_DOWN -> {
//
//                    }
//                    SDL_HAT_LEFTUP -> {
//
//                    }
//                    SDL_HAT_RIGHTUP -> {
//
//                    }
//                    SDL_HAT_LEFTDOWN -> {
//
//                    }
//                    SDL_HAT_RIGHTDOWN -> {
//
//                    }
//                }
//            }
//            SDL_EVENT_JOYSTICK_BUTTON_DOWN, SDL_EVENT_JOYSTICK_BUTTON_UP -> {
//                val evt = event.pointed.jbutton
//                val timestamp = this.Time.Time
//                val windowID = evt.windowID
//                val joystickID = evt.which
//                val button = evt.button
//                val isPressed = evt.down
//            }
//            SDL_EVENT_JOYSTICK_AXIS_MOTION -> {
//                val evt = event.pointed.jaxis
//                val timestamp = this.Time.Time
//                val windowID = evt.windowID
//                val joystickID = evt.which
//                val axis = evt.axis
//                val value = evt.value.let {
//                    Math.Lerp(-1f, 1f, Math.InverseLerp(SDL_JOYSTICK_AXIS_MIN.toFloat(), SDL_JOYSTICK_AXIS_MAX.toFloat(), it.toFloat()))
//                }
//            }

            SDL_EVENT_WINDOW_CLOSE_REQUESTED -> {
                this.IsRunning = false
            }
        }
    }

    internal fun OnDrawInternal() = this.OnDraw()
    protected abstract fun OnDraw()

    protected abstract fun OnMouseFocus(event: MouseFocusEvent)
    protected abstract fun OnMouseFocusLost(event: MouseFocusLostEvent)

    protected abstract fun OnKeyboardFocus(event: KeyboardFocusEvent)
    protected abstract fun OnKeyboardFocusLost(event: KeyboardFocusLostEvent)

    protected abstract fun OnTextInput(event: TextInputEvent)

    protected abstract fun OnMouseMove(event: MouseMoveEvent)
    protected abstract fun OnMouseButtonPress(event: MouseButtonEvent)
    protected abstract fun OnMouseButtonRelease(event: MouseButtonEvent)
    protected abstract fun OnMouseWheelScroll(event: MouseWheelScrollEvent)

    protected abstract fun OnKeyboardKeyPress(event: KeyboardKeyEvent)
    protected abstract fun OnKeyboardKeyRepeat(event: KeyboardKeyEvent)
    protected abstract fun OnKeyboardKeyRelease(event: KeyboardKeyEvent)

}

public class MouseFocusEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
)

public class MouseFocusLostEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
)

public class KeyboardFocusEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
)

public class KeyboardFocusLostEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
)

public class TextInputEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val Text: String,
)
