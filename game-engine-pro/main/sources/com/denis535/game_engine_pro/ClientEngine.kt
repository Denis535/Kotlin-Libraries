package com.denis535.game_engine_pro

import com.denis535.game_engine_pro.display.*
import com.denis535.game_engine_pro.input.*
import com.denis535.game_engine_pro.storage.*
import com.denis535.internal.sdl.*
import kotlinx.cinterop.*

public abstract class ClientEngine : Engine {

    public lateinit var Window: Window
        protected set
    public lateinit var Cursor: Cursor
        protected set
    public lateinit var Touchscreen: Touchscreen
        protected set
    public lateinit var Mouse: Mouse
        protected set
    public lateinit var Keyboard: Keyboard
        protected set
    public lateinit var Gamepads: List<Gamepad>
        protected set
    public var Content: Content? = null
        protected set
    public var Storage: Storage? = null
        protected set

    @OptIn(ExperimentalForeignApi::class)
    public constructor(manifest: Manifest) : super(manifest) {
        SDL_Init(SDL_INIT_VIDEO or SDL_INIT_SENSOR or SDL_INIT_GAMEPAD).SDL_CheckError()
    }

    public override fun close() {
        check(!this.IsClosed)
        check(!this.IsRunning)
        super.close()
    }

    protected override fun OnFrameBegin() {
        super.OnFrameBegin()
        this.Content?.Process()
    }

    @OptIn(ExperimentalForeignApi::class)
    internal override fun OnEvent(event: CPointer<SDL_Event>) {
        super.OnEvent(event)
        when (event.pointed.type) {
            // Window
            SDL_EVENT_WINDOW_SHOWN -> {
            }
            SDL_EVENT_WINDOW_HIDDEN -> {
            }
            SDL_EVENT_WINDOW_EXPOSED -> {
            }
            SDL_EVENT_WINDOW_MINIMIZED -> {
            }
            SDL_EVENT_WINDOW_MAXIMIZED -> {
            }
            SDL_EVENT_WINDOW_RESTORED -> {
            }
            SDL_EVENT_WINDOW_MOVED -> {
            }
            SDL_EVENT_WINDOW_RESIZED -> {
            }
            SDL_EVENT_WINDOW_PIXEL_SIZE_CHANGED -> {
            }
            SDL_EVENT_WINDOW_CLOSE_REQUESTED -> {
                this.IsRunning = false
            }

            // Window
            SDL_EVENT_WINDOW_MOUSE_ENTER -> {
                val evt = event.pointed.window
                val timestamp = Frame.Time
                val windowID = evt.windowID
                if (windowID == this.Window.NativeWindowID) {
                    val event = CursorEnterEvent(timestamp, windowID)
                    this.Window.OnCursorEnterCallback?.invoke(event)
                }
            }
            SDL_EVENT_WINDOW_MOUSE_LEAVE -> {
                val evt = event.pointed.window
                val timestamp = Frame.Time
                val windowID = evt.windowID
                if (windowID == this.Window.NativeWindowID) {
                    val event = CursorLeaveEvent(timestamp, windowID)
                    this.Window.OnCursorLeaveCallback?.invoke(event)
                }
            }

            // Window
            SDL_EVENT_WINDOW_FOCUS_GAINED -> {
                val evt = event.pointed.window
                val timestamp = Frame.Time
                val windowID = evt.windowID
                if (windowID == this.Window.NativeWindowID) {
                    val event = KeyboardFocusEvent(timestamp, windowID, true)
                    this.Window.OnKeyboardFocusCallback?.invoke(event)
                }
            }
            SDL_EVENT_WINDOW_FOCUS_LOST -> {
                val evt = event.pointed.window
                val timestamp = Frame.Time
                val windowID = evt.windowID
                if (windowID == this.Window.NativeWindowID) {
                    val event = KeyboardFocusEvent(timestamp, windowID, false)
                    this.Window.OnKeyboardFocusCallback?.invoke(event)
                }
            }

            // Window
            SDL_EVENT_PINCH_BEGIN -> {
                val evt = event.pointed.pinch
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val value = evt.scale
                if (windowID == this.Window.NativeWindowID) {
                    val event = ZoomEvent(timestamp, windowID, ZoomState.Begin, value)
                    this.Window.OnZoomCallback?.invoke(event)
                }
            }
            SDL_EVENT_PINCH_UPDATE -> {
                val evt = event.pointed.pinch
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val value = evt.scale
                if (windowID == this.Window.NativeWindowID) {
                    val event = ZoomEvent(timestamp, windowID, ZoomState.Changed, value)
                    this.Window.OnZoomCallback?.invoke(event)
                }
            }
            SDL_EVENT_PINCH_END -> {
                val evt = event.pointed.pinch
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val value = evt.scale
                if (windowID == this.Window.NativeWindowID) {
                    val event = ZoomEvent(timestamp, windowID, ZoomState.End, value)
                    this.Window.OnZoomCallback?.invoke(event)
                }
            }

            // Window
            SDL_EVENT_TEXT_INPUT -> {
                val evt = event.pointed.text
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val value = evt.text?.toKStringFromUtf8()
                if (windowID == this.Window.NativeWindowID) {
                    if (value != null) {
                        val event = TextEvent(timestamp, windowID, value)
                        this.Window.OnTextCallback?.invoke(event)
                    }
                }
            }

            // Touchscreen
            SDL_EVENT_FINGER_DOWN -> {
                val evt = event.pointed.tfinger
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val nativeDeviceID = evt.touchID
                val id = evt.fingerID
                val x = evt.x
                val y = evt.y
                val pressure = evt.pressure
                if (windowID == this.Window.NativeWindowID) {
                    if (nativeDeviceID == this.Touchscreen.NativeDeviceID) {
                        val event = TouchEvent(timestamp, windowID, nativeDeviceID, id, TouchState.Begin, Vector2(x, y), Vector2(0f, 0f), pressure)
                        this.Touchscreen.OnTouchCallback?.invoke(event)
                    }
                }
            }
            SDL_EVENT_FINGER_MOTION -> {
                val evt = event.pointed.tfinger
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val nativeDeviceID = evt.touchID
                val id = evt.fingerID
                val x = evt.x
                val y = evt.y
                val deltaX = evt.dx
                val deltaY = evt.dy
                val pressure = evt.pressure
                if (windowID == this.Window.NativeWindowID) {
                    if (nativeDeviceID == this.Touchscreen.NativeDeviceID) {
                        val event = TouchEvent(timestamp, windowID, nativeDeviceID, id, TouchState.Changed, Vector2(x, y), Vector2(deltaX, deltaY), pressure)
                        this.Touchscreen.OnTouchCallback?.invoke(event)
                    }
                }
            }
            SDL_EVENT_FINGER_UP -> {
                val evt = event.pointed.tfinger
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val nativeDeviceID = evt.touchID
                val id = evt.fingerID
                val x = evt.x
                val y = evt.y
                val pressure = evt.pressure
                if (windowID == this.Window.NativeWindowID) {
                    if (nativeDeviceID == this.Touchscreen.NativeDeviceID) {
                        val event = TouchEvent(timestamp, windowID, nativeDeviceID, id, TouchState.End, Vector2(x, y), Vector2(0f, 0f), pressure)
                        this.Touchscreen.OnTouchCallback?.invoke(event)
                    }
                }
            }
            SDL_EVENT_FINGER_CANCELED -> {
                val evt = event.pointed.tfinger
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val nativeDeviceID = evt.touchID
                val id = evt.fingerID
                val x = evt.x
                val y = evt.y
                val pressure = evt.pressure
                if (windowID == this.Window.NativeWindowID) {
                    if (nativeDeviceID == this.Touchscreen.NativeDeviceID) {
                        val event = TouchEvent(timestamp, windowID, nativeDeviceID, id, TouchState.Canceled, Vector2(x, y), Vector2(0f, 0f), pressure)
                        this.Touchscreen.OnTouchCallback?.invoke(event)
                    }
                }
            }

            // Mouse
            SDL_EVENT_MOUSE_MOTION -> {
                val evt = event.pointed.motion
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val nativeDeviceID = evt.which
                val x = evt.x
                val y = evt.y
                val deltaX = evt.xrel
                val deltaY = evt.yrel
                if (windowID == this.Window.NativeWindowID) {
                    val event = MouseMoveEvent(timestamp, windowID, nativeDeviceID, Vector2(x, y), Vector2(deltaX, deltaY))
                    this.Mouse.OnMoveCallback?.invoke(event)
                }
            }
            SDL_EVENT_MOUSE_BUTTON_DOWN, SDL_EVENT_MOUSE_BUTTON_UP -> {
                val evt = event.pointed.button
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val nativeDeviceID = evt.which
                val button = MouseButton.FromNativeValue(evt.button.toInt())
                val isPressed = evt.down
                val clickCount = evt.clicks.toInt()
                if (windowID == this.Window.NativeWindowID) {
                    if (button != null) {
                        val event = MouseButtonActionEvent(timestamp, windowID, nativeDeviceID, button, isPressed, clickCount)
                        this.Mouse.OnButtonActionCallback?.invoke(event)
                    }
                }
            }
            SDL_EVENT_MOUSE_WHEEL -> {
                val evt = event.pointed.wheel
                val timestamp = Frame.Time
                val windowID = evt.windowID
                val nativeDeviceID = evt.which
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
                    val event = MouseWheelScrollEvent(timestamp, windowID, nativeDeviceID, Vector2(scrollX, scrollY), Vector2I(integerScrollX, integerScrollY))
                    this.Mouse.OnWheelScrollCallback?.invoke(event)
                }
            }

            // Keyboard
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
                        this.Keyboard.OnKeyActionCallback?.invoke(event)
                    }
                }
            }

            // Gamepad
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

            // Gamepad
            SDL_EVENT_GAMEPAD_BUTTON_DOWN, SDL_EVENT_GAMEPAD_BUTTON_UP -> {
                val evt = event.pointed.gbutton
                val timestamp = Frame.Time
                val nativeDeviceID = evt.which
                val playerIndex = SDL_GetGamepadPlayerIndexForID(nativeDeviceID).SDL_CheckError()
                val playerGamepad = this.Gamepads.getOrNull(playerIndex)
                val button = GamepadButton.FromNativeValue(evt.button.toInt())
                val isPressed = evt.down
                if (playerGamepad != null) {
                    if (button != null) {
                        val event = GamepadButtonActionEvent(timestamp, nativeDeviceID, playerIndex, button, isPressed)
                        playerGamepad.OnButtonActionCallback?.invoke(event)
                    }
                }
            }
            SDL_EVENT_GAMEPAD_AXIS_MOTION -> {
                val evt = event.pointed.gaxis
                val timestamp = Frame.Time
                val nativeDeviceID = evt.which
                val playerIndex = SDL_GetGamepadPlayerIndexForID(nativeDeviceID).SDL_CheckError()
                val playerGamepad = this.Gamepads.getOrNull(playerIndex)
                val axis = GamepadAxis.FromNativeValue(evt.axis.toInt())
                val value = evt.value.let {
                    Math.Lerp(-1f, 1f, Math.InverseLerp(SDL_JOYSTICK_AXIS_MIN.toFloat(), SDL_JOYSTICK_AXIS_MAX.toFloat(), it.toFloat()))
                }
                if (playerGamepad != null) {
                    if (axis != null) {
                        val event = GamepadAxisActionEvent(timestamp, nativeDeviceID, playerIndex, axis, value)
                        playerGamepad.OnAxisActionCallback?.invoke(event)
                    }
                }
            }
        }
    }

    protected override fun OnFrameEnd() {
        super.OnFrameEnd()
    }

}
