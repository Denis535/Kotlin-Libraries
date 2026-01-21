package com.denis535.game_engine_pro.math

public data class Vector3I(
    public val X: Int,
    public val Y: Int,
    public val Z: Int,
) {
    public companion object {

        public val Zero: Vector3I = Vector3I(0, 0, 0)
        public val One: Vector3I = Vector3I(1, 1, 1)

        public val AxisX: Vector3I = Vector3I(1, 0, 0)
        public val AxisY: Vector3I = Vector3I(0, 1, 0)
        public val AxisZ: Vector3I = Vector3I(0, 0, 1)

        public fun Lerp(v0: Vector3I, v1: Vector3I, t: Float): Vector3 {
            return Vector3(
                v0.X + (v1.X - v0.X) * t,
                v0.Y + (v1.Y - v0.Y) * t,
                v0.Z + (v1.Z - v0.Z) * t,
            )
        }

        public fun Min(v0: Vector3I, v1: Vector3I): Vector3I {
            return Vector3I(
                Math.Min(v0.X, v1.X),
                Math.Min(v0.Y, v1.Y),
                Math.Min(v0.Z, v1.Z),
            )
        }

        public fun Max(v0: Vector3I, v1: Vector3I): Vector3I {
            return Vector3I(
                Math.Max(v0.X, v1.X),
                Math.Max(v0.Y, v1.Y),
                Math.Max(v0.Z, v1.Z),
            )
        }

        public fun Clamp(value: Vector3I, min: Vector3I, max: Vector3I): Vector3I {
            return Vector3I(
                Math.Clamp(value.X, min.X, max.X),
                Math.Clamp(value.Y, min.Y, max.Y),
                Math.Clamp(value.Z, min.Z, max.Z),
            )
        }

        public fun Clamp01(value: Vector3I): Vector3I {
            return Vector3I(
                Math.Clamp01(value.X),
                Math.Clamp01(value.Y),
                Math.Clamp01(value.Z),
            )
        }

    }

    public val Length: Float
        get() {
            return Math.Sqrt(this.X.toFloat() * this.X + this.Y * this.Y + this.Z * this.Z)
        }

    public val LengthSquared: Float
        get() {
            return this.X.toFloat() * this.X + this.Y * this.Y + this.Z * this.Z
        }

    public val Normalized: Vector3
        get() {
            val length = this.Length
            return Vector3(this.X / length, this.Y / length, this.Z / length)
        }

    public fun Dot(vector: Vector3I): Float {
        return this.X.toFloat() * vector.X + this.Y * vector.Y + this.Z * vector.Z
    }

    public fun Cross(vector: Vector3I): Vector3I {
        return Vector3I(
            this.Y * vector.Z - this.Z * vector.Y,
            this.Z * vector.X - this.X * vector.Z,
            this.X * vector.Y - this.Y * vector.X,
        )
    }

    public operator fun unaryMinus(): Vector3I = Vector3I(-this.X, -this.Y, -this.Z)

    public operator fun plus(vector: Vector3I): Vector3I = Vector3I(this.X + vector.X, this.Y + vector.Y, this.Z + vector.Z)
    public operator fun minus(vector: Vector3I): Vector3I = Vector3I(this.X - vector.X, this.Y - vector.Y, this.Z - vector.Z)

    public operator fun times(scalar: Float): Vector3 = Vector3(this.X * scalar, this.Y * scalar, this.Z * scalar)
    public operator fun div(scalar: Float): Vector3 = Vector3(this.X / scalar, this.Y / scalar, this.Z / scalar)

}
