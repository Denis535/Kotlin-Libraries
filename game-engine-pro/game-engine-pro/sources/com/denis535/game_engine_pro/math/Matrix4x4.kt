package com.denis535.game_engine_pro.math

// Column-major
// m00 m10 m20 m30 - x-column: x-direction, translation.x
// m01 m11 m21 m31 - y-column: y-direction, translation.y
// m02 m12 m22 m32 - z-column: z-direction, translation.z
// m03 m13 m23 m33 - w-column: 0, 0, 0, 1

public data class Matrix4x4(
    public val X0: Float,
    public val X1: Float,
    public val X2: Float,
    public val X3: Float,
    public val Y0: Float,
    public val Y1: Float,
    public val Y2: Float,
    public val Y3: Float,
    public val Z0: Float,
    public val Z1: Float,
    public val Z2: Float,
    public val Z3: Float,
    public val W0: Float,
    public val W1: Float,
    public val W2: Float,
    public val W3: Float,
) {
    public companion object {

        public val Identity: Matrix4x4 = Matrix4x4(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f,
        );

        public fun TRS(position: Vector3, rotation: Quaternion, scale: Vector3): Matrix4x4 {
            return TRS(Translation(position), Rotation(rotation), Scale(scale))
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
            );
        }

        public fun Rotation(rotation: Quaternion): Matrix4x4 {
            val xx = rotation.X * rotation.X * 2f;
            val xy = rotation.X * rotation.Y * 2f;
            val xz = rotation.X * rotation.Z * 2f;
            val xw = rotation.X * rotation.W * 2f;

            val yy = rotation.Y * rotation.Y * 2f;
            val yz = rotation.Y * rotation.Z * 2f;
            val yw = rotation.Y * rotation.W * 2f;

            val zz = rotation.Z * rotation.Z * 2f;
            val zw = rotation.Z * rotation.W * 2f;

            val m00 = 1f - (yy + zz);
            val m10 = xy + zw;
            val m20 = xz - yw;

            val m01 = xy - zw;
            val m11 = 1f - (xx + zz);
            val m21 = yz + xw;

            val m02 = xz + yw;
            val m12 = yz - xw;
            val m22 = 1f - (xx + yy);

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
            );
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
            val tanX = Math.Tan(fov * Math.DegToRad / 2) * aspect
            val tanY = Math.Tan(fov * Math.DegToRad / 2)
            val left = -tanX * zNear
            val right = +tanX * zNear
            val bottom = -tanY * zNear
            val top = +tanY * zNear
            return Frustum(left, right, bottom, top, zNear, zFar)
        }

    }

    public val Position: Vector3
        get() {
            return Vector3(X3, Y3, Z3)
        }

    public val AxisX: Vector3
        get() {
            return Vector3(X0, X1, X2)
        }

    public val AxisY: Vector3
        get() {
            return Vector3(Y0, Y1, Y2)
        }

    public val AxisZ: Vector3
        get() {
            return Vector3(Z0, Z1, Z2)
        }

    public fun Mul(matrix: Matrix4x4): Matrix4x4 {
        val m00 = this.X0 * matrix.X0 + this.Y0 * matrix.X1 + this.Z0 * matrix.X2 + this.W0 * matrix.X3;
        val m01 = this.X0 * matrix.Y0 + this.Y0 * matrix.Y1 + this.Z0 * matrix.Y2 + this.W0 * matrix.Y3;
        val m02 = this.X0 * matrix.Z0 + this.Y0 * matrix.Z1 + this.Z0 * matrix.Z2 + this.W0 * matrix.Z3;
        val m03 = this.X0 * matrix.W0 + this.Y0 * matrix.W1 + this.Z0 * matrix.W2 + this.W0 * matrix.W3;
        val m10 = this.X1 * matrix.X0 + this.Y1 * matrix.X1 + this.Z1 * matrix.X2 + this.W1 * matrix.X3;
        val m11 = this.X1 * matrix.Y0 + this.Y1 * matrix.Y1 + this.Z1 * matrix.Y2 + this.W1 * matrix.Y3;
        val m12 = this.X1 * matrix.Z0 + this.Y1 * matrix.Z1 + this.Z1 * matrix.Z2 + this.W1 * matrix.Z3;
        val m13 = this.X1 * matrix.W0 + this.Y1 * matrix.W1 + this.Z1 * matrix.W2 + this.W1 * matrix.W3;
        val m20 = this.X2 * matrix.X0 + this.Y2 * matrix.X1 + this.Z2 * matrix.X2 + this.W2 * matrix.X3;
        val m21 = this.X2 * matrix.Y0 + this.Y2 * matrix.Y1 + this.Z2 * matrix.Y2 + this.W2 * matrix.Y3;
        val m22 = this.X2 * matrix.Z0 + this.Y2 * matrix.Z1 + this.Z2 * matrix.Z2 + this.W2 * matrix.Z3;
        val m23 = this.X2 * matrix.W0 + this.Y2 * matrix.W1 + this.Z2 * matrix.W2 + this.W2 * matrix.W3;
        val m30 = this.X3 * matrix.X0 + this.Y3 * matrix.X1 + this.Z3 * matrix.X2 + this.W3 * matrix.X3;
        val m31 = this.X3 * matrix.Y0 + this.Y3 * matrix.Y1 + this.Z3 * matrix.Y2 + this.W3 * matrix.Y3;
        val m32 = this.X3 * matrix.Z0 + this.Y3 * matrix.Z1 + this.Z3 * matrix.Z2 + this.W3 * matrix.Z3;
        val m33 = this.X3 * matrix.W0 + this.Y3 * matrix.W1 + this.Z3 * matrix.W2 + this.W3 * matrix.W3;
        return Matrix4x4(
            m00, m10, m20, m30,
            m01, m11, m21, m31,
            m02, m12, m22, m32,
            m03, m13, m23, m33,
        )
    }

    public fun MultiplyPoint(point: Vector3): Vector3 {
        val x = this.X0 * point.X + this.Y0 * point.Y + this.Z0 * point.Z + this.W0;
        val y = this.X1 * point.X + this.Y1 * point.Y + this.Z1 * point.Z + this.W1;
        val z = this.X2 * point.X + this.Y2 * point.Y + this.Z2 * point.Z + this.W2;
        val w = this.X3 * point.X + this.Y3 * point.Y + this.Z3 * point.Z + this.W3;
        return Vector3(
            x / w,
            y / w,
            z / w,
        )
    }

}
