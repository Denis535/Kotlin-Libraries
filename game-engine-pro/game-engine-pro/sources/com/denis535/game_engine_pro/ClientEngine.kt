package com.denis535.game_engine_pro

import com.denis535.game_engine_pro.display.*
import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.math.*
import com.denis535.sdl.*
import kotlinx.cinterop.*

public abstract class ClientEngine : Engine {

    public val Window: Window
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

    public val Gamepads: List<Gamepad>
        get() {
            check(!this.IsClosed)
            return field
        }

    @OptIn(ExperimentalForeignApi::class)
    public constructor(description: Description, windowProvider: () -> Window) : super(description) {
        SDL_Init(SDL_INIT_VIDEO).SDL_CheckError()
        this.Window = windowProvider()
        this.Cursor = Cursor()
        this.Mouse = Mouse()
        this.Keyboard = Keyboard()
        this.Gamepads = listOf(Gamepad(), Gamepad(), Gamepad(), Gamepad())
        this.Window.IsShown = true
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        this.Gamepads.asReversed().forEach { it.close() }
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
            SDL_EVENT_WINDOW_SHOWN -> {
            }
            SDL_EVENT_WINDOW_HIDDEN -> {
            }
            SDL_EVENT_WINDOW_MOVED -> {
            }
            SDL_EVENT_WINDOW_RESIZED -> {
            }
            SDL_EVENT_WINDOW_MINIMIZED -> {
            }
            SDL_EVENT_WINDOW_MAXIMIZED -> {
            }
            SDL_EVENT_WINDOW_RESTORED -> {
            }
            SDL_EVENT_WINDOW_EXPOSED -> {
            }
            SDL_EVENT_WINDOW_PIXEL_SIZE_CHANGED -> {
            }

            SDL_EVENT_WINDOW_MOUSE_ENTER -> {
                val evt = event.pointed.window
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val event = MouseFocusEvent(timestamp, windowID, true)
                this.OnMouseFocus(event)
            }
            SDL_EVENT_WINDOW_MOUSE_LEAVE -> {
                val evt = event.pointed.window
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val event = MouseFocusEvent(timestamp, windowID, false)
                this.OnMouseFocus(event)
            }

            SDL_EVENT_WINDOW_FOCUS_GAINED -> {
                val evt = event.pointed.window
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val event = KeyboardFocusEvent(timestamp, windowID, true)
                this.OnKeyboardFocus(event)
            }
            SDL_EVENT_WINDOW_FOCUS_LOST -> {
                val evt = event.pointed.window
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val event = KeyboardFocusEvent(timestamp, windowID, false)
                this.OnKeyboardFocus(event)
            }

            SDL_EVENT_WINDOW_CLOSE_REQUESTED -> {
//                val evt = event.pointed.window
//                val timestamp = Frame.Time
//                val windowID = evt.windowID
                this.IsRunning = false
            }

            SDL_EVENT_TEXT_INPUT -> {
                val evt = event.pointed.text
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val text = evt.text?.toKStringFromUtf8()
                if (text != null) {
                    val event = TextInputEvent(timestamp, windowID, text)
                    this.OnTextInput(event)
                }
            }

            SDL_EVENT_FINGER_DOWN -> {
                val evt = event.pointed.tfinger
                val timestamp = Frame.Time
                val touchscreenID = evt.touchID
                val windowID = evt.windowID
                val fingerID = evt.fingerID
                val x = evt.x
                val y = evt.y
                val pressure = evt.pressure
                val event = TouchEvent(timestamp, windowID, fingerID, TouchState.Begin, Pair(x, y), Pair(0f, 0f), pressure)
            }
            SDL_EVENT_FINGER_MOTION -> {
                val evt = event.pointed.tfinger
                val timestamp = Frame.Time
                val touchscreenID = evt.touchID
                val windowID = evt.windowID
                val fingerID = evt.fingerID
                val x = evt.x
                val y = evt.y
                val deltaX = evt.dx
                val deltaY = evt.dy
                val pressure = evt.pressure
                val event = TouchEvent(timestamp, windowID, fingerID, TouchState.Changed, Pair(x, y), Pair(deltaX, deltaY), pressure)
            }
            SDL_EVENT_FINGER_UP -> {
                val evt = event.pointed.tfinger
                val timestamp = Frame.Time
                val touchscreenID = evt.touchID
                val windowID = evt.windowID
                val fingerID = evt.fingerID
                val x = evt.x
                val y = evt.y
                val pressure = evt.pressure
                val event = TouchEvent(timestamp, windowID, fingerID, TouchState.End, Pair(x, y), Pair(0f, 0f), pressure)
            }
            SDL_EVENT_FINGER_CANCELED -> {
                val evt = event.pointed.tfinger
                val timestamp = Frame.Time
                val touchscreenID = evt.touchID
                val windowID = evt.windowID
                val fingerID = evt.fingerID
                val x = evt.x
                val y = evt.y
                val pressure = evt.pressure
                val event = TouchEvent(timestamp, windowID, fingerID, TouchState.Canceled, Pair(x, y), Pair(0f, 0f), pressure)
            }

            SDL_EVENT_PINCH_BEGIN -> {
                val evt = event.pointed.pinch
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val zoom = evt.scale
                val event = ZoomEvent(timestamp, windowID, ZoomState.Begin, zoom)
            }
            SDL_EVENT_PINCH_UPDATE -> {
                val evt = event.pointed.pinch
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val zoom = evt.scale
                val event = ZoomEvent(timestamp, windowID, ZoomState.Changed, zoom)
            }
            SDL_EVENT_PINCH_END -> {
                val evt = event.pointed.pinch
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val zoom = evt.scale
                val event = ZoomEvent(timestamp, windowID, ZoomState.End, zoom)
            }

            SDL_EVENT_MOUSE_MOTION -> {
                val evt = event.pointed.motion
                val timestamp = Frame.Time
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
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val x = evt.x
                val y = evt.y
                val button = MouseButton.FromNativeValue(evt.button.toInt())
                val isPressed = evt.down
                val clickCount = evt.clicks.toInt()
                if (button != null) {
                    val event = MouseButtonActionEvent(timestamp, windowID, button, isPressed, clickCount, Pair(x, y))
                    this.OnMouseButtonAction(event)
                    this.Mouse.OnButtonAction?.invoke(event)
                }
            }
            SDL_EVENT_MOUSE_WHEEL -> {
                val evt = event.pointed.wheel
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val x = evt.mouse_x
                val y = evt.mouse_y
                val scrollX: Float
                val scrollY: Float
                val integerScrollX: Int
                val integerScrollY: Int
                if (evt.direction == SDL_MouseWheelDirection.SDL_MOUSEWHEEL_NORMAL) {
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
                val event = MouseWheelScrollEvent(timestamp, windowID, Pair(scrollX, scrollY), Pair(integerScrollX, integerScrollY), Pair(x, y))
                this.OnMouseWheelScroll(event)
                this.Mouse.OnWheelScroll?.invoke(event)
            }

            SDL_EVENT_KEY_DOWN, SDL_EVENT_KEY_UP -> {
                val evt = event.pointed.key
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val key = KeyboardKey.FromNativeValue(evt.scancode)
                val isPressed = evt.down
                val isRepeated = evt.repeat
                if (key != null) {
                    val event = KeyboardKeyActionEvent(timestamp, windowID, key, isPressed, isRepeated)
                    this.OnKeyboardKeyAction(event)
                    this.Keyboard.OnKeyAction?.invoke(event)
                }
            }

            SDL_EVENT_GAMEPAD_ADDED -> {
                val evt = event.pointed.gdevice
                val joystickID = evt.which
                val playerIndex = SDL_GetGamepadPlayerIndexForID(joystickID).SDL_CheckError()
//                if (playerIndex != -1) {
//                    // TODO
//                }
                val playerGamepad = this.Gamepads.getOrNull(playerIndex)
                if (playerGamepad != null) {
                    playerGamepad.NativeGamepad = SDL_OpenGamepad(joystickID).SDL_CheckError()
                }
            }
            SDL_EVENT_GAMEPAD_REMOVED -> {
                val evt = event.pointed.gdevice
                val joystickID = evt.which
                val playerIndex = SDL_GetGamepadPlayerIndexForID(joystickID).SDL_CheckError()
                val playerGamepad = this.Gamepads.getOrNull(playerIndex)
                if (playerGamepad != null) {
                    SDL_CloseGamepad(playerGamepad.NativeGamepad).SDL_CheckError()
                    playerGamepad.NativeGamepad = null
                }
            }
            SDL_EVENT_GAMEPAD_BUTTON_DOWN, SDL_EVENT_GAMEPAD_BUTTON_UP -> {
                val evt = event.pointed.gbutton
                val timestamp = Frame.Time
                val joystickID = evt.which
                val playerIndex = SDL_GetGamepadPlayerIndexForID(joystickID).SDL_CheckError()
                val playerGamepad = this.Gamepads.getOrNull(playerIndex)
                val button = GamepadButton.FromNativeValue(evt.button.toInt())
                val isPressed = evt.down
                if (playerGamepad != null) {
                    if (button != null) {
                        val event = GamepadButtonActionEvent(timestamp, playerIndex, button, isPressed)
                        this.OnGamepadButtonAction(event)
                        playerGamepad.OnButtonAction?.invoke(event)
                    }
                }
            }
            SDL_EVENT_GAMEPAD_AXIS_MOTION -> {
                val evt = event.pointed.gaxis
                val timestamp = Frame.Time
                val joystickID = evt.which
                val playerIndex = SDL_GetGamepadPlayerIndexForID(joystickID).SDL_CheckError()
                val playerGamepad = this.Gamepads.getOrNull(playerIndex)
                val axis = GamepadAxis.FromNativeValue(evt.axis.toInt())
                val value = evt.value.let {
                    Math.Lerp(-1f, 1f, Math.InverseLerp(SDL_JOYSTICK_AXIS_MIN.toFloat(), SDL_JOYSTICK_AXIS_MAX.toFloat(), it.toFloat()))
                }
                if (playerGamepad != null) {
                    if (axis != null) {
                        val event = GamepadAxisChangeEvent(timestamp, playerIndex, axis, value)
                        this.OnGamepadAxisChange(event)
                        playerGamepad.OnAxisChange?.invoke(event)
                    }
                }
            }
        }
    }

    internal fun OnDrawInternal() = this.OnDraw()
    protected abstract fun OnDraw()

    protected abstract fun OnMouseFocus(event: MouseFocusEvent)
    protected abstract fun OnKeyboardFocus(event: KeyboardFocusEvent)
    protected abstract fun OnTextInput(event: TextInputEvent)

    protected abstract fun OnMouseMove(event: MouseMoveEvent)
    protected abstract fun OnMouseButtonAction(event: MouseButtonActionEvent)
    protected abstract fun OnMouseWheelScroll(event: MouseWheelScrollEvent)

    protected abstract fun OnKeyboardKeyAction(event: KeyboardKeyActionEvent)

    protected abstract fun OnGamepadButtonAction(event: GamepadButtonActionEvent)
    protected abstract fun OnGamepadAxisChange(event: GamepadAxisChangeEvent)

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

public class TextInputEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val Text: String,
)
