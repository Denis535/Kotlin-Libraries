package com.denis535.game_engine_pro

public data class Vector2(
    public val X: Float,
    public val Y: Float,
) {
    public companion object {

        public val Zero: Vector2 = Vector2(0f, 0f)
        public val One: Vector2 = Vector2(1f, 1f)

        public val AxisX: Vector2 = Vector2(1f, 0f)
        public val AxisY: Vector2 = Vector2(0f, 1f)

        public fun Lerp(v0: Vector2, v1: Vector2, t: Float): Vector2 {
            return Vector2(
                v0.X + (v1.X - v0.X) * t,
                v0.Y + (v1.Y - v0.Y) * t,
            )
        }

        public fun Min(v0: Vector2, v1: Vector2): Vector2 {
            return Vector2(
                Math.Min(v0.X, v1.X),
                Math.Min(v0.Y, v1.Y),
            )
        }

        public fun Max(v0: Vector2, v1: Vector2): Vector2 {
            return Vector2(
                Math.Max(v0.X, v1.X),
                Math.Max(v0.Y, v1.Y),
            )
        }

        public fun Clamp(value: Vector2, min: Vector2, max: Vector2): Vector2 {
            return Vector2(
                Math.Clamp(value.X, min.X, max.X),
                Math.Clamp(value.Y, min.Y, max.Y),
            )
        }

        public fun Clamp01(value: Vector2): Vector2 {
            return Vector2(
                Math.Clamp01(value.X),
                Math.Clamp01(value.Y),
            )
        }

    }

    public val Length: Float
        get() {
            return Math.Sqrt(this.X * this.X + this.Y * this.Y)
        }

    public val LengthSquared: Float
        get() {
            return this.X * this.X + this.Y * this.Y
        }

    public val Normalized: Vector2
        get() {
            val length = this.Length
            return Vector2(this.X / length, this.Y / length)
        }

    public fun Dot(vector: Vector2): Float {
        return this.X * vector.X + this.Y * vector.Y
    }

    public operator fun unaryMinus(): Vector2 = Vector2(-this.X, -this.Y)

    public operator fun plus(vector: Vector2): Vector2 = Vector2(this.X + vector.X, this.Y + vector.Y)
    public operator fun minus(vector: Vector2): Vector2 = Vector2(this.X - vector.X, this.Y - vector.Y)

    public operator fun times(scalar: Float): Vector2 = Vector2(this.X * scalar, this.Y * scalar)
    public operator fun div(scalar: Float): Vector2 = Vector2(this.X / scalar, this.Y / scalar)

}
