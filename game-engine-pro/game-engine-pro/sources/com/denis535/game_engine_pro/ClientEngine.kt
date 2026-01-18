package com.denis535.game_engine_pro

import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.windows.*
import com.denis535.sdl.*
import kotlinx.cinterop.*

public abstract class ClientEngine : Engine {

    public val Window: MainWindow
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
    public constructor(manifest: Manifest, windowProvider: () -> MainWindow) {
        SDL_Init(SDL_INIT_VIDEO).also { SDL.ThrowErrorIfNeeded() }
        SDL_SetAppMetadata(manifest.Name, manifest.Version, manifest.Id).also { SDL.ThrowErrorIfNeeded() }
        SDL_SetAppMetadataProperty(SDL_PROP_APP_METADATA_CREATOR_STRING, manifest.Creator).also { SDL.ThrowErrorIfNeeded() }
        this.Window = windowProvider()
        this.Mouse = Mouse()
        this.Keyboard = Keyboard()
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        this.Keyboard.close()
        this.Mouse.close()
        this.Window.close()
        SDL_Quit().also { SDL.ThrowErrorIfNeeded() }
    }

    @OptIn(ExperimentalForeignApi::class)
    internal override fun ProcessFrame(time: Time, fixedDeltaTime: Float) {
        super.ProcessFrame(time, fixedDeltaTime)
        this.OnDraw(time)
    }

    @OptIn(ExperimentalForeignApi::class)
    internal override fun ProcessEvent(time: Time, event: CPointer<SDL_Event>) {
        super.ProcessEvent(time, event)
        when (event.pointed.type) {
            SDL_EVENT_WINDOW_MOUSE_ENTER -> {
                val evt = event.pointed.window
                val timestamp = time.Time
                val windowID = evt.windowID
                this.OnMouseFocus()
            }
            SDL_EVENT_WINDOW_MOUSE_LEAVE -> {
                val evt = event.pointed.window
                val timestamp = time.Time
                val windowID = evt.windowID
                this.OnMouseFocusLost()
            }

            SDL_EVENT_WINDOW_FOCUS_GAINED -> {
                val evt = event.pointed.window
                val timestamp = time.Time
                val windowID = evt.windowID
                this.OnInputFocus()
            }
            SDL_EVENT_WINDOW_FOCUS_LOST -> {
                val evt = event.pointed.window
                val timestamp = time.Time
                val windowID = evt.windowID
                this.OnInputFocusLost()
            }

            SDL_EVENT_TEXT_INPUT -> {
                val evt = event.pointed.text
                val timestamp = time.Time
                val windowID = evt.windowID
                val text = evt.text?.toKStringFromUtf8()
                if (text != null) {
                    this.OnInput(text)
                }
            }

            SDL_EVENT_MOUSE_MOTION -> {
                val evt = event.pointed.motion
                val timestamp = time.Time
                val windowID = evt.windowID
                val x = evt.x
                val y = evt.y
                val deltaX = evt.xrel
                val deltaY = evt.yrel
                val event = MouseMoveEvent(timestamp, windowID, Pair(x, y), Pair(deltaX, deltaY))
                this.OnMouseMove(event)
            }
            SDL_EVENT_MOUSE_BUTTON_DOWN, SDL_EVENT_MOUSE_BUTTON_UP -> {
                val evt = event.pointed.button
                val timestamp = time.Time
                val windowID = evt.windowID
                val x = evt.x
                val y = evt.y
                val isPressed = evt.down
                val button = MouseButton.FromNativeValue(evt.button)
                val clicks = evt.clicks.toInt()
                val event = if (button != null) {
                    MouseButtonActionEvent(timestamp, windowID, Pair(x, y), button, clicks)
                } else {
                    null
                }
                if (isPressed) {
                    if (event != null) this.OnMouseButtonPress(event)
                } else {
                    if (event != null) this.OnMouseButtonRelease(event)
                }
            }
            SDL_EVENT_MOUSE_WHEEL -> {
                val evt = event.pointed.wheel
                val timestamp = time.Time
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
                val event = MouseWheelScrollEvent(timestamp, windowID, Pair(x, y), scrollX, scrollY, integerScrollX, integerScrollY)
                this.OnMouseWheelScroll(event)
            }

//            SDL_EVENT_FINGER_DOWN -> {
//                val evt = event.pointed.tfinger
//                val timestamp = time.Time
//                val windowID = evt.windowID
//                val id = evt.fingerID
//                val x = evt.x
//                val y = evt.y
//                val pressure = evt.pressure
//            }
//            SDL_EVENT_FINGER_UP, SDL_EVENT_FINGER_CANCELED -> {
//                val evt = evt.pointed.tfinger
//                val timestamp = time.Time
//                val windowID = evt.windowID
//                val id = evt.fingerID
//                val x = evt.x
//                val y = evt.y
//            }
//            SDL_EVENT_FINGER_MOTION -> {
//                val evt = event.pointed.tfinger
//                val timestamp = time.Time
//                val windowID = evt.windowID
//                val id = evt.fingerID
//                val x = evt.x
//                val y = evt.y
//                val deltaX = evt.dx
//                val deltaY = evt.dy
//                val pressure = evt.pressure
//            }

            SDL_EVENT_KEY_DOWN, SDL_EVENT_KEY_UP -> {
                val evt = event.pointed.key
                val timestamp = time.Time
                val windowID = evt.windowID
                val isPressed = evt.down
                val isRepeated = evt.repeat
                val key = KeyboardKey.FromNativeValue(evt.scancode)
                val event = if (key != null) {
                    KeyboardKeyActionEvent(timestamp, windowID, key)
                } else {
                    null
                }
                if (isPressed) {
                    if (!isRepeated) {
                        if (event != null) this.OnKeyboardKeyPress(event)
                    } else {
                        if (event != null) this.OnKeyboardKeyRepeat(event)
                    }
                } else {
                    if (event != null) this.OnKeyboardKeyRelease(event)
                }
            }

//            SDL_EVENT_JOYSTICK_HAT_MOTION -> {
//                val evt = event.pointed.jhat
//                val timestamp = time.Time
//                val windowID = evt.windowID
//                val id = evt.which
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
//                val timestamp = time.Time
//                val windowID = evt.windowID
//                val id = evt.which
//                val button = evt.button
//                val isPressed = evt.down
//            }
//            SDL_EVENT_JOYSTICK_AXIS_MOTION -> {
//                val evt = event.pointed.jaxis
//                val timestamp = time.Time
//                val windowID = evt.windowID
//                val id = evt.which
//                val axis = evt.axis
//                val value = evt.value.let {
//                    MathUtils.Lerp(-1f, 1f, MathUtils.InverseLerp(SDL_JOYSTICK_AXIS_MIN.toFloat(), SDL_JOYSTICK_AXIS_MAX.toFloat(), it.toFloat()))
//                }
//            }

            SDL_EVENT_WINDOW_CLOSE_REQUESTED -> {
                val evt = event.pointed.window
                val timestamp = time.Time
                val windowID = evt.windowID
                this.IsRunning = false
            }
        }
    }

    protected open fun OnMouseFocus() {
    }

    protected open fun OnMouseFocusLost() {
    }

    protected open fun OnInputFocus() {
    }

    protected open fun OnInputFocusLost() {
    }

    protected open fun OnInput(text: String) {
    }

    protected open fun OnMouseMove(event: MouseMoveEvent) {
        this.Mouse.OnMove?.invoke(event)
    }

    protected open fun OnMouseButtonPress(event: MouseButtonActionEvent) {
        this.Mouse.OnButtonPress?.invoke(event)
    }

    protected open fun OnMouseButtonRelease(event: MouseButtonActionEvent) {
        this.Mouse.OnButtonRelease?.invoke(event)
    }

    protected open fun OnMouseWheelScroll(event: MouseWheelScrollEvent) {
        this.Mouse.OnWheelScroll?.invoke(event)
    }

    protected open fun OnKeyboardKeyPress(event: KeyboardKeyActionEvent) {
        this.Keyboard.OnKeyPress?.invoke(event)
    }

    protected open fun OnKeyboardKeyRepeat(event: KeyboardKeyActionEvent) {
        this.Keyboard.OnKeyRepeat?.invoke(event)
    }

    protected open fun OnKeyboardKeyRelease(event: KeyboardKeyActionEvent) {
        this.Keyboard.OnKeyRelease?.invoke(event)
    }

    protected abstract fun OnDraw(time: Time)

}
