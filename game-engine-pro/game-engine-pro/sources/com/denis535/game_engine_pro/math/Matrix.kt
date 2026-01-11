package com.denis535.game_engine_pro.math

// Column-major
//    |  0   1   2   3
// ---+----------------
// 0  | m00 m10 m20 m30 - right,   translation.x
// 1  | m01 m11 m21 m31 - up,      translation.y
// 2  | m02 m12 m22 m32 - forward, translation.z
// 3  | m03 m13 m23 m33 - 0, 0, 0, 1

public data class Matrix(
    public val m00: Float,
    public val m10: Float,
    public val m20: Float,
    public val m30: Float,
    public val m01: Float,
    public val m11: Float,
    public val m21: Float,
    public val m31: Float,
    public val m02: Float,
    public val m12: Float,
    public val m22: Float,
    public val m32: Float,
    public val m03: Float,
    public val m13: Float,
    public val m23: Float,
    public val m33: Float,
) {
    public companion object {

        public val Identity: Matrix = Matrix(
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f,
        );

        public fun Translation(position: Position): Matrix {
            return Matrix(
                1f, 0f, 0f, position.X,
                0f, 1f, 0f, position.Y,
                0f, 0f, 1f, position.Z,
                0f, 0f, 0f, 1f,
            );
        }

        public fun Rotation(rotation: Rotation): Matrix {
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

            return Matrix(
                m00, m10, m20, 0f,
                m01, m11, m21, 0f,
                m02, m12, m22, 0f,
                0f, 0f, 0f, 1f,
            )
        }

        public fun Scale(scale: Scale): Matrix {
            return Matrix(
                scale.X, 0f, 0f, 0f,
                0f, scale.Y, 0f, 0f,
                0f, 0f, scale.Z, 0f,
                0f, 0f, 0f, 1f,
            );
        }

        public fun TRS(position: Position, rotation: Rotation, scale: Scale): Matrix {
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

            return Matrix(
                m00 * scale.X, m10 * scale.X, m20 * scale.X, position.X,
                m01 * scale.Y, m11 * scale.Y, m21 * scale.Y, position.Y,
                m02 * scale.Z, m12 * scale.Z, m22 * scale.Z, position.Z,
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
        ): Matrix {
            val sx = 2f / (right - left)
            val sy = 2f / (top - bottom)
            val sz = -2f / (zFar - zNear)
            val tx = -(right + left) / (right - left)
            val ty = -(top + bottom) / (top - bottom)
            val tz = -(zFar + zNear) / (zFar - zNear)
            return Matrix(
                sx, 0f, 0f, 0f,
                0f, sy, 0f, 0f,
                0f, 0f, sz, 0f,
                tx, ty, tz, 1f,
            )
        }

        public fun Perspective(
            fov: Float,
            aspect: Float,
            zNear: Float,
            zFar: Float,
        ): Matrix {
            val tanX = Math.Tan(fov * Math.DegToRad / 2) * aspect
            val tanY = Math.Tan(fov * Math.DegToRad / 2)
            val left = -tanX * zNear
            val right = +tanX * zNear
            val bottom = -tanY * zNear
            val top = +tanY * zNear
            return Frustum(left, right, bottom, top, zNear, zFar)
        }

        public fun Frustum(
            left: Float,
            right: Float,
            bottom: Float,
            top: Float,
            zNear: Float,
            zFar: Float,
        ): Matrix {
            val sx = (2f * zNear) / (right - left)
            val sy = (2f * zNear) / (top - bottom)
            val sz = -(zFar + zNear) / (zFar - zNear)
            val ox = (right + left) / (right - left)
            val oy = (top + bottom) / (top - bottom)
            val tz = -(2f * zFar * zNear) / (zFar - zNear)
            return Matrix(
                sx, 0f, 0f, 0f,
                0f, sy, 0f, 0f,
                ox, oy, sz, -1f,
                0f, 0f, tz, 0f,
            )
        }

    }

    public val Determinant: Float
        get() {
            // m00 m10 m20 m30
            // m01 m11 m21 m31
            // m02 m12 m22 m32
            // m03 m13 m23 m33
            val minor00 = m00 * m11 - m01 * m10
            val minor01 = m00 * m12 - m02 * m10
            val minor02 = m00 * m13 - m03 * m10
            val minor03 = m01 * m12 - m02 * m11
            val minor04 = m01 * m13 - m03 * m11
            val minor05 = m02 * m13 - m03 * m12
            val minor06 = m20 * m31 - m21 * m30
            val minor07 = m20 * m32 - m22 * m30
            val minor08 = m20 * m33 - m23 * m30
            val minor09 = m21 * m32 - m22 * m31
            val minor10 = m21 * m33 - m23 * m31
            val minor11 = m22 * m33 - m23 * m32
            return minor00 * minor11 - minor01 * minor10 + minor02 * minor09 + minor03 * minor08 - minor04 * minor07 + minor05 * minor06
        }

    public val Inversed: Matrix
        get() {
            // m00 m10 m20 m30
            // m01 m11 m21 m31
            // m02 m12 m22 m32
            // m03 m13 m23 m33
            val minor00 = m00 * m11 - m01 * m10
            val minor01 = m00 * m12 - m02 * m10
            val minor02 = m00 * m13 - m03 * m10
            val minor03 = m01 * m12 - m02 * m11
            val minor04 = m01 * m13 - m03 * m11
            val minor05 = m02 * m13 - m03 * m12
            val minor06 = m20 * m31 - m21 * m30
            val minor07 = m20 * m32 - m22 * m30
            val minor08 = m20 * m33 - m23 * m30
            val minor09 = m21 * m32 - m22 * m31
            val minor10 = m21 * m33 - m23 * m31
            val minor11 = m22 * m33 - m23 * m32
            val determinant = minor00 * minor11 - minor01 * minor10 + minor02 * minor09 + minor03 * minor08 - minor04 * minor07 + minor05 * minor06
            val m00 = (+this.m11 * minor11 - this.m12 * minor10 + this.m13 * minor09) / determinant
            val m10 = (-this.m10 * minor11 + this.m12 * minor08 - this.m13 * minor07) / determinant
            val m20 = (+this.m10 * minor10 - this.m11 * minor08 + this.m13 * minor06) / determinant
            val m30 = (-this.m10 * minor09 + this.m11 * minor07 - this.m12 * minor06) / determinant
            val m01 = (-this.m01 * minor11 + this.m02 * minor10 - this.m03 * minor09) / determinant
            val m11 = (+this.m00 * minor11 - this.m02 * minor08 + this.m03 * minor07) / determinant
            val m21 = (-this.m00 * minor10 + this.m01 * minor08 - this.m03 * minor06) / determinant
            val m31 = (+this.m00 * minor09 - this.m01 * minor07 + this.m02 * minor06) / determinant
            val m02 = (+this.m31 * minor05 - this.m32 * minor04 + this.m33 * minor03) / determinant
            val m12 = (-this.m30 * minor05 + this.m32 * minor02 - this.m33 * minor01) / determinant
            val m22 = (+this.m30 * minor04 - this.m31 * minor02 + this.m33 * minor00) / determinant
            val m32 = (-this.m30 * minor03 + this.m31 * minor01 - this.m32 * minor00) / determinant
            val m03 = (-this.m21 * minor05 + this.m22 * minor04 - this.m23 * minor03) / determinant
            val m13 = (+this.m20 * minor05 - this.m22 * minor02 + this.m23 * minor01) / determinant
            val m23 = (-this.m20 * minor04 + this.m21 * minor02 - this.m23 * minor00) / determinant
            val m33 = (+this.m20 * minor03 - this.m21 * minor01 + this.m22 * minor00) / determinant
            return Matrix(
                m00, m10, m20, m30,
                m01, m11, m21, m31,
                m02, m12, m22, m32,
                m03, m13, m23, m33,
            )
        }

    public fun TransformPoint(position: Position): Position {
        val x = this.m00 * position.X + this.m01 * position.Y + this.m02 * position.Z + this.m03;
        val y = this.m10 * position.X + this.m11 * position.Y + this.m12 * position.Z + this.m13;
        val z = this.m20 * position.X + this.m21 * position.Y + this.m22 * position.Z + this.m23;
        val w = this.m30 * position.X + this.m31 * position.Y + this.m32 * position.Z + this.m33;
        return Position(
            x / w,
            y / w,
            z / w,
        )
    }

    public fun TransformDirection(direction: Direction): Direction {
        val x = this.m00 * direction.X + this.m01 * direction.Y + this.m02 * direction.Z
        val y = this.m10 * direction.X + this.m11 * direction.Y + this.m12 * direction.Z
        val z = this.m20 * direction.X + this.m21 * direction.Y + this.m22 * direction.Z
        return Direction(
            x,
            y,
            z,
        )
    }

    public operator fun times(other: Matrix): Matrix {
        val m00 = this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20 + this.m03 * other.m30;
        val m10 = this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20 + this.m13 * other.m30;
        val m20 = this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20 + this.m23 * other.m30;
        val m30 = this.m30 * other.m00 + this.m31 * other.m10 + this.m32 * other.m20 + this.m33 * other.m30;
        val m01 = this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21 + this.m03 * other.m31;
        val m11 = this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21 + this.m13 * other.m31;
        val m21 = this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21 + this.m23 * other.m31;
        val m31 = this.m30 * other.m01 + this.m31 * other.m11 + this.m32 * other.m21 + this.m33 * other.m31;
        val m02 = this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22 + this.m03 * other.m32;
        val m12 = this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22 + this.m13 * other.m32;
        val m22 = this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22 + this.m23 * other.m32;
        val m32 = this.m30 * other.m02 + this.m31 * other.m12 + this.m32 * other.m22 + this.m33 * other.m32;
        val m03 = this.m00 * other.m03 + this.m01 * other.m13 + this.m02 * other.m23 + this.m03 * other.m33;
        val m13 = this.m10 * other.m03 + this.m11 * other.m13 + this.m12 * other.m23 + this.m13 * other.m33;
        val m23 = this.m20 * other.m03 + this.m21 * other.m13 + this.m22 * other.m23 + this.m23 * other.m33;
        val m33 = this.m30 * other.m03 + this.m31 * other.m13 + this.m32 * other.m23 + this.m33 * other.m33;
        return Matrix(
            m00, m10, m20, m30,
            m01, m11, m21, m31,
            m02, m12, m22, m32,
            m03, m13, m23, m33,
        )
    }

}
