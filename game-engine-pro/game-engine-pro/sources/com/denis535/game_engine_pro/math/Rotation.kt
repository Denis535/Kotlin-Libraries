package com.denis535.game_engine_pro.math

public data class Rotation(
    public val X: Float,
    public val Y: Float,
    public val Z: Float,
    public val W: Float,
) {
    public companion object {

        public val Identity: Rotation = Rotation(0f, 0f, 0f, 1f);

//        public fun Lerp(v0: Rotation, v1: Rotation, t: Float): Rotation {
//            return Rotation(
//                v0.X + (v1.X - v0.X) * t, //
//                v0.Y + (v1.Y - v0.Y) * t, //
//                v0.Z + (v1.Z - v0.Z) * t  //
//            );
//        }

    }

//    public operator fun unaryMinus(): Scale = Scale(-this.X, -this.Y, -this.Z)
//
//    public operator fun plus(scalar: Float): Scale = Scale(this.X + scalar, this.Y + scalar, this.Z + scalar)
//    public operator fun minus(scalar: Float): Scale = Scale(this.X - scalar, this.Y - scalar, this.Z - scalar)
//    public operator fun times(scalar: Float): Scale = Scale(this.X * scalar, this.Y * scalar, this.Z * scalar)
//    public operator fun div(scalar: Float): Scale = Scale(this.X / scalar, this.Y / scalar, this.Z / scalar)
//
//    public operator fun plus(scale: Scale): Scale = Scale(this.X + scale.X, this.Y + scale.Y, this.Z + scale.Z)
//    public operator fun minus(scale: Scale): Scale = Scale(this.X - scale.X, this.Y - scale.Y, this.Z - scale.Z)
//
//    public operator fun times(scale: Scale): Position = Position(this.X * scale.X, this.Y * scale.Y, this.Z * scale.Z)
//    public operator fun div(scale: Scale): Position = Position(this.X / scale.X, this.Y / scale.Y, this.Z / scale.Z)

}
