package com.denis535.game_engine_pro.math

public data class Rotation(
    public val X: Float,
    public val Y: Float,
    public val Z: Float,
    public val W: Float,
) {
    public companion object {

        public val Identity: Rotation = Rotation(0f, 0f, 0f, 1f);

        public fun FromAxes(right: Direction, up: Direction, forward: Direction): Rotation {
            val m00 = right.X
            val m10 = right.Y
            val m20 = right.Z

            val m01 = up.X
            val m11 = up.Y
            val m21 = up.Z

            val m02 = forward.X
            val m12 = forward.Y
            val m22 = forward.Z

            if (m00 + m11 + m22 > 0f) {
                val s = Math.Sqrt(m00 + m11 + m22 + 1f) * 2f
                return Rotation(
                    (m21 - m12) / s,
                    (m02 - m20) / s,
                    (m10 - m01) / s,
                    0.25f * s,
                )
            }
            if (m00 > m11 && m00 > m22) {
                val s = Math.Sqrt(1f + m00 - m11 - m22) * 2f
                return Rotation(
                    0.25f * s,
                    (m01 + m10) / s,
                    (m02 + m20) / s,
                    (m21 - m12) / s,
                )
            }
            if (m11 > m22) {
                val s = Math.Sqrt(1f + m11 - m00 - m22) * 2f
                return Rotation(
                    (m01 + m10) / s,
                    0.25f * s,
                    (m12 + m21) / s,
                    (m02 - m20) / s,
                )
            }
            run {
                val s = Math.Sqrt(1f + m22 - m00 - m11) * 2f
                return Rotation(
                    (m02 + m20) / s,
                    (m12 + m21) / s,
                    0.25f * s,
                    (m10 - m01) / s,
                )
            }
        }

        public fun FromForwardUp(forward: Direction, up: Direction = Direction.Up): Rotation {
            val right = up.Cross(forward)
            val up = forward.Cross(right)
            return FromAxes(right, up, forward)
        }

        public fun FromForwardRight(forward: Direction, right: Direction): Rotation {
            val up = forward.Cross(right)
            val right = up.Cross(forward)
            return FromAxes(right, up, forward)
        }

        public fun FromAxisAngle(axis: Direction, angle: Float): Rotation {
            val sin = Math.Sin(angle * Math.DegToRad / 2f)
            val cos = Math.Cos(angle * Math.DegToRad / 2f)
            return Rotation(
                axis.X * sin,
                axis.Y * sin,
                axis.Z * sin,
                cos,
            )
        }

        public fun FromAngleX(angleX: Float): Rotation {
            // https://github.com/Unity-Technologies/Unity.Mathematics/blob/master/src/Unity.Mathematics/quaternion.cs#L352
            val sinX = Math.Sin(angleX * Math.DegToRad / 2f)
            val cosX = Math.Cos(angleX * Math.DegToRad / 2f)
            return Rotation(
                sinX,
                0f,
                0f,
                cosX,
            );
        }

        public fun FromAngleY(angleY: Float): Rotation {
            val sinY = Math.Sin(angleY * Math.DegToRad / 2f)
            val cosY = Math.Cos(angleY * Math.DegToRad / 2f)
            return Rotation(
                0f,
                sinY,
                0f,
                cosY,
            );
        }

        public fun FromAngleZ(angleZ: Float): Rotation {
            val sinZ = Math.Sin(angleZ * Math.DegToRad / 2f)
            val cosZ = Math.Cos(angleZ * Math.DegToRad / 2f)
            return Rotation(
                0f,
                0f,
                sinZ,
                cosZ,
            );
        }

        public fun FromEulerAnglesYXZ(angleX: Float, angleY: Float, angleZ: Float): Rotation {
            // https://github.com/Unity-Technologies/Unity.Mathematics/blob/master/src/Unity.Mathematics/quaternion.cs#L155
            // return FromAngleZ(angleZ).Mul(FromAngleX(angleX)).Mul(FromAngleY(angleY))
            val sinX = Math.Sin(angleX * Math.DegToRad / 2f)
            val cosX = Math.Cos(angleX * Math.DegToRad / 2f)
            val sinY = Math.Sin(angleY * Math.DegToRad / 2f)
            val cosY = Math.Cos(angleY * Math.DegToRad / 2f)
            val sinZ = Math.Sin(angleZ * Math.DegToRad / 2f)
            val cosZ = Math.Cos(angleZ * Math.DegToRad / 2f)
            return Rotation(
                sinX * cosY * cosZ - sinY * sinZ * cosX,
                sinY * cosX * cosZ + sinX * sinZ * cosY,
                sinZ * cosX * cosY + sinX * sinY * cosZ,
                cosX * cosY * cosZ - sinY * sinZ * sinX,
            )
        }

        public fun Slerp(v0: Rotation, v1: Rotation, t: Float): Rotation {
            var dot = v0.Dot(v1)
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

            if (dot > 0.9995f) {
                return Rotation(
                    v0.X + (v1_X - v0.X) * t,
                    v0.Y + (v1_Y - v0.Y) * t,
                    v0.Z + (v1_Z - v0.Z) * t,
                    v0.W + (v1_W - v0.W) * t,
                )
            } else {
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

    public val Inverse: Rotation
        get() {
            return Rotation(-this.X, -this.Y, -this.Z, this.W)
        }

    public val Right: Direction
        get() {
            val xx = this.X * this.X * 2f;
            val xy = this.X * this.Y * 2f;
            val xz = this.X * this.Z * 2f;
            val xw = this.X * this.W * 2f;

            val yy = this.Y * this.Y * 2f;
            val yz = this.Y * this.Z * 2f;
            val yw = this.Y * this.W * 2f;

            val zz = this.Z * this.Z * 2f;
            val zw = this.Z * this.W * 2f;

            val m00 = 1f - (yy + zz);
            val m10 = xy + zw;
            val m20 = xz - yw;
            return Direction(m00, m10, m20)
        }

    public val Up: Direction
        get() {
            val xx = this.X * this.X * 2f;
            val xy = this.X * this.Y * 2f;
            val xz = this.X * this.Z * 2f;
            val xw = this.X * this.W * 2f;

            val yy = this.Y * this.Y * 2f;
            val yz = this.Y * this.Z * 2f;
            val yw = this.Y * this.W * 2f;

            val zz = this.Z * this.Z * 2f;
            val zw = this.Z * this.W * 2f;

            val m01 = xy - zw;
            val m11 = 1f - (xx + zz);
            val m21 = yz + xw;
            return Direction(m01, m11, m21)
        }

    public val Forward: Direction
        get() {
            val xx = this.X * this.X * 2f;
            val xy = this.X * this.Y * 2f;
            val xz = this.X * this.Z * 2f;
            val xw = this.X * this.W * 2f;

            val yy = this.Y * this.Y * 2f;
            val yz = this.Y * this.Z * 2f;
            val yw = this.Y * this.W * 2f;

            val zz = this.Z * this.Z * 2f;
            val zw = this.Z * this.W * 2f;

            val m02 = xz + yw;
            val m12 = yz - xw;
            val m22 = 1f - (xx + yy);
            return Direction(m02, m12, m22)
        }

    public fun Mul(rotation: Rotation): Rotation {
        return Rotation(
            this.W * rotation.X + this.X * rotation.W + this.Y * rotation.Z - this.Z * rotation.Y,
            this.W * rotation.Y + this.Y * rotation.W + this.Z * rotation.X - this.X * rotation.Z,
            this.W * rotation.Z + this.Z * rotation.W + this.X * rotation.Y - this.Y * rotation.X,
            this.W * rotation.W - this.X * rotation.X - this.Y * rotation.Y - this.Z * rotation.Z,
        );
    }

    public fun Dot(rotation: Rotation): Float {
        return this.X * rotation.X + this.Y * rotation.Y + this.Z * rotation.Z + this.W * rotation.W;
    }

    public fun Angle(rotation: Rotation): Float {
        var dot = this.Dot(rotation)
        if (dot < 0f) {
            dot = -dot
        }
        val theta = Math.Acos(dot)
        return theta * Math.RadToDeg * 2f
    }

}
