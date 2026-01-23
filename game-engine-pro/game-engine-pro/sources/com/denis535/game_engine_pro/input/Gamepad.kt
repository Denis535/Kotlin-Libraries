package com.denis535.game_engine_pro.input

import com.denis535.game_engine_pro.math.*
import com.denis535.sdl.*
import kotlinx.cinterop.*

public class Gamepad : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    public val PlayerIndex: Int
        get() {
            check(!this.IsClosed)
            return field
        }

    @OptIn(ExperimentalForeignApi::class)
    private val Native: CPointer<cnames.structs.SDL_Gamepad>?
        get() {
            check(!this.IsClosed)
            return SDL_GetGamepadFromID(this.ID).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    private val ID: UInt
        get() {
            check(!this.IsClosed)
            val joystick = SDL_GetJoystickFromPlayerIndex(this.PlayerIndex).also { SDL.ThrowErrorIfNeeded() }
            return SDL_GetJoystickID(joystick).also { SDL.ThrowErrorIfNeeded() }
        }

    @OptIn(ExperimentalForeignApi::class)
    public var IsEnabled: Boolean
        get() {
            check(!this.IsClosed)
            return SDL_GamepadConnected(this.Native).also { SDL.ThrowErrorIfNeeded() }
        }
        set(value) {
            check(!this.IsClosed)
            if (value) {
                SDL_OpenGamepad(this.ID).also { SDL.ThrowErrorIfNeeded() }
            } else {
                SDL_CloseGamepad(this.Native).also { SDL.ThrowErrorIfNeeded() }
            }
        }

    @OptIn(ExperimentalForeignApi::class)
    public val Name: String?
        get() {
            check(!this.IsClosed)
            return SDL_GetGamepadName(this.Native).also { SDL.ThrowErrorIfNeeded() }?.toKString()
        }

    public var OnButtonAction: ((GamepadButtonActionEvent) -> Unit)? = null
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
    public var OnAxisChange: ((GamepadAxisChangeEvent) -> Unit)? = null
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

    internal constructor(playerIndex: Int) {
        this.PlayerIndex = playerIndex
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun IsButtonPressed(button: GamepadButton): Boolean {
        check(!this.IsClosed)
        return SDL_GetGamepadButton(this.Native, button.ToNativeValue()).also { SDL.ThrowErrorIfNeeded() }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun GetAxisValue(axis: GamepadAxis): Float {
        check(!this.IsClosed)
        return SDL_GetGamepadAxis(this.Native, axis.ToNativeValue()).also { SDL.ThrowErrorIfNeeded() }.let {
            Math.Lerp(-1f, 1f, Math.InverseLerp(SDL_JOYSTICK_AXIS_MIN.toFloat(), SDL_JOYSTICK_AXIS_MAX.toFloat(), it.toFloat()))
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun Rumble(lowFrequencyRumble: Float, highFrequencyRumble: Float, duration: Float) {
        check(!this.IsClosed)
        val lowFrequencyRumble = (lowFrequencyRumble * 0xFFFF).toInt().coerceIn(0, 0xFFFF).toUShort()
        val highFrequencyRumble = (highFrequencyRumble * 0xFFFF).toInt().coerceIn(0, 0xFFFF).toUShort()
        val duration = (duration * 1000).toUInt()
        SDL_RumbleGamepad(this.Native, lowFrequencyRumble, highFrequencyRumble, duration).also { SDL.ThrowErrorIfNeeded() }
    }

    public override fun close() {
        check(!this.IsClosed)
        this.IsClosed = true
    }

}

public class GamepadButtonActionEvent(
    public val Timestamp: Float,
    public val PlayerIndex: Int,
    public val Button: GamepadButton,
    public val IsPressed: Boolean,
)

public class GamepadAxisChangeEvent(
    public val Timestamp: Float,
    public val PlayerIndex: Int,
    public val Axis: GamepadAxis,
    public val Value: Float,
)

public enum class GamepadButton {
    Left,
    Right,
    Up,
    Down,

    South, // A / X
    East,  // B / Circle
    West,  // X / Square
    North, // Y / Triangle

    LeftStick,
    RightStick,

    LeftShoulder,
    RightShoulder,

    LeftPaddle_1,
    LeftPaddle_2,

    RightPaddle_1,
    RightPaddle_2,

    Start,
    Back,
    Guide,

    Touchpad,

    Misc_1,
    Misc_2,
    Misc_3,
    Misc_4,
    Misc_5,
    Misc_6;

    @OptIn(ExperimentalForeignApi::class)
    internal fun ToNativeValue(): Int {
        return when (this) {
            Left -> SDL_GAMEPAD_BUTTON_DPAD_LEFT
            Right -> SDL_GAMEPAD_BUTTON_DPAD_RIGHT
            Up -> SDL_GAMEPAD_BUTTON_DPAD_UP
            Down -> SDL_GAMEPAD_BUTTON_DPAD_DOWN

            South -> SDL_GAMEPAD_BUTTON_SOUTH
            East -> SDL_GAMEPAD_BUTTON_EAST
            West -> SDL_GAMEPAD_BUTTON_WEST
            North -> SDL_GAMEPAD_BUTTON_NORTH

            LeftStick -> SDL_GAMEPAD_BUTTON_LEFT_STICK
            RightStick -> SDL_GAMEPAD_BUTTON_RIGHT_STICK

            LeftShoulder -> SDL_GAMEPAD_BUTTON_LEFT_SHOULDER
            RightShoulder -> SDL_GAMEPAD_BUTTON_RIGHT_SHOULDER

            LeftPaddle_1 -> SDL_GAMEPAD_BUTTON_LEFT_PADDLE1
            RightPaddle_1 -> SDL_GAMEPAD_BUTTON_RIGHT_PADDLE1
            LeftPaddle_2 -> SDL_GAMEPAD_BUTTON_LEFT_PADDLE2
            RightPaddle_2 -> SDL_GAMEPAD_BUTTON_RIGHT_PADDLE2

            Start -> SDL_GAMEPAD_BUTTON_START
            Back -> SDL_GAMEPAD_BUTTON_BACK
            Guide -> SDL_GAMEPAD_BUTTON_GUIDE

            Touchpad -> SDL_GAMEPAD_BUTTON_TOUCHPAD

            Misc_1 -> SDL_GAMEPAD_BUTTON_MISC1
            Misc_2 -> SDL_GAMEPAD_BUTTON_MISC2
            Misc_3 -> SDL_GAMEPAD_BUTTON_MISC3
            Misc_4 -> SDL_GAMEPAD_BUTTON_MISC4
            Misc_5 -> SDL_GAMEPAD_BUTTON_MISC5
            Misc_6 -> SDL_GAMEPAD_BUTTON_MISC6
        }
    }

    public companion object {
        @OptIn(ExperimentalForeignApi::class)
        internal fun FromNativeValue(value: Int): GamepadButton? {
            return when (value) {
                SDL_GAMEPAD_BUTTON_DPAD_LEFT -> Left
                SDL_GAMEPAD_BUTTON_DPAD_RIGHT -> Right
                SDL_GAMEPAD_BUTTON_DPAD_UP -> Up
                SDL_GAMEPAD_BUTTON_DPAD_DOWN -> Down

                SDL_GAMEPAD_BUTTON_SOUTH -> South
                SDL_GAMEPAD_BUTTON_EAST -> East
                SDL_GAMEPAD_BUTTON_WEST -> West
                SDL_GAMEPAD_BUTTON_NORTH -> North

                SDL_GAMEPAD_BUTTON_LEFT_STICK -> LeftStick
                SDL_GAMEPAD_BUTTON_RIGHT_STICK -> RightStick

                SDL_GAMEPAD_BUTTON_LEFT_SHOULDER -> LeftShoulder
                SDL_GAMEPAD_BUTTON_RIGHT_SHOULDER -> RightShoulder

                SDL_GAMEPAD_BUTTON_LEFT_PADDLE1 -> LeftPaddle_1
                SDL_GAMEPAD_BUTTON_RIGHT_PADDLE1 -> RightPaddle_1
                SDL_GAMEPAD_BUTTON_LEFT_PADDLE2 -> LeftPaddle_2
                SDL_GAMEPAD_BUTTON_RIGHT_PADDLE2 -> RightPaddle_2

                SDL_GAMEPAD_BUTTON_START -> Start
                SDL_GAMEPAD_BUTTON_BACK -> Back
                SDL_GAMEPAD_BUTTON_GUIDE -> Guide

                SDL_GAMEPAD_BUTTON_TOUCHPAD -> Touchpad

                SDL_GAMEPAD_BUTTON_MISC1 -> Misc_1
                SDL_GAMEPAD_BUTTON_MISC2 -> Misc_2
                SDL_GAMEPAD_BUTTON_MISC3 -> Misc_3
                SDL_GAMEPAD_BUTTON_MISC4 -> Misc_4
                SDL_GAMEPAD_BUTTON_MISC5 -> Misc_5
                SDL_GAMEPAD_BUTTON_MISC6 -> Misc_6
                else -> null
            }
        }
    }
}

public enum class GamepadAxis {
    LeftX,
    LeftY,

    RightX,
    RightY,

    LeftTrigger,
    RightTrigger;

    @OptIn(ExperimentalForeignApi::class)
    internal fun ToNativeValue(): Int {
        return when (this) {
            LeftX -> SDL_GAMEPAD_AXIS_LEFTX
            LeftY -> SDL_GAMEPAD_AXIS_LEFTY
            RightX -> SDL_GAMEPAD_AXIS_RIGHTX
            RightY -> SDL_GAMEPAD_AXIS_RIGHTY
            LeftTrigger -> SDL_GAMEPAD_AXIS_LEFT_TRIGGER
            RightTrigger -> SDL_GAMEPAD_AXIS_RIGHT_TRIGGER
        }
    }

    public companion object {
        @OptIn(ExperimentalForeignApi::class)
        internal fun FromNativeValue(value: Int): GamepadAxis? {
            return when (value) {
                SDL_GAMEPAD_AXIS_LEFTX -> LeftX
                SDL_GAMEPAD_AXIS_LEFTY -> LeftY
                SDL_GAMEPAD_AXIS_RIGHTX -> RightX
                SDL_GAMEPAD_AXIS_RIGHTY -> RightY
                SDL_GAMEPAD_AXIS_LEFT_TRIGGER -> LeftTrigger
                SDL_GAMEPAD_AXIS_RIGHT_TRIGGER -> RightTrigger
                else -> null
            }
        }
    }
}
