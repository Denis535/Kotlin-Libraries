package com.denis535.game_engine_pro

public data class Quaternion(
    public val X: Float,
    public val Y: Float,
    public val Z: Float,
    public val W: Float,
) {
    public companion object {

        public val Identity: Quaternion = Quaternion(0f, 0f, 0f, 1f)

        public fun Axes(axisX: Vector3, axisY: Vector3, axisZ: Vector3): Quaternion {
            val axisX_X = axisX.X
            val axisX_Y = axisX.Y
            val axisX_Z = axisX.Z

            val axisY_X = axisY.X
            val axisY_Y = axisY.Y
            val axisY_Z = axisY.Z

            val axisZ_X = axisZ.X
            val axisZ_Y = axisZ.Y
            val axisZ_Z = axisZ.Z

            if (axisX_X + axisY_Y + axisZ_Z > 0f) {
                val s = Math.Sqrt(axisX_X + axisY_Y + axisZ_Z + 1f) * 2f
                return Quaternion(
                    (axisY_Z - axisZ_Y) / s,
                    (axisZ_X - axisX_Z) / s,
                    (axisX_Y - axisY_X) / s,
                    0.25f * s,
                )
            }
            if (axisX_X > axisY_Y && axisX_X > axisZ_Z) {
                val s = Math.Sqrt(1f + axisX_X - axisY_Y - axisZ_Z) * 2f
                return Quaternion(
                    0.25f * s,
                    (axisY_X + axisX_Y) / s,
                    (axisZ_X + axisX_Z) / s,
                    (axisY_Z - axisZ_Y) / s,
                )
            }
            if (axisY_Y > axisZ_Z) {
                val s = Math.Sqrt(1f + axisY_Y - axisX_X - axisZ_Z) * 2f
                return Quaternion(
                    (axisY_X + axisX_Y) / s,
                    0.25f * s,
                    (axisZ_Y + axisY_Z) / s,
                    (axisZ_X - axisX_Z) / s,
                )
            }
            run {
                val s = Math.Sqrt(1f + axisZ_Z - axisX_X - axisY_Y) * 2f
                return Quaternion(
                    (axisZ_X + axisX_Z) / s,
                    (axisZ_Y + axisY_Z) / s,
                    0.25f * s,
                    (axisX_Y - axisY_X) / s,
                )
            }
        }

        public fun AngleAxis(angle: Float, axis: Vector3): Quaternion {
            val sin = Math.Sin(angle / 2f * Math.DEG_TO_RAD)
            val cos = Math.Cos(angle / 2f * Math.DEG_TO_RAD)
            return Quaternion(
                axis.X * sin,
                axis.Y * sin,
                axis.Z * sin,
                cos,
            )
        }

        public fun AngleAxisX(angleX: Float): Quaternion {
            // https://github.com/Unity-Technologies/Unity.Mathematics/blob/master/src/Unity.Mathematics/quaternion.cs#L352
            val sinX = Math.Sin(angleX / 2f * Math.DEG_TO_RAD)
            val cosX = Math.Cos(angleX / 2f * Math.DEG_TO_RAD)
            return Quaternion(
                sinX,
                0f,
                0f,
                cosX,
            )
        }

        public fun AngleAxisY(angleY: Float): Quaternion {
            val sinY = Math.Sin(angleY / 2f * Math.DEG_TO_RAD)
            val cosY = Math.Cos(angleY / 2f * Math.DEG_TO_RAD)
            return Quaternion(
                0f,
                sinY,
                0f,
                cosY,
            )
        }

        public fun AngleAxisZ(angleZ: Float): Quaternion {
            val sinZ = Math.Sin(angleZ / 2f * Math.DEG_TO_RAD)
            val cosZ = Math.Cos(angleZ / 2f * Math.DEG_TO_RAD)
            return Quaternion(
                0f,
                0f,
                sinZ,
                cosZ,
            )
        }

        public fun Angles(angleX: Float, angleY: Float, angleZ: Float): Quaternion {
            // https://github.com/Unity-Technologies/Unity.Mathematics/blob/master/src/Unity.Mathematics/quaternion.cs#L155
            // Y - up
            // X - right
            // Z - forward
            return this.AngleAxisZ(angleZ) // rotate around forward (roll) axis
                .Mul(this.AngleAxisX(angleX)) // rotate around right (pitch) axis
                .Mul(this.AngleAxisY(angleY)) // rotate around up (yaw) axis
        }

        public fun Direction(axisZ: Vector3, axisY: Vector3 = Vector3.AxisY): Quaternion {
            // X - right
            // Y - top
            // Z - forward
            val axisX = axisY.Cross(axisZ).Normalized
            val axisY = axisZ.Cross(axisX)
            return this.Axes(axisX, axisY, axisZ)
        }

        public fun Slerp(v0: Quaternion, v1: Quaternion, t: Float): Quaternion {
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
                return Quaternion(
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

                return Quaternion(
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

    public val Normalized: Quaternion
        get() {
            val length = this.Length
            return Quaternion(this.X / length, this.Y / length, this.Z / length, this.W / length)
        }

    public val AxisX: Vector3
        get() {
//            val xx = this.X * this.X * 2f
            val xy = this.X * this.Y * 2f
            val xz = this.X * this.Z * 2f
//            val xw = this.X * this.W * 2f

            val yy = this.Y * this.Y * 2f
//            val yz = this.Y * this.Z * 2f
            val yw = this.Y * this.W * 2f

            val zz = this.Z * this.Z * 2f
            val zw = this.Z * this.W * 2f

            val m00 = 1f - (yy + zz)
            val m10 = xy + zw
            val m20 = xz - yw
            return Vector3(m00, m10, m20)
        }

    public val AxisY: Vector3
        get() {
            val xx = this.X * this.X * 2f
            val xy = this.X * this.Y * 2f
//            val xz = this.X * this.Z * 2f
            val xw = this.X * this.W * 2f

//            val yy = this.Y * this.Y * 2f
            val yz = this.Y * this.Z * 2f
//            val yw = this.Y * this.W * 2f

            val zz = this.Z * this.Z * 2f
            val zw = this.Z * this.W * 2f

            val m01 = xy - zw
            val m11 = 1f - (xx + zz)
            val m21 = yz + xw
            return Vector3(m01, m11, m21)
        }

    public val AxisZ: Vector3
        get() {
            val xx = this.X * this.X * 2f
//            val xy = this.X * this.Y * 2f
            val xz = this.X * this.Z * 2f
            val xw = this.X * this.W * 2f

            val yy = this.Y * this.Y * 2f
            val yz = this.Y * this.Z * 2f
            val yw = this.Y * this.W * 2f

//            val zz = this.Z * this.Z * 2f
//            val zw = this.Z * this.W * 2f

            val m02 = xz + yw
            val m12 = yz - xw
            val m22 = 1f - (xx + yy)
            return Vector3(m02, m12, m22)
        }

    public fun Mul(quaternion: Quaternion): Quaternion {
        // https://github.com/Unity-Technologies/Unity.Mathematics/blob/master/src/Unity.Mathematics/quaternion.cs#L630
        return Quaternion(
            this.W * quaternion.X + this.X * quaternion.W + this.Y * quaternion.Z - this.Z * quaternion.Y,
            this.W * quaternion.Y + this.Y * quaternion.W + this.Z * quaternion.X - this.X * quaternion.Z,
            this.W * quaternion.Z + this.Z * quaternion.W + this.X * quaternion.Y - this.Y * quaternion.X,
            this.W * quaternion.W - this.X * quaternion.X - this.Y * quaternion.Y - this.Z * quaternion.Z,
        )
    }

    public fun Dot(quaternion: Quaternion): Float {
        return this.X * quaternion.X + this.Y * quaternion.Y + this.Z * quaternion.Z + this.W * quaternion.W
    }

    public fun Angle(quaternion: Quaternion): Float {
        var dot = this.Dot(quaternion)
        if (dot < 0f) {
            dot = -dot
        }
        val theta = Math.Acos(dot)
        return theta * 2f * Math.RAD_TO_DEG
    }

}
