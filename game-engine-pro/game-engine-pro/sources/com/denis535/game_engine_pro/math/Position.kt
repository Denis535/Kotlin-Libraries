package com.denis535.game_engine_pro.math

public data class Position(
    public val X: Float,
    public val Y: Float,
    public val Z: Float,
) {
    public companion object {

        public val Zero: Position = Position(0f, 0f, 0f)

        public fun Lerp(v0: Position, v1: Position, t: Float): Position {
            return Position(
                v0.X + (v1.X - v0.X) * t,
                v0.Y + (v1.Y - v0.Y) * t,
                v0.Z + (v1.Z - v0.Z) * t,
            );
        }

        public fun Min(v0: Position, v1: Position): Position {
            return Position(
                Math.Min(v0.X, v1.X),
                Math.Min(v0.Y, v1.Y),
                Math.Min(v0.Z, v1.Z),
            );
        }

        public fun Max(v0: Position, v1: Position): Position {
            return Position(
                Math.Max(v0.X, v1.X),
                Math.Max(v0.Y, v1.Y),
                Math.Max(v0.Z, v1.Z),
            );
        }

        public fun Clamp(value: Position, min: Position, max: Position): Position {
            return Position(
                Math.Clamp(value.X, min.X, max.X),
                Math.Clamp(value.Y, min.Y, max.Y),
                Math.Clamp(value.Z, min.Z, max.Z),
            )
        }

        public fun Clamp01(value: Position): Position {
            return Position(
                Math.Clamp01(value.X),
                Math.Clamp01(value.Y),
                Math.Clamp01(value.Z),
            )
        }

    }

    public operator fun unaryMinus(): Position = Position(-this.X, -this.Y, -this.Z)

    public operator fun plus(scalar: Float): Position = Position(this.X + scalar, this.Y + scalar, this.Z + scalar)
    public operator fun minus(scalar: Float): Position = Position(this.X - scalar, this.Y - scalar, this.Z - scalar)
    public operator fun times(scalar: Float): Position = Position(this.X * scalar, this.Y * scalar, this.Z * scalar)
    public operator fun div(scalar: Float): Position = Position(this.X / scalar, this.Y / scalar, this.Z / scalar)

    public operator fun plus(position: Position): Position = Position(this.X + position.X, this.Y + position.Y, this.Z + position.Z)
    public operator fun minus(position: Position): Position = Position(this.X - position.X, this.Y - position.Y, this.Z - position.Z)

    public operator fun plus(direction: Direction): Position = Position(this.X + direction.X, this.Y + direction.Y, this.Z + direction.Z)
    public operator fun minus(direction: Direction): Position = Position(this.X - direction.X, this.Y - direction.Y, this.Z - direction.Z)

    public operator fun times(scale: Scale): Position = Position(this.X * scale.X, this.Y * scale.Y, this.Z * scale.Z)
    public operator fun div(scale: Scale): Position = Position(this.X / scale.X, this.Y / scale.Y, this.Z / scale.Z)

}
