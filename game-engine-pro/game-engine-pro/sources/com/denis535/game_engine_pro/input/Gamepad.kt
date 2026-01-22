package com.denis535.game_engine_pro.input

import com.denis535.sdl.*
import kotlinx.cinterop.*

public class Gamepad : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    internal constructor()

    public override fun close() {
        check(!this.IsClosed)
        this.IsClosed = true
    }

}

public class GamepadButtonEvent(
    public val Timestamp: Float,
    public val PlayerIndex: Int,
    public val Button: GamepadButton,
)

public class GamepadAxisEvent(
    public val Timestamp: Float,
    public val PlayerIndex: Int,
    public val Axis: GamepadAxis,
    public val Value: Float,
)

public enum class GamepadButton {
    DPAD_LEFT,
    DPAD_RIGHT,
    DPAD_UP,
    DPAD_DOWN,

    WEST,  // X / Square
    EAST,  // B / Circle
    NORTH, // Y / Triangle
    SOUTH, // A / X

    LEFT_STICK,
    RIGHT_STICK,

    LEFT_SHOULDER,
    RIGHT_SHOULDER,

    LEFT_PADDLE1,
    RIGHT_PADDLE1,
    LEFT_PADDLE2,
    RIGHT_PADDLE2,

    START,
    BACK,
    GUIDE,

    TOUCHPAD,

    MISC1,
    MISC2,
    MISC3,
    MISC4,
    MISC5,
    MISC6;

    @OptIn(ExperimentalForeignApi::class)
    internal fun ToNativeValue(): Int {
        return when (this) {
            DPAD_LEFT -> SDL_GAMEPAD_BUTTON_DPAD_LEFT
            DPAD_RIGHT -> SDL_GAMEPAD_BUTTON_DPAD_RIGHT
            DPAD_UP -> SDL_GAMEPAD_BUTTON_DPAD_UP
            DPAD_DOWN -> SDL_GAMEPAD_BUTTON_DPAD_DOWN

            WEST -> SDL_GAMEPAD_BUTTON_WEST
            EAST -> SDL_GAMEPAD_BUTTON_EAST
            NORTH -> SDL_GAMEPAD_BUTTON_NORTH
            SOUTH -> SDL_GAMEPAD_BUTTON_SOUTH

            LEFT_STICK -> SDL_GAMEPAD_BUTTON_LEFT_STICK
            RIGHT_STICK -> SDL_GAMEPAD_BUTTON_RIGHT_STICK

            LEFT_SHOULDER -> SDL_GAMEPAD_BUTTON_LEFT_SHOULDER
            RIGHT_SHOULDER -> SDL_GAMEPAD_BUTTON_RIGHT_SHOULDER

            LEFT_PADDLE1 -> SDL_GAMEPAD_BUTTON_LEFT_PADDLE1
            RIGHT_PADDLE1 -> SDL_GAMEPAD_BUTTON_RIGHT_PADDLE1
            LEFT_PADDLE2 -> SDL_GAMEPAD_BUTTON_LEFT_PADDLE2
            RIGHT_PADDLE2 -> SDL_GAMEPAD_BUTTON_RIGHT_PADDLE2

            START -> SDL_GAMEPAD_BUTTON_START
            BACK -> SDL_GAMEPAD_BUTTON_BACK
            GUIDE -> SDL_GAMEPAD_BUTTON_GUIDE

            TOUCHPAD -> SDL_GAMEPAD_BUTTON_TOUCHPAD

            MISC1 -> SDL_GAMEPAD_BUTTON_MISC1
            MISC2 -> SDL_GAMEPAD_BUTTON_MISC2
            MISC3 -> SDL_GAMEPAD_BUTTON_MISC3
            MISC4 -> SDL_GAMEPAD_BUTTON_MISC4
            MISC5 -> SDL_GAMEPAD_BUTTON_MISC5
            MISC6 -> SDL_GAMEPAD_BUTTON_MISC6
        }
    }

    public companion object {
        @OptIn(ExperimentalForeignApi::class)
        internal fun FromNativeValue(value: Int): GamepadButton? {
            return when (value) {
                SDL_GAMEPAD_BUTTON_DPAD_LEFT -> DPAD_LEFT
                SDL_GAMEPAD_BUTTON_DPAD_RIGHT -> DPAD_RIGHT
                SDL_GAMEPAD_BUTTON_DPAD_UP -> DPAD_UP
                SDL_GAMEPAD_BUTTON_DPAD_DOWN -> DPAD_DOWN

                SDL_GAMEPAD_BUTTON_WEST -> WEST
                SDL_GAMEPAD_BUTTON_EAST -> EAST
                SDL_GAMEPAD_BUTTON_NORTH -> NORTH
                SDL_GAMEPAD_BUTTON_SOUTH -> SOUTH

                SDL_GAMEPAD_BUTTON_LEFT_STICK -> LEFT_STICK
                SDL_GAMEPAD_BUTTON_RIGHT_STICK -> RIGHT_STICK

                SDL_GAMEPAD_BUTTON_LEFT_SHOULDER -> LEFT_SHOULDER
                SDL_GAMEPAD_BUTTON_RIGHT_SHOULDER -> RIGHT_SHOULDER

                SDL_GAMEPAD_BUTTON_LEFT_PADDLE1 -> LEFT_PADDLE1
                SDL_GAMEPAD_BUTTON_RIGHT_PADDLE1 -> RIGHT_PADDLE1
                SDL_GAMEPAD_BUTTON_LEFT_PADDLE2 -> LEFT_PADDLE2
                SDL_GAMEPAD_BUTTON_RIGHT_PADDLE2 -> RIGHT_PADDLE2

                SDL_GAMEPAD_BUTTON_START -> START
                SDL_GAMEPAD_BUTTON_BACK -> BACK
                SDL_GAMEPAD_BUTTON_GUIDE -> GUIDE

                SDL_GAMEPAD_BUTTON_TOUCHPAD -> TOUCHPAD

                SDL_GAMEPAD_BUTTON_MISC1 -> MISC1
                SDL_GAMEPAD_BUTTON_MISC2 -> MISC2
                SDL_GAMEPAD_BUTTON_MISC3 -> MISC3
                SDL_GAMEPAD_BUTTON_MISC4 -> MISC4
                SDL_GAMEPAD_BUTTON_MISC5 -> MISC5
                SDL_GAMEPAD_BUTTON_MISC6 -> MISC6
                else -> null
            }
        }
    }
}

public enum class GamepadAxis {
    LEFTX,
    LEFTY,
    RIGHTX,
    RIGHTY,
    LEFT_TRIGGER,
    RIGHT_TRIGGER;

    @OptIn(ExperimentalForeignApi::class)
    internal fun ToNativeValue(): Int {
        return when (this) {
            LEFTX -> SDL_GAMEPAD_AXIS_LEFTX
            LEFTY -> SDL_GAMEPAD_AXIS_LEFTY
            RIGHTX -> SDL_GAMEPAD_AXIS_RIGHTX
            RIGHTY -> SDL_GAMEPAD_AXIS_RIGHTY
            LEFT_TRIGGER -> SDL_GAMEPAD_AXIS_LEFT_TRIGGER
            RIGHT_TRIGGER -> SDL_GAMEPAD_AXIS_RIGHT_TRIGGER
        }
    }

    public companion object {
        @OptIn(ExperimentalForeignApi::class)
        internal fun FromNativeValue(value: Int): GamepadAxis? {
            return when (value) {
                SDL_GAMEPAD_AXIS_LEFTX -> LEFTX
                SDL_GAMEPAD_AXIS_LEFTY -> LEFTY
                SDL_GAMEPAD_AXIS_RIGHTX -> RIGHTX
                SDL_GAMEPAD_AXIS_RIGHTY -> RIGHTY
                SDL_GAMEPAD_AXIS_LEFT_TRIGGER -> LEFT_TRIGGER
                SDL_GAMEPAD_AXIS_RIGHT_TRIGGER -> RIGHT_TRIGGER
                else -> null
            }
        }
    }
}
