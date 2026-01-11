package com.denis535.game_engine_pro.math

public data class Point(
    public val X: Float, public val Y: Float, public val Z: Float
) {
    public companion object {

        public val Zero: Point = Point(0f, 0f, 0f)

        public fun Lerp(v0: Point, v1: Point, t: Float): Point {
            return v0 + (v1 - v0) * t;
        }

        public fun Min(v0: Point, v1: Point): Point {
            return Point(Math.Min(v0.X, v1.X), Math.Min(v0.Y, v1.Y), Math.Min(v0.Z, v1.Z));
        }

        public fun Max(v0: Point, v1: Point): Point {
            return Point(Math.Max(v0.X, v1.X), Math.Max(v0.Y, v1.Y), Math.Max(v0.Z, v1.Z));
        }

        public fun Clamp(value: Point, min: Point, max: Point): Point {
            return Point(
                Math.Clamp(value.X, min.X, max.X), Math.Clamp(value.Y, min.Y, max.Y), Math.Clamp(value.Z, min.Z, max.Z)
            )
        }

        public fun Clamp01(value: Point): Point {
            return Point(
                Math.Clamp01(value.X), Math.Clamp01(value.Y), Math.Clamp01(value.Z)
            )
        }

    }

    public operator fun unaryMinus(): Point = Point(-this.X, -this.Y, -this.Z)

    public operator fun plus(scalar: Float): Point = Point(this.X + scalar, this.Y + scalar, this.Z + scalar)
    public operator fun minus(scalar: Float): Point = Point(this.X - scalar, this.Y - scalar, this.Z - scalar)

    public operator fun times(scalar: Float): Point = Point(this.X * scalar, this.Y * scalar, this.Z * scalar)
    public operator fun div(scalar: Float): Point = Point(this.X / scalar, this.Y / scalar, this.Z / scalar)

    public operator fun plus(other: Point): Point = Point(this.X + other.X, this.Y + other.Y, this.Z + other.Z)
    public operator fun minus(other: Point): Point = Point(this.X - other.X, this.Y - other.Y, this.Z - other.Z)

    public operator fun times(other: Point): Point = Point(this.X * other.X, this.Y * other.Y, this.Z * other.Z)
    public operator fun div(other: Point): Point = Point(this.X / other.X, this.Y / other.Y, this.Z / other.Z)

}
