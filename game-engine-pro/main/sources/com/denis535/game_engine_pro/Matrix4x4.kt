package com.denis535.game_engine_pro

// Column-major
// m00 m10 m20 m30 - x-column: x-axis, position.x
// m01 m11 m21 m31 - y-column: y-axis, position.y
// m02 m12 m22 m32 - z-column: z-axis, position.z
// m03 m13 m23 m33 - w-column: 0, 0, 0, 1

public data class Matrix4x4(
    public val m00: Float, // 0-X
    public val m10: Float, // 0-Y
    public val m20: Float, // 0-Z
    public val m30: Float, // 0-W
    public val m01: Float, // 1-X
    public val m11: Float, // 1-Y
    public val m21: Float, // 1-Z
    public val m31: Float, // 1-W
    public val m02: Float, // 2-X
    public val m12: Float, // 2-Y
    public val m22: Float, // 2-Z
    public val m32: Float, // 2-W
    public val m03: Float, // 3-X
    public val m13: Float, // 3-Y
    public val m23: Float, // 3-Z
    public val m33: Float, // 3-W
) {
    public companion object {

        public val Identity: Matrix4x4 = Matrix4x4(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f,
        )

        public fun TRS(position: Vector3, rotation: Quaternion, scale: Vector3): Matrix4x4 {
            return this.TRS(this.Translation(position), this.Rotation(rotation), this.Scale(scale))
        }

        public fun TRS(position: Matrix4x4, rotation: Matrix4x4, scale: Matrix4x4): Matrix4x4 {
            return scale.Mul(rotation).Mul(position)
        }

        public fun Translation(position: Vector3): Matrix4x4 {
            return Matrix4x4(
                1f, 0f, 0f, position.X,
                0f, 1f, 0f, position.Y,
                0f, 0f, 1f, position.Z,
                0f, 0f, 0f, 1f,
            )
        }

        public fun Rotation(rotation: Quaternion): Matrix4x4 {
            val xx = rotation.X * rotation.X * 2f
            val xy = rotation.X * rotation.Y * 2f
            val xz = rotation.X * rotation.Z * 2f
            val xw = rotation.X * rotation.W * 2f

            val yy = rotation.Y * rotation.Y * 2f
            val yz = rotation.Y * rotation.Z * 2f
            val yw = rotation.Y * rotation.W * 2f

            val zz = rotation.Z * rotation.Z * 2f
            val zw = rotation.Z * rotation.W * 2f

            val m00 = 1f - (yy + zz)
            val m10 = xy + zw
            val m20 = xz - yw

            val m01 = xy - zw
            val m11 = 1f - (xx + zz)
            val m21 = yz + xw

            val m02 = xz + yw
            val m12 = yz - xw
            val m22 = 1f - (xx + yy)

            return Matrix4x4(
                m00, m10, m20, 0f,
                m01, m11, m21, 0f,
                m02, m12, m22, 0f,
                0f, 0f, 0f, 1f,
            )
        }

        public fun Scale(scale: Vector3): Matrix4x4 {
            return Matrix4x4(
                scale.X, 0f, 0f, 0f,
                0f, scale.Y, 0f, 0f,
                0f, 0f, scale.Z, 0f,
                0f, 0f, 0f, 1f,
            )
        }

        public fun Ortho(
            left: Float,
            right: Float,
            bottom: Float,
            top: Float,
            zNear: Float,
            zFar: Float,
        ): Matrix4x4 {
            val scaleX = 2f / (right - left)
            val scaleY = 2f / (top - bottom)
            val scaleZ = -2f / (zFar - zNear)
            val offsetX = -(right + left) / (right - left)
            val offsetY = -(top + bottom) / (top - bottom)
            val offsetZ = -(zFar + zNear) / (zFar - zNear)
            return Matrix4x4(
                scaleX, 0f, 0f, 0f,
                0f, scaleY, 0f, 0f,
                0f, 0f, scaleZ, 0f,
                offsetX, offsetY, offsetZ, 1f,
            )
        }

        public fun Frustum(
            left: Float,
            right: Float,
            bottom: Float,
            top: Float,
            zNear: Float,
            zFar: Float,
        ): Matrix4x4 {
            val scaleX = 2f * zNear / (right - left)
            val scaleY = 2f * zNear / (top - bottom)
            val scaleZ = -(zFar + zNear) / (zFar - zNear)
            val offsetX = (right + left) / (right - left)
            val offsetY = (top + bottom) / (top - bottom)
            val offsetZ = -2f * zFar * zNear / (zFar - zNear)
            return Matrix4x4(
                scaleX, 0f, 0f, 0f,
                0f, scaleY, 0f, 0f,
                offsetX, offsetY, scaleZ, -1f,
                0f, 0f, offsetZ, 0f,
            )
        }

        public fun Perspective(
            fov: Float,
            aspect: Float,
            zNear: Float,
            zFar: Float,
        ): Matrix4x4 {
            val tanX = Math.Tan(fov * Math.DEG_TO_RAD / 2) * aspect
            val tanY = Math.Tan(fov * Math.DEG_TO_RAD / 2)
            val left = -tanX * zNear
            val right = +tanX * zNear
            val bottom = -tanY * zNear
            val top = +tanY * zNear
            return this.Frustum(left, right, bottom, top, zNear, zFar)
        }

    }

    public val Position: Vector3
        get() {
            return Vector3(this.m30, this.m31, this.m32)
        }

    public val AxisX: Vector3
        get() {
            return Vector3(this.m00, this.m10, this.m20)
        }

    public val AxisY: Vector3
        get() {
            return Vector3(this.m01, this.m11, this.m21)
        }

    public val AxisZ: Vector3
        get() {
            return Vector3(this.m02, this.m12, this.m22)
        }

    public fun Mul(matrix: Matrix4x4): Matrix4x4 {
        val m00 = this.m00 * matrix.m00 + this.m01 * matrix.m10 + this.m02 * matrix.m20 + this.m03 * matrix.m30
        val m01 = this.m00 * matrix.m01 + this.m01 * matrix.m11 + this.m02 * matrix.m21 + this.m03 * matrix.m31
        val m02 = this.m00 * matrix.m02 + this.m01 * matrix.m12 + this.m02 * matrix.m22 + this.m03 * matrix.m32
        val m03 = this.m00 * matrix.m03 + this.m01 * matrix.m13 + this.m02 * matrix.m23 + this.m03 * matrix.m33
        val m10 = this.m10 * matrix.m00 + this.m11 * matrix.m10 + this.m12 * matrix.m20 + this.m13 * matrix.m30
        val m11 = this.m10 * matrix.m01 + this.m11 * matrix.m11 + this.m12 * matrix.m21 + this.m13 * matrix.m31
        val m12 = this.m10 * matrix.m02 + this.m11 * matrix.m12 + this.m12 * matrix.m22 + this.m13 * matrix.m32
        val m13 = this.m10 * matrix.m03 + this.m11 * matrix.m13 + this.m12 * matrix.m23 + this.m13 * matrix.m33
        val m20 = this.m20 * matrix.m00 + this.m21 * matrix.m10 + this.m22 * matrix.m20 + this.m23 * matrix.m30
        val m21 = this.m20 * matrix.m01 + this.m21 * matrix.m11 + this.m22 * matrix.m21 + this.m23 * matrix.m31
        val m22 = this.m20 * matrix.m02 + this.m21 * matrix.m12 + this.m22 * matrix.m22 + this.m23 * matrix.m32
        val m23 = this.m20 * matrix.m03 + this.m21 * matrix.m13 + this.m22 * matrix.m23 + this.m23 * matrix.m33
        val m30 = this.m30 * matrix.m00 + this.m31 * matrix.m10 + this.m32 * matrix.m20 + this.m33 * matrix.m30
        val m31 = this.m30 * matrix.m01 + this.m31 * matrix.m11 + this.m32 * matrix.m21 + this.m33 * matrix.m31
        val m32 = this.m30 * matrix.m02 + this.m31 * matrix.m12 + this.m32 * matrix.m22 + this.m33 * matrix.m32
        val m33 = this.m30 * matrix.m03 + this.m31 * matrix.m13 + this.m32 * matrix.m23 + this.m33 * matrix.m33
        return Matrix4x4(
            m00, m10, m20, m30,
            m01, m11, m21, m31,
            m02, m12, m22, m32,
            m03, m13, m23, m33,
        )
    }

    public fun MultiplyPoint(point: Vector3): Vector3 {
        return this.MultiplyPoint(point.X, point.Y, point.Z)
    }

    public fun MultiplyPoint(x: Float, y: Float, z: Float): Vector3 {
        val rx = this.m00 * x + this.m01 * y + this.m02 * z + this.m03
        val ry = this.m10 * x + this.m11 * y + this.m12 * z + this.m13
        val rz = this.m20 * x + this.m21 * y + this.m22 * z + this.m23
        val rw = this.m30 * x + this.m31 * y + this.m32 * z + this.m33
        return Vector3(
            rx / rw,
            ry / rw,
            rz / rw,
        )
    }

}
