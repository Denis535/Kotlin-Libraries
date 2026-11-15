package com.denis535.game_engine_pro

import com.denis535.game_engine_pro.display.*
import com.denis535.game_engine_pro.input.*
import kotlinx.cinterop.*
import sdl.*

@OptIn(ExperimentalForeignApi::class)
internal fun CursorStyle.ToNativeValue(): SDL_SystemCursor {
    return when (this) {
        CursorStyle.Arrow -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_DEFAULT
        CursorStyle.Text -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_TEXT
        CursorStyle.Pointer -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_POINTER
        CursorStyle.Crosshair -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_CROSSHAIR
        CursorStyle.Progress -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_PROGRESS
        CursorStyle.Wait -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_WAIT
        CursorStyle.NotAllowed -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_NOT_ALLOWED

        CursorStyle.Move -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_MOVE

        CursorStyle.SingleArrowResize_N -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_N_RESIZE
        CursorStyle.SingleArrowResize_S -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_S_RESIZE
        CursorStyle.SingleArrowResize_W -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_W_RESIZE
        CursorStyle.SingleArrowResize_E -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_E_RESIZE

        CursorStyle.SingleArrowResize_N_W -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_NW_RESIZE
        CursorStyle.SingleArrowResize_N_E -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_NE_RESIZE
        CursorStyle.SingleArrowResize_S_W -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_SW_RESIZE
        CursorStyle.SingleArrowResize_S_E -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_SE_RESIZE

        CursorStyle.DoubleArrowResize_N_S -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_NS_RESIZE
        CursorStyle.DoubleArrowResize_W_E -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_EW_RESIZE

        CursorStyle.DoubleArrowResize_NW_SE -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_NWSE_RESIZE
        CursorStyle.DoubleArrowResize_NE_SW -> SDL_SystemCursor.SDL_SYSTEM_CURSOR_NESW_RESIZE
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun CursorStyle.Companion.FromNativeValue(value: SDL_SystemCursor): CursorStyle? {
    return when (value) {
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_DEFAULT -> CursorStyle.Arrow
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_TEXT -> CursorStyle.Text
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_POINTER -> CursorStyle.Pointer
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_CROSSHAIR -> CursorStyle.Crosshair
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_PROGRESS -> CursorStyle.Progress
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_WAIT -> CursorStyle.Wait
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_NOT_ALLOWED -> CursorStyle.NotAllowed

        SDL_SystemCursor.SDL_SYSTEM_CURSOR_MOVE -> CursorStyle.Move

        SDL_SystemCursor.SDL_SYSTEM_CURSOR_N_RESIZE -> CursorStyle.SingleArrowResize_N
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_S_RESIZE -> CursorStyle.SingleArrowResize_S
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_W_RESIZE -> CursorStyle.SingleArrowResize_W
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_E_RESIZE -> CursorStyle.SingleArrowResize_E

        SDL_SystemCursor.SDL_SYSTEM_CURSOR_NW_RESIZE -> CursorStyle.SingleArrowResize_N_W
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_NE_RESIZE -> CursorStyle.SingleArrowResize_N_E
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_SW_RESIZE -> CursorStyle.SingleArrowResize_S_W
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_SE_RESIZE -> CursorStyle.SingleArrowResize_S_E

        SDL_SystemCursor.SDL_SYSTEM_CURSOR_NS_RESIZE -> CursorStyle.DoubleArrowResize_N_S
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_EW_RESIZE -> CursorStyle.DoubleArrowResize_W_E

        SDL_SystemCursor.SDL_SYSTEM_CURSOR_NWSE_RESIZE -> CursorStyle.DoubleArrowResize_NW_SE
        SDL_SystemCursor.SDL_SYSTEM_CURSOR_NESW_RESIZE -> CursorStyle.DoubleArrowResize_NE_SW

        else -> null
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun MouseButton.ToNativeValue(): Int {
    return when (this) {
        MouseButton.Left -> SDL_BUTTON_LEFT
        MouseButton.Right -> SDL_BUTTON_RIGHT
        MouseButton.Middle -> SDL_BUTTON_MIDDLE
        MouseButton.X1 -> SDL_BUTTON_X1
        MouseButton.X2 -> SDL_BUTTON_X2
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun MouseButton.ToNativeMask(): UInt {
    return when (this) {
        MouseButton.Left -> SDL_BUTTON_LMASK
        MouseButton.Right -> SDL_BUTTON_RMASK
        MouseButton.Middle -> SDL_BUTTON_MMASK
        MouseButton.X1 -> SDL_BUTTON_X1MASK
        MouseButton.X2 -> SDL_BUTTON_X2MASK
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun KeyboardKey.ToNativeValue(): SDL_Scancode {
    return when (this) {
        KeyboardKey.Letter_A -> SDL_SCANCODE_A
        KeyboardKey.Letter_B -> SDL_SCANCODE_B
        KeyboardKey.Letter_C -> SDL_SCANCODE_C
        KeyboardKey.Letter_D -> SDL_SCANCODE_D
        KeyboardKey.Letter_E -> SDL_SCANCODE_E
        KeyboardKey.Letter_F -> SDL_SCANCODE_F
        KeyboardKey.Letter_G -> SDL_SCANCODE_G
        KeyboardKey.Letter_H -> SDL_SCANCODE_H
        KeyboardKey.Letter_I -> SDL_SCANCODE_I
        KeyboardKey.Letter_J -> SDL_SCANCODE_J
        KeyboardKey.Letter_K -> SDL_SCANCODE_K
        KeyboardKey.Letter_L -> SDL_SCANCODE_L
        KeyboardKey.Letter_M -> SDL_SCANCODE_M
        KeyboardKey.Letter_N -> SDL_SCANCODE_N
        KeyboardKey.Letter_O -> SDL_SCANCODE_O
        KeyboardKey.Letter_P -> SDL_SCANCODE_P
        KeyboardKey.Letter_Q -> SDL_SCANCODE_Q
        KeyboardKey.Letter_R -> SDL_SCANCODE_R
        KeyboardKey.Letter_S -> SDL_SCANCODE_S
        KeyboardKey.Letter_T -> SDL_SCANCODE_T
        KeyboardKey.Letter_U -> SDL_SCANCODE_U
        KeyboardKey.Letter_V -> SDL_SCANCODE_V
        KeyboardKey.Letter_W -> SDL_SCANCODE_W
        KeyboardKey.Letter_X -> SDL_SCANCODE_X
        KeyboardKey.Letter_Y -> SDL_SCANCODE_Y
        KeyboardKey.Letter_Z -> SDL_SCANCODE_Z

        KeyboardKey.Digit_0 -> SDL_SCANCODE_0
        KeyboardKey.Digit_1 -> SDL_SCANCODE_1
        KeyboardKey.Digit_2 -> SDL_SCANCODE_2
        KeyboardKey.Digit_3 -> SDL_SCANCODE_3
        KeyboardKey.Digit_4 -> SDL_SCANCODE_4
        KeyboardKey.Digit_5 -> SDL_SCANCODE_5
        KeyboardKey.Digit_6 -> SDL_SCANCODE_6
        KeyboardKey.Digit_7 -> SDL_SCANCODE_7
        KeyboardKey.Digit_8 -> SDL_SCANCODE_8
        KeyboardKey.Digit_9 -> SDL_SCANCODE_9

        KeyboardKey.Up -> SDL_SCANCODE_UP
        KeyboardKey.Down -> SDL_SCANCODE_DOWN
        KeyboardKey.Left -> SDL_SCANCODE_LEFT
        KeyboardKey.Right -> SDL_SCANCODE_RIGHT

        KeyboardKey.Space -> SDL_SCANCODE_SPACE
        KeyboardKey.Backspace -> SDL_SCANCODE_BACKSPACE
        KeyboardKey.Delete -> SDL_SCANCODE_DELETE
        KeyboardKey.Enter -> SDL_SCANCODE_RETURN
        KeyboardKey.Tab -> SDL_SCANCODE_TAB
        KeyboardKey.Escape -> SDL_SCANCODE_ESCAPE

        KeyboardKey.LeftAlt -> SDL_SCANCODE_LALT
        KeyboardKey.LeftControl -> SDL_SCANCODE_LCTRL
        KeyboardKey.LeftShift -> SDL_SCANCODE_LSHIFT

        KeyboardKey.RightAlt -> SDL_SCANCODE_RALT
        KeyboardKey.RightControl -> SDL_SCANCODE_RCTRL
        KeyboardKey.RightShift -> SDL_SCANCODE_RSHIFT

        KeyboardKey.CapsLock -> SDL_SCANCODE_CAPSLOCK
        KeyboardKey.NumLock -> SDL_SCANCODE_NUMLOCKCLEAR

        KeyboardKey.F1 -> SDL_SCANCODE_F1
        KeyboardKey.F2 -> SDL_SCANCODE_F2
        KeyboardKey.F3 -> SDL_SCANCODE_F3
        KeyboardKey.F4 -> SDL_SCANCODE_F4
        KeyboardKey.F5 -> SDL_SCANCODE_F5
        KeyboardKey.F6 -> SDL_SCANCODE_F6
        KeyboardKey.F7 -> SDL_SCANCODE_F7
        KeyboardKey.F8 -> SDL_SCANCODE_F8
        KeyboardKey.F9 -> SDL_SCANCODE_F9
        KeyboardKey.F10 -> SDL_SCANCODE_F10
        KeyboardKey.F11 -> SDL_SCANCODE_F11
        KeyboardKey.F12 -> SDL_SCANCODE_F12

        KeyboardKey.Keypad_0 -> SDL_SCANCODE_KP_0
        KeyboardKey.Keypad_1 -> SDL_SCANCODE_KP_1
        KeyboardKey.Keypad_2 -> SDL_SCANCODE_KP_2
        KeyboardKey.Keypad_3 -> SDL_SCANCODE_KP_3
        KeyboardKey.Keypad_4 -> SDL_SCANCODE_KP_4
        KeyboardKey.Keypad_5 -> SDL_SCANCODE_KP_5
        KeyboardKey.Keypad_6 -> SDL_SCANCODE_KP_6
        KeyboardKey.Keypad_7 -> SDL_SCANCODE_KP_7
        KeyboardKey.Keypad_8 -> SDL_SCANCODE_KP_8
        KeyboardKey.Keypad_9 -> SDL_SCANCODE_KP_9
        KeyboardKey.Keypad_Decimal -> SDL_SCANCODE_KP_DECIMAL
        KeyboardKey.Keypad_Plus -> SDL_SCANCODE_KP_PLUS
        KeyboardKey.Keypad_Minus -> SDL_SCANCODE_KP_MINUS
        KeyboardKey.Keypad_Multiply -> SDL_SCANCODE_KP_MULTIPLY
        KeyboardKey.Keypad_Divide -> SDL_SCANCODE_KP_DIVIDE
        KeyboardKey.Keypad_Enter -> SDL_SCANCODE_KP_ENTER
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun GamepadButton.ToNativeValue(): Int {
    return when (this) {
        GamepadButton.Left -> SDL_GAMEPAD_BUTTON_DPAD_LEFT
        GamepadButton.Right -> SDL_GAMEPAD_BUTTON_DPAD_RIGHT
        GamepadButton.Up -> SDL_GAMEPAD_BUTTON_DPAD_UP
        GamepadButton.Down -> SDL_GAMEPAD_BUTTON_DPAD_DOWN

        GamepadButton.South -> SDL_GAMEPAD_BUTTON_SOUTH
        GamepadButton.East -> SDL_GAMEPAD_BUTTON_EAST
        GamepadButton.West -> SDL_GAMEPAD_BUTTON_WEST
        GamepadButton.North -> SDL_GAMEPAD_BUTTON_NORTH

        GamepadButton.LeftStick -> SDL_GAMEPAD_BUTTON_LEFT_STICK
        GamepadButton.RightStick -> SDL_GAMEPAD_BUTTON_RIGHT_STICK

        GamepadButton.LeftShoulder -> SDL_GAMEPAD_BUTTON_LEFT_SHOULDER
        GamepadButton.RightShoulder -> SDL_GAMEPAD_BUTTON_RIGHT_SHOULDER

        GamepadButton.LeftPaddle_1 -> SDL_GAMEPAD_BUTTON_LEFT_PADDLE1
        GamepadButton.RightPaddle_1 -> SDL_GAMEPAD_BUTTON_RIGHT_PADDLE1
        GamepadButton.LeftPaddle_2 -> SDL_GAMEPAD_BUTTON_LEFT_PADDLE2
        GamepadButton.RightPaddle_2 -> SDL_GAMEPAD_BUTTON_RIGHT_PADDLE2

        GamepadButton.Start -> SDL_GAMEPAD_BUTTON_START
        GamepadButton.Back -> SDL_GAMEPAD_BUTTON_BACK
        GamepadButton.Guide -> SDL_GAMEPAD_BUTTON_GUIDE

        GamepadButton.Touchpad -> SDL_GAMEPAD_BUTTON_TOUCHPAD

        GamepadButton.Misc_1 -> SDL_GAMEPAD_BUTTON_MISC1
        GamepadButton.Misc_2 -> SDL_GAMEPAD_BUTTON_MISC2
        GamepadButton.Misc_3 -> SDL_GAMEPAD_BUTTON_MISC3
        GamepadButton.Misc_4 -> SDL_GAMEPAD_BUTTON_MISC4
        GamepadButton.Misc_5 -> SDL_GAMEPAD_BUTTON_MISC5
        GamepadButton.Misc_6 -> SDL_GAMEPAD_BUTTON_MISC6
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun GamepadAxis.ToNativeValue(): Int {
    return when (this) {
        GamepadAxis.LeftX -> SDL_GAMEPAD_AXIS_LEFTX
        GamepadAxis.LeftY -> SDL_GAMEPAD_AXIS_LEFTY
        GamepadAxis.RightX -> SDL_GAMEPAD_AXIS_RIGHTX
        GamepadAxis.RightY -> SDL_GAMEPAD_AXIS_RIGHTY
        GamepadAxis.LeftTrigger -> SDL_GAMEPAD_AXIS_LEFT_TRIGGER
        GamepadAxis.RightTrigger -> SDL_GAMEPAD_AXIS_RIGHT_TRIGGER
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun MouseButton.Companion.FromNativeValue(value: Int): MouseButton? {
    return when (value) {
        SDL_BUTTON_LEFT -> MouseButton.Left
        SDL_BUTTON_RIGHT -> MouseButton.Right
        SDL_BUTTON_MIDDLE -> MouseButton.Middle
        SDL_BUTTON_X1 -> MouseButton.X1
        SDL_BUTTON_X2 -> MouseButton.X2
        else -> null
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun KeyboardKey.Companion.FromNativeValue(value: SDL_Scancode): KeyboardKey? {
    return when (value) {
        SDL_SCANCODE_A -> KeyboardKey.Letter_A
        SDL_SCANCODE_B -> KeyboardKey.Letter_B
        SDL_SCANCODE_C -> KeyboardKey.Letter_C
        SDL_SCANCODE_D -> KeyboardKey.Letter_D
        SDL_SCANCODE_E -> KeyboardKey.Letter_E
        SDL_SCANCODE_F -> KeyboardKey.Letter_F
        SDL_SCANCODE_G -> KeyboardKey.Letter_G
        SDL_SCANCODE_H -> KeyboardKey.Letter_H
        SDL_SCANCODE_I -> KeyboardKey.Letter_I
        SDL_SCANCODE_J -> KeyboardKey.Letter_J
        SDL_SCANCODE_K -> KeyboardKey.Letter_K
        SDL_SCANCODE_L -> KeyboardKey.Letter_L
        SDL_SCANCODE_M -> KeyboardKey.Letter_M
        SDL_SCANCODE_N -> KeyboardKey.Letter_N
        SDL_SCANCODE_O -> KeyboardKey.Letter_O
        SDL_SCANCODE_P -> KeyboardKey.Letter_P
        SDL_SCANCODE_Q -> KeyboardKey.Letter_Q
        SDL_SCANCODE_R -> KeyboardKey.Letter_R
        SDL_SCANCODE_S -> KeyboardKey.Letter_S
        SDL_SCANCODE_T -> KeyboardKey.Letter_T
        SDL_SCANCODE_U -> KeyboardKey.Letter_U
        SDL_SCANCODE_V -> KeyboardKey.Letter_V
        SDL_SCANCODE_W -> KeyboardKey.Letter_W
        SDL_SCANCODE_X -> KeyboardKey.Letter_X
        SDL_SCANCODE_Y -> KeyboardKey.Letter_Y
        SDL_SCANCODE_Z -> KeyboardKey.Letter_Z

        SDL_SCANCODE_0 -> KeyboardKey.Digit_0
        SDL_SCANCODE_1 -> KeyboardKey.Digit_1
        SDL_SCANCODE_2 -> KeyboardKey.Digit_2
        SDL_SCANCODE_3 -> KeyboardKey.Digit_3
        SDL_SCANCODE_4 -> KeyboardKey.Digit_4
        SDL_SCANCODE_5 -> KeyboardKey.Digit_5
        SDL_SCANCODE_6 -> KeyboardKey.Digit_6
        SDL_SCANCODE_7 -> KeyboardKey.Digit_7
        SDL_SCANCODE_8 -> KeyboardKey.Digit_8
        SDL_SCANCODE_9 -> KeyboardKey.Digit_9

        SDL_SCANCODE_UP -> KeyboardKey.Up
        SDL_SCANCODE_DOWN -> KeyboardKey.Down
        SDL_SCANCODE_LEFT -> KeyboardKey.Left
        SDL_SCANCODE_RIGHT -> KeyboardKey.Right

        SDL_SCANCODE_SPACE -> KeyboardKey.Space
        SDL_SCANCODE_BACKSPACE -> KeyboardKey.Backspace
        SDL_SCANCODE_DELETE -> KeyboardKey.Delete
        SDL_SCANCODE_RETURN -> KeyboardKey.Enter
        SDL_SCANCODE_TAB -> KeyboardKey.Tab
        SDL_SCANCODE_ESCAPE -> KeyboardKey.Escape

        SDL_SCANCODE_LALT -> KeyboardKey.LeftAlt
        SDL_SCANCODE_LCTRL -> KeyboardKey.LeftControl
        SDL_SCANCODE_LSHIFT -> KeyboardKey.LeftShift

        SDL_SCANCODE_RALT -> KeyboardKey.RightAlt
        SDL_SCANCODE_RCTRL -> KeyboardKey.RightControl
        SDL_SCANCODE_RSHIFT -> KeyboardKey.RightShift

        SDL_SCANCODE_CAPSLOCK -> KeyboardKey.CapsLock
        SDL_SCANCODE_NUMLOCKCLEAR -> KeyboardKey.NumLock

        SDL_SCANCODE_F1 -> KeyboardKey.F1
        SDL_SCANCODE_F2 -> KeyboardKey.F2
        SDL_SCANCODE_F3 -> KeyboardKey.F3
        SDL_SCANCODE_F4 -> KeyboardKey.F4
        SDL_SCANCODE_F5 -> KeyboardKey.F5
        SDL_SCANCODE_F6 -> KeyboardKey.F6
        SDL_SCANCODE_F7 -> KeyboardKey.F7
        SDL_SCANCODE_F8 -> KeyboardKey.F8
        SDL_SCANCODE_F9 -> KeyboardKey.F9
        SDL_SCANCODE_F10 -> KeyboardKey.F10
        SDL_SCANCODE_F11 -> KeyboardKey.F11
        SDL_SCANCODE_F12 -> KeyboardKey.F12

        SDL_SCANCODE_KP_0 -> KeyboardKey.Keypad_0
        SDL_SCANCODE_KP_1 -> KeyboardKey.Keypad_1
        SDL_SCANCODE_KP_2 -> KeyboardKey.Keypad_2
        SDL_SCANCODE_KP_3 -> KeyboardKey.Keypad_3
        SDL_SCANCODE_KP_4 -> KeyboardKey.Keypad_4
        SDL_SCANCODE_KP_5 -> KeyboardKey.Keypad_5
        SDL_SCANCODE_KP_6 -> KeyboardKey.Keypad_6
        SDL_SCANCODE_KP_7 -> KeyboardKey.Keypad_7
        SDL_SCANCODE_KP_8 -> KeyboardKey.Keypad_8
        SDL_SCANCODE_KP_9 -> KeyboardKey.Keypad_9
        SDL_SCANCODE_KP_DECIMAL -> KeyboardKey.Keypad_Decimal
        SDL_SCANCODE_KP_PLUS -> KeyboardKey.Keypad_Plus
        SDL_SCANCODE_KP_MINUS -> KeyboardKey.Keypad_Minus
        SDL_SCANCODE_KP_MULTIPLY -> KeyboardKey.Keypad_Multiply
        SDL_SCANCODE_KP_DIVIDE -> KeyboardKey.Keypad_Divide
        SDL_SCANCODE_KP_ENTER -> KeyboardKey.Keypad_Enter

        else -> null
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun GamepadButton.Companion.FromNativeValue(value: Int): GamepadButton? {
    return when (value) {
        SDL_GAMEPAD_BUTTON_DPAD_LEFT -> GamepadButton.Left
        SDL_GAMEPAD_BUTTON_DPAD_RIGHT -> GamepadButton.Right
        SDL_GAMEPAD_BUTTON_DPAD_UP -> GamepadButton.Up
        SDL_GAMEPAD_BUTTON_DPAD_DOWN -> GamepadButton.Down

        SDL_GAMEPAD_BUTTON_SOUTH -> GamepadButton.South
        SDL_GAMEPAD_BUTTON_EAST -> GamepadButton.East
        SDL_GAMEPAD_BUTTON_WEST -> GamepadButton.West
        SDL_GAMEPAD_BUTTON_NORTH -> GamepadButton.North

        SDL_GAMEPAD_BUTTON_LEFT_STICK -> GamepadButton.LeftStick
        SDL_GAMEPAD_BUTTON_RIGHT_STICK -> GamepadButton.RightStick

        SDL_GAMEPAD_BUTTON_LEFT_SHOULDER -> GamepadButton.LeftShoulder
        SDL_GAMEPAD_BUTTON_RIGHT_SHOULDER -> GamepadButton.RightShoulder

        SDL_GAMEPAD_BUTTON_LEFT_PADDLE1 -> GamepadButton.LeftPaddle_1
        SDL_GAMEPAD_BUTTON_RIGHT_PADDLE1 -> GamepadButton.RightPaddle_1
        SDL_GAMEPAD_BUTTON_LEFT_PADDLE2 -> GamepadButton.LeftPaddle_2
        SDL_GAMEPAD_BUTTON_RIGHT_PADDLE2 -> GamepadButton.RightPaddle_2

        SDL_GAMEPAD_BUTTON_START -> GamepadButton.Start
        SDL_GAMEPAD_BUTTON_BACK -> GamepadButton.Back
        SDL_GAMEPAD_BUTTON_GUIDE -> GamepadButton.Guide

        SDL_GAMEPAD_BUTTON_TOUCHPAD -> GamepadButton.Touchpad

        SDL_GAMEPAD_BUTTON_MISC1 -> GamepadButton.Misc_1
        SDL_GAMEPAD_BUTTON_MISC2 -> GamepadButton.Misc_2
        SDL_GAMEPAD_BUTTON_MISC3 -> GamepadButton.Misc_3
        SDL_GAMEPAD_BUTTON_MISC4 -> GamepadButton.Misc_4
        SDL_GAMEPAD_BUTTON_MISC5 -> GamepadButton.Misc_5
        SDL_GAMEPAD_BUTTON_MISC6 -> GamepadButton.Misc_6
        else -> null
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun GamepadAxis.Companion.FromNativeValue(value: Int): GamepadAxis? {
    return when (value) {
        SDL_GAMEPAD_AXIS_LEFTX -> GamepadAxis.LeftX
        SDL_GAMEPAD_AXIS_LEFTY -> GamepadAxis.LeftY
        SDL_GAMEPAD_AXIS_RIGHTX -> GamepadAxis.RightX
        SDL_GAMEPAD_AXIS_RIGHTY -> GamepadAxis.RightY
        SDL_GAMEPAD_AXIS_LEFT_TRIGGER -> GamepadAxis.LeftTrigger
        SDL_GAMEPAD_AXIS_RIGHT_TRIGGER -> GamepadAxis.RightTrigger
        else -> null
    }
}
