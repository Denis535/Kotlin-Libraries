package com.denis535.game_engine_pro

public data class Vector4(
    public val X: Float,
    public val Y: Float,
    public val Z: Float,
    public val W: Float,
) {
    public companion object {

        public val Zero: Vector4 = Vector4(0f, 0f, 0f, 0f)
        public val One: Vector4 = Vector4(1f, 1f, 1f, 1f)

        public val AxisX: Vector4 = Vector4(1f, 0f, 0f, 0f)
        public val AxisY: Vector4 = Vector4(0f, 1f, 0f, 0f)
        public val AxisZ: Vector4 = Vector4(0f, 0f, 1f, 0f)
        public val AxisW: Vector4 = Vector4(0f, 0f, 0f, 1f)

        public fun Min(v0: Vector4, v1: Vector4): Vector4 {
            return Vector4(
                Math.Min(v0.X, v1.X),
                Math.Min(v0.Y, v1.Y),
                Math.Min(v0.Z, v1.Z),
                Math.Min(v0.W, v1.W),
            )
        }

        public fun Max(v0: Vector4, v1: Vector4): Vector4 {
            return Vector4(
                Math.Max(v0.X, v1.X),
                Math.Max(v0.Y, v1.Y),
                Math.Max(v0.Z, v1.Z),
                Math.Max(v0.W, v1.W),
            )
        }

        public fun Lerp(v0: Vector4, v1: Vector4, t: Float): Vector4 {
            return Vector4(
                v0.X + (v1.X - v0.X) * t,
                v0.Y + (v1.Y - v0.Y) * t,
                v0.Z + (v1.Z - v0.Z) * t,
                v0.W + (v1.W - v0.W) * t,
            )
        }

        public fun Clamp(value: Vector4, min: Vector4, max: Vector4): Vector4 {
            return Vector4(
                Math.Clamp(value.X, min.X, max.X),
                Math.Clamp(value.Y, min.Y, max.Y),
                Math.Clamp(value.Z, min.Z, max.Z),
                Math.Clamp(value.W, min.W, max.W),
            )
        }

        public fun Clamp01(value: Vector4): Vector4 {
            return Vector4(
                Math.Clamp01(value.X),
                Math.Clamp01(value.Y),
                Math.Clamp01(value.Z),
                Math.Clamp01(value.W),
            )
        }

    }

    public val Length: Float
        get() {
            return Math.Sqrt(this.X * this.X + this.Y * this.Y + this.Z * this.Z + this.W * this.W)
        }

    public val LengthSquared: Float
        get() {
            return this.X * this.X + this.Y * this.Y + this.Z * this.Z + this.W * this.W
        }

    public val Normalized: Vector4
        get() {
            val length = this.Length
            return Vector4(this.X / length, this.Y / length, this.Z / length, this.W / length)
        }

    public fun Dot(vector: Vector4): Float {
        return this.X * vector.X + this.Y * vector.Y + this.Z * vector.Z + this.W * vector.W
    }

    public operator fun unaryMinus(): Vector4 = Vector4(-this.X, -this.Y, -this.Z, -this.W)

    public operator fun plus(vector: Vector4): Vector4 = Vector4(this.X + vector.X, this.Y + vector.Y, this.Z + vector.Z, this.W + vector.W)
    public operator fun minus(vector: Vector4): Vector4 = Vector4(this.X - vector.X, this.Y - vector.Y, this.Z - vector.Z, this.W - vector.W)

    public operator fun times(scalar: Float): Vector4 = Vector4(this.X * scalar, this.Y * scalar, this.Z * scalar, this.W * scalar)
    public operator fun div(scalar: Float): Vector4 = Vector4(this.X / scalar, this.Y / scalar, this.Z / scalar, this.W / scalar)

}
