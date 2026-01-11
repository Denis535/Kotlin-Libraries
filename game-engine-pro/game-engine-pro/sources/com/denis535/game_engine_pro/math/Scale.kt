package com.denis535.game_engine_pro.math

public data class Scale(
    public val X: Float, public val Y: Float, public val Z: Float
) {
    public companion object {

        public val Zero: Scale = Scale(0f, 0f, 0f)
        public val One: Scale = Scale(1f, 1f, 1f)

        public fun Lerp(v0: Scale, v1: Scale, t: Float): Scale {
            return v0 + (v1 - v0) * t;
        }

    }

    public operator fun unaryMinus(): Scale = Scale(-this.X, -this.Y, -this.Z)

    public operator fun plus(scalar: Float): Scale = Scale(this.X + scalar, this.Y + scalar, this.Z + scalar)
    public operator fun minus(scalar: Float): Scale = Scale(this.X - scalar, this.Y - scalar, this.Z - scalar)

    public operator fun times(scalar: Float): Scale = Scale(this.X * scalar, this.Y * scalar, this.Z * scalar)
    public operator fun div(scalar: Float): Scale = Scale(this.X / scalar, this.Y / scalar, this.Z / scalar)

    public operator fun plus(other: Scale): Scale = Scale(this.X + other.X, this.Y + other.Y, this.Z + other.Z)
    public operator fun minus(other: Scale): Scale = Scale(this.X - other.X, this.Y - other.Y, this.Z - other.Z)

    public operator fun times(other: Scale): Scale = Scale(this.X * other.X, this.Y * other.Y, this.Z * other.Z)
    public operator fun div(other: Scale): Scale = Scale(this.X / other.X, this.Y / other.Y, this.Z / other.Z)

}
