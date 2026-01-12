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

    public operator fun unaryMinus(): Rotation = Rotation(-this.X, -this.Y, -this.Z, this.W)

    public fun TransformPosition(position: Position): Position {
        val x = this.X
        val y = this.Y
        val z = this.Z
        val w = this.W

        // 2 * (x,y,z) компоненты для удобства
        val xx = x * x
        val yy = y * y
        val zz = z * z
        val xy = x * y
        val xz = x * z
        val yz = y * z
        val wx = w * x
        val wy = w * y
        val wz = w * z

        val rx = (1f - 2f * (yy + zz)) * position.X + (2f * (xy - wz)) * position.Y + (2f * (xz + wy)) * position.Z
        val ry = (2f * (xy + wz)) * position.X + (1f - 2f * (xx + zz)) * position.Y + (2f * (yz - wx)) * position.Z
        val rz = (2f * (xz - wy)) * position.X + (2f * (yz + wx)) * position.Y + (1f - 2f * (xx + yy)) * position.Z
        return Position(rx, ry, rz)
    }

}
