package com.denis535.game_engine_pro.math

public data class Direction(
    public val X: Float,
    public val Y: Float,
    public val Z: Float,
) {
    public companion object {

        public val Left: Direction = Direction(-1f, 0f, 0f)
        public val Right: Direction = Direction(+1f, 0f, 0f)

        public val Up: Direction = Direction(0f, +1f, 0f)
        public val Down: Direction = Direction(0f, -1f, 0f)

        public val Forward: Direction = Direction(0f, 0f, +1f)
        public val Backward: Direction = Direction(0f, 0f, -1f)

        public fun Lerp(v0: Direction, v1: Direction, t: Float): Direction {
            return Direction(
                v0.X + (v1.X - v0.X) * t,
                v0.Y + (v1.Y - v0.Y) * t,
                v0.Z + (v1.Z - v0.Z) * t,
            );
        }

        public fun Dot(v0: Direction, v1: Direction): Float {
            return v0.X * v1.X + v0.Y * v1.Y + v0.Z * v1.Z;
        }

        public fun Cross(v0: Direction, v1: Direction): Direction {
            return Direction(v0.Y * v1.Z - v0.Z * v1.Y, v0.Z * v1.X - v0.X * v1.Z, v0.X * v1.Y - v0.Y * v1.X);
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

    public val Normalized: Direction
        get() {
            val length = this.Length;
            return Direction(this.X / length, this.Y / length, this.Z / length)
        }

    public operator fun unaryMinus(): Direction = Direction(-this.X, -this.Y, -this.Z)

    public operator fun plus(scalar: Float): Direction = Direction(this.X + scalar, this.Y + scalar, this.Z + scalar)
    public operator fun minus(scalar: Float): Direction = Direction(this.X - scalar, this.Y - scalar, this.Z - scalar)
    public operator fun times(scalar: Float): Direction = Direction(this.X * scalar, this.Y * scalar, this.Z * scalar)
    public operator fun div(scalar: Float): Direction = Direction(this.X / scalar, this.Y / scalar, this.Z / scalar)

    public operator fun plus(direction: Direction): Direction = Direction(this.X + direction.X, this.Y + direction.Y, this.Z + direction.Z)
    public operator fun minus(direction: Direction): Direction = Direction(this.X - direction.X, this.Y - direction.Y, this.Z - direction.Z)

    public operator fun times(scale: Scale): Position = Position(this.X * scale.X, this.Y * scale.Y, this.Z * scale.Z)
    public operator fun div(scale: Scale): Position = Position(this.X / scale.X, this.Y / scale.Y, this.Z / scale.Z)

}
