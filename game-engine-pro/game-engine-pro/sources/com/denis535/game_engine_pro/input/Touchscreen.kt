package com.denis535.game_engine_pro.input

import com.denis535.sdl.*
import kotlinx.cinterop.*

public class Touchscreen : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    internal constructor()

    public override fun close() {
        check(!this.IsClosed)
        this.IsClosed = true
    }

}

public class TouchEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val TouchID: ULong,
    public val State: TouchState,
    public val Point: Pair<Float, Float>, // [0..1]
    public val Delta: Pair<Float, Float>, // [-1..1]
    public val Pressure: Float, // [0..1]
)

public class ZoomEvent(
    public val Timestamp: Float,
    public val WindowID: UInt,
    public val State: ZoomState,
    public val Zoom: Float, // value <= 1 or value >= 1
)

public enum class TouchState {
    Begin,
    Changed,
    End,
    Canceled;
}

public enum class ZoomState {
    Begin,
    Changed,
    End;
}
