package com.denis535.game_engine_pro

public data class Vector2I(
    public val X: Int,
    public val Y: Int,
) {
    public companion object {

        public val Zero: Vector2I = Vector2I(0, 0)
        public val One: Vector2I = Vector2I(1, 1)

        public val AxisX: Vector2I = Vector2I(1, 0)
        public val AxisY: Vector2I = Vector2I(0, 1)

        public fun Lerp(v0: Vector2I, v1: Vector2I, t: Float): Vector2 {
            return Vector2(
                v0.X + (v1.X - v0.X) * t,
                v0.Y + (v1.Y - v0.Y) * t,
            )
        }

        public fun Min(v0: Vector2I, v1: Vector2I): Vector2I {
            return Vector2I(
                Math.Min(v0.X, v1.X),
                Math.Min(v0.Y, v1.Y),
            )
        }

        public fun Max(v0: Vector2I, v1: Vector2I): Vector2I {
            return Vector2I(
                Math.Max(v0.X, v1.X),
                Math.Max(v0.Y, v1.Y),
            )
        }

        public fun Clamp(value: Vector2I, min: Vector2I, max: Vector2I): Vector2I {
            return Vector2I(
                Math.Clamp(value.X, min.X, max.X),
                Math.Clamp(value.Y, min.Y, max.Y),
            )
        }

        public fun Clamp01(value: Vector2I): Vector2I {
            return Vector2I(
                Math.Clamp01(value.X),
                Math.Clamp01(value.Y),
            )
        }

    }

    public val Length: Float
        get() {
            return Math.Sqrt(this.X.toFloat() * this.X + this.Y * this.Y)
        }

    public val LengthSquared: Float
        get() {
            return this.X.toFloat() * this.X + this.Y * this.Y
        }

    public val Normalized: Vector2
        get() {
            val length = this.Length
            return Vector2(this.X / length, this.Y / length)
        }

    public fun Dot(vector: Vector2I): Int {
        return this.X * vector.X + this.Y * vector.Y
    }

    public operator fun unaryMinus(): Vector2I = Vector2I(-this.X, -this.Y)

    public operator fun plus(vector: Vector2I): Vector2I = Vector2I(this.X + vector.X, this.Y + vector.Y)
    public operator fun minus(vector: Vector2I): Vector2I = Vector2I(this.X - vector.X, this.Y - vector.Y)

    public operator fun times(scalar: Float): Vector2 = Vector2(this.X * scalar, this.Y * scalar)
    public operator fun div(scalar: Float): Vector2 = Vector2(this.X / scalar, this.Y / scalar)

}
