package com.denis535.game_engine_pro

public class Time {

    public var Time: Float = 0.0f
        internal set

    public var DeltaTime: Float = 0.0f
        internal set

    public var FrameCount: Int = 0
        internal set

    public val Fps: Float
        get() {
            return if (this.DeltaTime > 0.0f) 1.0f / this.DeltaTime else 0.0f
        }

    public val Fixed: FixedTime = FixedTime()

    internal constructor()

    public override fun toString(): String {
        return "Time: ${this.Time}"
    }

}

public class FixedTime {

    public val Time: Float
        get() {
            return this.DeltaTime * this.FrameCount
        }

    public var DeltaTime: Float = 0.0f
        internal set

    public var FrameCount: Int = 0
        internal set

    internal constructor()

    public override fun toString(): String {
        return "FixedTime: ${this.Time}"
    }

}
