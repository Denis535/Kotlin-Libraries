package com.denis535.game_engine_pro

public data class Vector3(
    public val X: Float,
    public val Y: Float,
    public val Z: Float,
) {
    public companion object {

        public val Zero: Vector3 = Vector3(0f, 0f, 0f)
        public val One: Vector3 = Vector3(1f, 1f, 1f)

        public val AxisX: Vector3 = Vector3(1f, 0f, 0f)
        public val AxisY: Vector3 = Vector3(0f, 1f, 0f)
        public val AxisZ: Vector3 = Vector3(0f, 0f, 1f)

        public fun Lerp(v0: Vector3, v1: Vector3, t: Float): Vector3 {
            return Vector3(
                v0.X + (v1.X - v0.X) * t,
                v0.Y + (v1.Y - v0.Y) * t,
                v0.Z + (v1.Z - v0.Z) * t,
            )
        }

        public fun Min(v0: Vector3, v1: Vector3): Vector3 {
            return Vector3(
                Math.Min(v0.X, v1.X),
                Math.Min(v0.Y, v1.Y),
                Math.Min(v0.Z, v1.Z),
            )
        }

        public fun Max(v0: Vector3, v1: Vector3): Vector3 {
            return Vector3(
                Math.Max(v0.X, v1.X),
                Math.Max(v0.Y, v1.Y),
                Math.Max(v0.Z, v1.Z),
            )
        }

        public fun Clamp(value: Vector3, min: Vector3, max: Vector3): Vector3 {
            return Vector3(
                Math.Clamp(value.X, min.X, max.X),
                Math.Clamp(value.Y, min.Y, max.Y),
                Math.Clamp(value.Z, min.Z, max.Z),
            )
        }

        public fun Clamp01(value: Vector3): Vector3 {
            return Vector3(
                Math.Clamp01(value.X),
                Math.Clamp01(value.Y),
                Math.Clamp01(value.Z),
            )
        }

    }

    public val Length: Float
        get() {
            return Math.Sqrt(this.X * this.X + this.Y * this.Y + this.Z * this.Z)
        }

    public val LengthSquared: Float
        get() {
            return this.X * this.X + this.Y * this.Y + this.Z * this.Z
        }

    public val Normalized: Vector3
        get() {
            val length = this.Length
            return Vector3(this.X / length, this.Y / length, this.Z / length)
        }

    public fun Dot(vector: Vector3): Float {
        return this.X * vector.X + this.Y * vector.Y + this.Z * vector.Z
    }

    public fun Cross(vector: Vector3): Vector3 {
        return Vector3(
            this.Y * vector.Z - this.Z * vector.Y,
            this.Z * vector.X - this.X * vector.Z,
            this.X * vector.Y - this.Y * vector.X,
        )
    }

    public operator fun unaryMinus(): Vector3 = Vector3(-this.X, -this.Y, -this.Z)

    public operator fun plus(vector: Vector3): Vector3 = Vector3(this.X + vector.X, this.Y + vector.Y, this.Z + vector.Z)
    public operator fun minus(vector: Vector3): Vector3 = Vector3(this.X - vector.X, this.Y - vector.Y, this.Z - vector.Z)

    public operator fun times(scalar: Float): Vector3 = Vector3(this.X * scalar, this.Y * scalar, this.Z * scalar)
    public operator fun div(scalar: Float): Vector3 = Vector3(this.X / scalar, this.Y / scalar, this.Z / scalar)

}
