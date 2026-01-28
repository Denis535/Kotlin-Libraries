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

    public val Touchscreen: Touchscreen
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
        this.Window.IsShown = true
        this.Cursor = Cursor()
        this.Touchscreen = Touchscreen()
        this.Mouse = Mouse()
        this.Keyboard = Keyboard()
        this.Gamepads = listOf(Gamepad(), Gamepad(), Gamepad(), Gamepad())
    }

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        this.Gamepads.asReversed().forEach { it.close() }
        this.Keyboard.close()
        this.Mouse.close()
        this.Touchscreen.close()
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
                if (windowID == this.Window.NativeWindowID) {
                    val event = MouseFocusEvent(timestamp, windowID, true)
                    this.OnFocus(event)
                }
            }
            SDL_EVENT_WINDOW_MOUSE_LEAVE -> {
                val evt = event.pointed.window
                val timestamp = Frame.Time
                val windowID = evt.windowID
                if (windowID == this.Window.NativeWindowID) {
                    val event = MouseFocusEvent(timestamp, windowID, false)
                    this.OnFocus(event)
                }
            }

            SDL_EVENT_WINDOW_FOCUS_GAINED -> {
                val evt = event.pointed.window
                val timestamp = Frame.Time
                val windowID = evt.windowID
                if (windowID == this.Window.NativeWindowID) {
                    val event = KeyboardFocusEvent(timestamp, windowID, true)
                    this.OnFocus(event)
                }
            }
            SDL_EVENT_WINDOW_FOCUS_LOST -> {
                val evt = event.pointed.window
                val timestamp = Frame.Time
                val windowID = evt.windowID
                if (windowID == this.Window.NativeWindowID) {
                    val event = KeyboardFocusEvent(timestamp, windowID, false)
                    this.OnFocus(event)
                }
            }

            SDL_EVENT_FINGER_DOWN -> {
                val evt = event.pointed.tfinger
                val nativeDeviceID = evt.touchID
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val id = evt.fingerID
                val x = evt.x
                val y = evt.y
                val pressure = evt.pressure
                if (nativeDeviceID == this.Touchscreen.NativeDeviceID) {
                    if (windowID == this.Window.NativeWindowID) {
                        val event = TouchEvent(nativeDeviceID, timestamp, windowID, id, TouchState.Begin, Pair(x, y), Pair(0f, 0f), pressure)
                        this.OnTouch(event)
                        this.Touchscreen.OnTouchCallback?.invoke(event)
                    }
                }
            }
            SDL_EVENT_FINGER_MOTION -> {
                val evt = event.pointed.tfinger
                val nativeDeviceID = evt.touchID
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val id = evt.fingerID
                val x = evt.x
                val y = evt.y
                val deltaX = evt.dx
                val deltaY = evt.dy
                val pressure = evt.pressure
                if (nativeDeviceID == this.Touchscreen.NativeDeviceID) {
                    if (windowID == this.Window.NativeWindowID) {
                        val event = TouchEvent(nativeDeviceID, timestamp, windowID, id, TouchState.Changed, Pair(x, y), Pair(deltaX, deltaY), pressure)
                        this.OnTouch(event)
                        this.Touchscreen.OnTouchCallback?.invoke(event)
                    }
                }
            }
            SDL_EVENT_FINGER_UP -> {
                val evt = event.pointed.tfinger
                val nativeDeviceID = evt.touchID
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val id = evt.fingerID
                val x = evt.x
                val y = evt.y
                val pressure = evt.pressure
                if (nativeDeviceID == this.Touchscreen.NativeDeviceID) {
                    if (windowID == this.Window.NativeWindowID) {
                        val event = TouchEvent(nativeDeviceID, timestamp, windowID, id, TouchState.End, Pair(x, y), Pair(0f, 0f), pressure)
                        this.OnTouch(event)
                        this.Touchscreen.OnTouchCallback?.invoke(event)
                    }
                }
            }
            SDL_EVENT_FINGER_CANCELED -> {
                val evt = event.pointed.tfinger
                val nativeDeviceID = evt.touchID
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val id = evt.fingerID
                val x = evt.x
                val y = evt.y
                val pressure = evt.pressure
                if (nativeDeviceID == this.Touchscreen.NativeDeviceID) {
                    if (windowID == this.Window.NativeWindowID) {
                        val event = TouchEvent(nativeDeviceID, timestamp, windowID, id, TouchState.Canceled, Pair(x, y), Pair(0f, 0f), pressure)
                        this.OnTouch(event)
                        this.Touchscreen.OnTouchCallback?.invoke(event)
                    }
                }
            }

            SDL_EVENT_PINCH_BEGIN -> {
                val evt = event.pointed.pinch
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val zoom = evt.scale
                if (windowID == this.Window.NativeWindowID) {
                    val event = ZoomEvent(timestamp, windowID, ZoomState.Begin, zoom)
                    this.OnZoom(event)
                }
            }
            SDL_EVENT_PINCH_UPDATE -> {
                val evt = event.pointed.pinch
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val zoom = evt.scale
                if (windowID == this.Window.NativeWindowID) {
                    val event = ZoomEvent(timestamp, windowID, ZoomState.Changed, zoom)
                    this.OnZoom(event)
                }
            }
            SDL_EVENT_PINCH_END -> {
                val evt = event.pointed.pinch
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val zoom = evt.scale
                if (windowID == this.Window.NativeWindowID) {
                    val event = ZoomEvent(timestamp, windowID, ZoomState.End, zoom)
                    this.OnZoom(event)
                }
            }

            SDL_EVENT_TEXT_INPUT -> {
                val evt = event.pointed.text
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val text = evt.text?.toKStringFromUtf8()
                if (windowID == this.Window.NativeWindowID) {
                    if (text != null) {
                        val event = TextEvent(timestamp, windowID, text)
                        this.OnText(event)
                    }
                }
            }

            SDL_EVENT_WINDOW_CLOSE_REQUESTED -> {
//                val evt = event.pointed.window
//                val timestamp = Frame.Time
//                val windowID = evt.windowID
                this.IsRunning = false
            }

            SDL_EVENT_MOUSE_MOTION -> {
                val evt = event.pointed.motion
                val nativeDeviceID = evt.which
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val x = evt.x
                val y = evt.y
                val deltaX = evt.xrel
                val deltaY = evt.yrel
                if (windowID == this.Window.NativeWindowID) {
                    val event = MouseMoveEvent(nativeDeviceID, timestamp, windowID, Pair(x, y), Pair(deltaX, deltaY))
                    this.OnMouseMove(event)
                    this.Mouse.OnMoveCallback?.invoke(event)
                }
            }
            SDL_EVENT_MOUSE_BUTTON_DOWN, SDL_EVENT_MOUSE_BUTTON_UP -> {
                val evt = event.pointed.button
                val nativeDeviceID = evt.which
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val button = MouseButton.FromNativeValue(evt.button.toInt())
                val isPressed = evt.down
                val clickCount = evt.clicks.toInt()
                if (windowID == this.Window.NativeWindowID) {
                    if (button != null) {
                        val event = MouseButtonActionEvent(nativeDeviceID, timestamp, windowID, button, isPressed, clickCount)
                        this.OnMouseButtonAction(event)
                        this.Mouse.OnButtonActionCallback?.invoke(event)
                    }
                }
            }
            SDL_EVENT_MOUSE_WHEEL -> {
                val evt = event.pointed.wheel
                val nativeDeviceID = evt.which
                val timestamp = Frame.Time
                val windowID = evt.windowID
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
                if (windowID == this.Window.NativeWindowID) {
                    val event = MouseWheelScrollEvent(nativeDeviceID, timestamp, windowID, Pair(scrollX, scrollY), Pair(integerScrollX, integerScrollY))
                    this.OnMouseWheelScroll(event)
                    this.Mouse.OnWheelScrollCallback?.invoke(event)
                }
            }

            SDL_EVENT_KEY_DOWN, SDL_EVENT_KEY_UP -> {
                val evt = event.pointed.key
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val key = KeyboardKey.FromNativeValue(evt.scancode)
                val isPressed = evt.down
                val isRepeated = evt.repeat
                if (windowID == this.Window.NativeWindowID) {
                    if (key != null) {
                        val event = KeyboardKeyActionEvent(timestamp, windowID, key, isPressed, isRepeated)
                        this.OnKeyboardKeyAction(event)
                        this.Keyboard.OnKeyActionCallback?.invoke(event)
                    }
                }
            }

            SDL_EVENT_GAMEPAD_ADDED -> {
                val evt = event.pointed.gdevice
                val nativeDeviceID = evt.which
                val playerIndex = SDL_GetGamepadPlayerIndexForID(nativeDeviceID).SDL_CheckError()
//                if (playerIndex != -1) {
//                    // TODO
//                }
                val playerGamepad = this.Gamepads.getOrNull(playerIndex)
                if (playerGamepad != null) {
                    playerGamepad.NativeDevice = SDL_OpenGamepad(nativeDeviceID).SDL_CheckError()
                }
            }
            SDL_EVENT_GAMEPAD_REMOVED -> {
                val evt = event.pointed.gdevice
                val nativeDeviceID = evt.which
                val playerIndex = SDL_GetGamepadPlayerIndexForID(nativeDeviceID).SDL_CheckError()
                val playerGamepad = this.Gamepads.getOrNull(playerIndex)
                if (playerGamepad != null) {
                    SDL_CloseGamepad(playerGamepad.NativeDevice).SDL_CheckError()
                    playerGamepad.NativeDevice = null
                }
            }
            SDL_EVENT_GAMEPAD_BUTTON_DOWN, SDL_EVENT_GAMEPAD_BUTTON_UP -> {
                val evt = event.pointed.gbutton
                val nativeDeviceID = evt.which
                val timestamp = Frame.Time
                val playerIndex = SDL_GetGamepadPlayerIndexForID(nativeDeviceID).SDL_CheckError()
                val playerGamepad = this.Gamepads.getOrNull(playerIndex)
                val button = GamepadButton.FromNativeValue(evt.button.toInt())
                val isPressed = evt.down
                if (playerGamepad != null) {
                    if (button != null) {
                        val event = GamepadButtonActionEvent(nativeDeviceID, timestamp, playerIndex, button, isPressed)
                        this.OnGamepadButtonAction(event)
                        playerGamepad.OnButtonActionCallback?.invoke(event)
                    }
                }
            }
            SDL_EVENT_GAMEPAD_AXIS_MOTION -> {
                val evt = event.pointed.gaxis
                val nativeDeviceID = evt.which
                val timestamp = Frame.Time
                val playerIndex = SDL_GetGamepadPlayerIndexForID(nativeDeviceID).SDL_CheckError()
                val playerGamepad = this.Gamepads.getOrNull(playerIndex)
                val axis = GamepadAxis.FromNativeValue(evt.axis.toInt())
                val value = evt.value.let {
                    Math.Lerp(-1f, 1f, Math.InverseLerp(SDL_JOYSTICK_AXIS_MIN.toFloat(), SDL_JOYSTICK_AXIS_MAX.toFloat(), it.toFloat()))
                }
                if (playerGamepad != null) {
                    if (axis != null) {
                        val event = GamepadAxisActionEvent(nativeDeviceID, timestamp, playerIndex, axis, value)
                        this.OnGamepadAxisAction(event)
                        playerGamepad.OnAxisActionCallback?.invoke(event)
                    }
                }
            }
        }
    }

    internal fun OnDrawInternal() = this.OnDraw()
    protected abstract fun OnDraw()

    protected abstract fun OnFocus(event: MouseFocusEvent)
    protected abstract fun OnFocus(event: KeyboardFocusEvent)
    protected abstract fun OnTouch(event: TouchEvent)
    protected abstract fun OnZoom(event: ZoomEvent)
    protected abstract fun OnText(event: TextEvent)

    protected abstract fun OnMouseMove(event: MouseMoveEvent)
    protected abstract fun OnMouseButtonAction(event: MouseButtonActionEvent)
    protected abstract fun OnMouseWheelScroll(event: MouseWheelScrollEvent)
    protected abstract fun OnKeyboardKeyAction(event: KeyboardKeyActionEvent)
    protected abstract fun OnGamepadButtonAction(event: GamepadButtonActionEvent)
    protected abstract fun OnGamepadAxisAction(event: GamepadAxisActionEvent)

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
