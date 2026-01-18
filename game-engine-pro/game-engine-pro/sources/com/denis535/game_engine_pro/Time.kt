package com.denis535.game_engine_pro

public class Time {

    public val Fixed: FixedTime = FixedTime()

    public var Number: Int = 0
        internal set

    public var Time: Float = 0.0f
        internal set

    public var DeltaTime: Float = 0.0f
        internal set

    public val Fps: Float
        get() {
            return if (this.DeltaTime > 0.0f) 1.0f / this.DeltaTime else 0.0f
        }

    internal constructor()

    public override fun toString(): String {
        return "Time(Number=${this.Number}, Time=${this.Time})"
    }

}

public class FixedTime {

    public var Number: Int = 0
        internal set

    public val Time: Float
        get() {
            return this.Number * this.DeltaTime
        }

    public var DeltaTime: Float = 0.0f
        internal set

    internal constructor()

    public override fun toString(): String {
        return "FixedTime(Number=${this.Number}, Time=${this.Time})"
    }

}
