package com.denis535.game_engine_pro.input

import com.denis535.game_engine_pro.*
import com.denis535.internal.sdl.*
import kotlinx.cinterop.*

public class Gamepad : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    @OptIn(ExperimentalForeignApi::class)
    internal var NativeDevice: CPointer<cnames.structs.SDL_Gamepad>? = null
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

    @OptIn(ExperimentalForeignApi::class)
    public val IsConnected: Boolean
        get() {
            check(!this.IsClosed)
            this.NativeDevice?.let {
                return SDL_GamepadConnected(it).SDL_CheckError()
            }
            return false
        }

    @OptIn(ExperimentalForeignApi::class)
    public val Vendor: UShort
        get() {
            check(!this.IsClosed)
            this.NativeDevice?.let {
                return SDL_GetGamepadVendor(it).SDL_CheckError()
            }
            return 0U
        }

    @OptIn(ExperimentalForeignApi::class)
    public val Product: UShort
        get() {
            check(!this.IsClosed)
            this.NativeDevice?.let {
                return SDL_GetGamepadProduct(it).SDL_CheckError()
            }
            return 0U
        }

    @OptIn(ExperimentalForeignApi::class)
    public val ProductVersion: UShort
        get() {
            check(!this.IsClosed)
            this.NativeDevice?.let {
                return SDL_GetGamepadProductVersion(it).SDL_CheckError()
            }
            return 0U
        }

    @OptIn(ExperimentalForeignApi::class)
    public val Name: String?
        get() {
            check(!this.IsClosed)
            this.NativeDevice?.let {
                return SDL_GetGamepadName(it).SDL_CheckError()?.toKString()
            }
            return null
        }

    public var OnButtonActionCallback: ((GamepadButtonActionEvent) -> Unit)? = null
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
    public var OnAxisActionCallback: ((GamepadAxisActionEvent) -> Unit)? = null
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

    @OptIn(ExperimentalForeignApi::class)
    public override fun close() {
        check(!this.IsClosed)
        this.NativeDevice?.let {
            SDL_CloseGamepad(it).SDL_CheckError()
        }
        this.IsClosed = true
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun IsButtonPressed(button: GamepadButton): Boolean {
        check(!this.IsClosed)
        this.NativeDevice?.let {
            return SDL_GetGamepadButton(it, button.ToNativeValue()).SDL_CheckError()
        }
        return false
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun GetAxisValue(axis: GamepadAxis): Float {
        check(!this.IsClosed)
        this.NativeDevice?.let {
            return SDL_GetGamepadAxis(it, axis.ToNativeValue()).SDL_CheckError().let {
                Math.Lerp(-1f, 1f, Math.InverseLerp(SDL_JOYSTICK_AXIS_MIN.toFloat(), SDL_JOYSTICK_AXIS_MAX.toFloat(), it.toFloat()))
            }
        }
        return 0f
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun SetVibration(lowFrequencyRumble: Float, highFrequencyRumble: Float, duration: Float) {
        check(!this.IsClosed)
        this.NativeDevice?.let {
            val lowFrequencyRumble = (lowFrequencyRumble * 0xFFFF).toInt().coerceIn(0, 0xFFFF).toUShort()
            val highFrequencyRumble = (highFrequencyRumble * 0xFFFF).toInt().coerceIn(0, 0xFFFF).toUShort()
            val duration = (duration * 1000).toUInt()
            SDL_RumbleGamepad(it, lowFrequencyRumble, highFrequencyRumble, duration).SDL_CheckError()
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    public fun SetLedColor(red: Float, green: Float, blue: Float) {
        check(!this.IsClosed)
        this.NativeDevice?.let {
            val red = (red * 0xFF).toInt().coerceIn(0, 0xFF).toUByte()
            val green = (green * 0xFF).toInt().coerceIn(0, 0xFF).toUByte()
            val blue = (blue * 0xFF).toInt().coerceIn(0, 0xFF).toUByte()
            SDL_SetGamepadLED(it, red, green, blue).SDL_CheckError()
        }
    }

}

public data class GamepadButtonActionEvent(
    public val Timestamp: Float,
    internal val NativeDeviceID: UInt,
    public val PlayerIndex: Int,
    public val Button: GamepadButton,
    public val IsPressed: Boolean,
)

public data class GamepadAxisActionEvent(
    public val Timestamp: Float,
    internal val NativeDeviceID: UInt,
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
}

public enum class GamepadAxis {
    LeftX,
    LeftY,

    RightX,
    RightY,

    LeftTrigger,
    RightTrigger;
}
