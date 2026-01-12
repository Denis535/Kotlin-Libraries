package com.denis535.game_engine_pro.math

public data class Rotation(
    public val X: Float,
    public val Y: Float,
    public val Z: Float,
    public val W: Float,
) {
    public companion object {

        public val Identity: Rotation = Rotation(0f, 0f, 0f, 1f);

        public fun Slerp(v0: Rotation, v1: Rotation, t: Float): Rotation {
            var dot = v0.X * v1.X + v0.Y * v1.Y + v0.Z * v1.Z + v0.W * v1.W
            var v1_X = v1.X
            var v1_Y = v1.Y
            var v1_Z = v1.Z
            var v1_W = v1.W

            if (dot < 0f) {
                dot = -dot
                v1_X = -v1_X
                v1_Y = -v1_Y
                v1_Z = -v1_Z
                v1_W = -v1_W
            }

            if (dot > 1f) {
                dot = 1f
            }

            if (dot > 0.9995f) {
                return Rotation(
                    v0.X + (v1_X - v0.X) * t,
                    v0.Y + (v1_Y - v0.Y) * t,
                    v0.Z + (v1_Z - v0.Z) * t,
                    v0.W + (v1_W - v0.W) * t,
                )
            }

            val theta = Math.Acos(dot)

            val sinTheta = Math.Sin(theta)
            val cosTheta = dot

            val sinTheta_t = Math.Sin(theta * t)
            val cosTheta_t = Math.Cos(theta * t)

            val k0 = cosTheta_t - cosTheta * sinTheta_t / sinTheta
            val k1 = sinTheta_t / sinTheta

            return Rotation(
                v0.X * k0 + v1_X * k1,
                v0.Y * k0 + v1_Y * k1,
                v0.Z * k0 + v1_Z * k1,
                v0.W * k0 + v1_W * k1,
            )
        }

        public fun Angle(v0: Rotation, v1: Rotation): Float {
            var dot = v0.X * v1.X + v0.Y * v1.Y + v0.Z * v1.Z + v0.W * v1.W

            if (dot < 0f) {
                dot = -dot
            }

            if (dot > 1f) {
                dot = 1f
            }

            val theta = 2f * Math.Acos(Math.Clamp01(Math.Abs(dot)))
            return theta * Math.RadToDeg
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

    public val Normalized: Rotation
        get() {
            val length = this.Length;
            return Rotation(this.X / length, this.Y / length, this.Z / length, this.W / length)
        }

    public fun Mul(rotation: Rotation): Rotation {
        return Rotation(
            this.W * rotation.X + this.X * rotation.W + this.Y * rotation.Z - this.Z * rotation.Y,
            this.W * rotation.Y + this.Y * rotation.W + this.Z * rotation.X - this.X * rotation.Z,
            this.W * rotation.Z + this.Z * rotation.W + this.X * rotation.Y - this.Y * rotation.X,
            this.W * rotation.W - this.X * rotation.X - this.Y * rotation.Y - this.Z * rotation.Z,
        );
    }

}
