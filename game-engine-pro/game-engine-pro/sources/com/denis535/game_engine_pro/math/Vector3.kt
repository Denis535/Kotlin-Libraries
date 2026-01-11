package com.denis535.game_engine_pro.math

public data class Vector3(
    public val X: Float,
    public val Y: Float,
    public val Z: Float
) {
    public companion object {

        public val Left: Vector3 = Vector3(-1f, 0f, 0f)
        public val Right: Vector3 = Vector3(+1f, 0f, 0f)

        public val Up: Vector3 = Vector3(0f, +1f, 0f)
        public val Down: Vector3 = Vector3(0f, -1f, 0f)

        public val Forward: Vector3 = Vector3(0f, 0f, +1f)
        public val Backward: Vector3 = Vector3(0f, 0f, -1f)

        public fun Dot(v0: Vector3, v1: Vector3): Float {
            return v0.X * v1.X + v0.Y * v1.Y + v0.Z * v1.Z;
        }

        public fun Cross(v0: Vector3, v1: Vector3): Vector3 {
            return Vector3(v0.Y * v1.Z - v0.Z * v1.Y, v0.Z * v1.X - v0.X * v1.Z, v0.X * v1.Y - v0.Y * v1.X);
        }

        public fun Clamp(value: Vector3, min: Vector3, max: Vector3): Vector3 {
            return Vector3(
                Math.Clamp(value.X, min.X, max.X), Math.Clamp(value.Y, min.Y, max.Y), Math.Clamp(value.Z, min.Z, max.Z)
            )
        }

        public fun Clamp01(value: Vector3): Vector3 {
            return Vector3(
                Math.Clamp01(value.X), Math.Clamp01(value.Y), Math.Clamp01(value.Z)
            )
        }

        public fun Min(v0: Vector3, v1: Vector3): Vector3 {
            return Vector3(Math.Min(v0.X, v1.X), Math.Min(v0.Y, v1.Y), Math.Min(v0.Z, v1.Z));
        }

        public fun Max(v0: Vector3, v1: Vector3): Vector3 {
            return Vector3(Math.Max(v0.X, v1.X), Math.Max(v0.Y, v1.Y), Math.Max(v0.Z, v1.Z));
        }

        public fun Lerp(v0: Vector3, v1: Vector3, t: Float): Vector3 {
            return v0 + (v1 - v0) * t;
        }

        public fun Lerp(v0: Vector3, v1: Vector3, t: Vector3): Vector3 {
            return v0 + (v1 - v0) * t;
        }

        public fun InverseLerp(v0: Vector3, v1: Vector3, value: Vector3): Vector3 {
            return (value - v0) / (v1 - v0);
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
            val length = this.Length;
            return Vector3(this.X / length, this.Y / length, this.Z / length)
        }

    public operator fun unaryMinus(): Vector3 = Vector3(-this.X, -this.Y, -this.Z)

    public operator fun plus(scalar: Float): Vector3 = Vector3(this.X + scalar, this.Y + scalar, this.Z + scalar)
    public operator fun minus(scalar: Float): Vector3 = Vector3(this.X - scalar, this.Y - scalar, this.Z - scalar)

    public operator fun times(scalar: Float): Vector3 = Vector3(this.X * scalar, this.Y * scalar, this.Z * scalar)
    public operator fun div(scalar: Float): Vector3 = Vector3(this.X / scalar, this.Y / scalar, this.Z / scalar)

    public operator fun plus(other: Vector3): Vector3 = Vector3(this.X + other.X, this.Y + other.Y, this.Z + other.Z)
    public operator fun minus(other: Vector3): Vector3 = Vector3(this.X - other.X, this.Y - other.Y, this.Z - other.Z)

    public operator fun times(other: Vector3): Vector3 = Vector3(this.X * other.X, this.Y * other.Y, this.Z * other.Z)
    public operator fun div(other: Vector3): Vector3 = Vector3(this.X / other.X, this.Y / other.Y, this.Z / other.Z)

}
