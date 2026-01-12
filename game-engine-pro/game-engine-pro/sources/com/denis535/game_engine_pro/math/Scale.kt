package com.denis535.game_engine_pro.math

public data class Scale(
    public val X: Float,
    public val Y: Float,
    public val Z: Float,
) {
    public companion object {

        public val Zero: Scale = Scale(0f, 0f, 0f)
        public val One: Scale = Scale(1f, 1f, 1f)

        public fun Lerp(v0: Scale, v1: Scale, t: Float): Scale {
            return Scale(
                v0.X + (v1.X - v0.X) * t,
                v0.Y + (v1.Y - v0.Y) * t,
                v0.Z + (v1.Z - v0.Z) * t,
            );
        }

    }

    public operator fun unaryMinus(): Scale = Scale(-this.X, -this.Y, -this.Z)

    public operator fun plus(scale: Scale): Scale = Scale(this.X + scale.X, this.Y + scale.Y, this.Z + scale.Z)
    public operator fun minus(scale: Scale): Scale = Scale(this.X - scale.X, this.Y - scale.Y, this.Z - scale.Z)

    public operator fun times(scalar: Float): Scale = Scale(this.X * scalar, this.Y * scalar, this.Z * scalar)
    public operator fun div(scalar: Float): Scale = Scale(this.X / scalar, this.Y / scalar, this.Z / scalar)

    public operator fun times(scale: Scale): Scale = Scale(this.X * scale.X, this.Y * scale.Y, this.Z * scale.Z)
    public operator fun div(scale: Scale): Scale = Scale(this.X / scale.X, this.Y / scale.Y, this.Z / scale.Z)

}
