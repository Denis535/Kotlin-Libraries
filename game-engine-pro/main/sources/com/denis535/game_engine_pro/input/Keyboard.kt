package com.denis535.game_engine_pro.input

import com.denis535.game_engine_pro.*
import com.denis535.internal.sdl.*
import kotlinx.cinterop.*

public class Keyboard : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    public var OnKeyActionCallback: ((KeyboardKeyActionEvent) -> Unit)? = null
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

    public constructor()

    public override fun close() {
        check(!this.IsClosed)
        this.IsClosed = true
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun IsKeyPressed(key: KeyboardKey): Boolean {
        check(!this.IsClosed)
        val state = SDL_GetKeyboardState(null).SDL_CheckError()
        return state!![key.ToNativeValue().toInt()].value
    }

}

public data class KeyboardKeyActionEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val Key: KeyboardKey,
    public val IsPressed: Boolean,
    public val IsRepeated: Boolean,
)

public enum class KeyboardKey {
    Letter_A,
    Letter_B,
    Letter_C,
    Letter_D,
    Letter_E,
    Letter_F,
    Letter_G,
    Letter_H,
    Letter_I,
    Letter_J,
    Letter_K,
    Letter_L,
    Letter_M,
    Letter_N,
    Letter_O,
    Letter_P,
    Letter_Q,
    Letter_R,
    Letter_S,
    Letter_T,
    Letter_U,
    Letter_V,
    Letter_W,
    Letter_X,
    Letter_Y,
    Letter_Z,

    Digit_0,
    Digit_1,
    Digit_2,
    Digit_3,
    Digit_4,
    Digit_5,
    Digit_6,
    Digit_7,
    Digit_8,
    Digit_9,

    Up,
    Down,
    Left,
    Right,

    Space,
    Backspace,
    Delete,
    Enter,
    Tab,
    Escape,

    LeftAlt,
    LeftControl,
    LeftShift,

    RightAlt,
    RightControl,
    RightShift,

    CapsLock,
    NumLock,

    F1,
    F2,
    F3,
    F4,
    F5,
    F6,
    F7,
    F8,
    F9,
    F10,
    F11,
    F12,

    Keypad_0,
    Keypad_1,
    Keypad_2,
    Keypad_3,
    Keypad_4,
    Keypad_5,
    Keypad_6,
    Keypad_7,
    Keypad_8,
    Keypad_9,
    Keypad_Decimal,
    Keypad_Plus,
    Keypad_Minus,
    Keypad_Multiply,
    Keypad_Divide,
    Keypad_Enter;

    public companion object
}
