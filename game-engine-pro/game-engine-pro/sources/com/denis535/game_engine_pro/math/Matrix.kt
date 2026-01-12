package com.denis535.game_engine_pro.math

// Column-major
//    |  0   1   2   3
// ---+----------------
// 0  | m00 m10 m20 m30 - x-direction, translation.x
// 1  | m01 m11 m21 m31 - y-direction, translation.y
// 2  | m02 m12 m22 m32 - z-direction, translation.z
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

        public fun Translation(translation: Position): Matrix {
            return Matrix(
                1f, 0f, 0f, translation.X,
                0f, 1f, 0f, translation.Y,
                0f, 0f, 1f, translation.Z,
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

        public fun TRS(translation: Position, rotation: Rotation, scale: Scale): Matrix {
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
                m00 * scale.X, m10 * scale.X, m20 * scale.X, translation.X,
                m01 * scale.Y, m11 * scale.Y, m21 * scale.Y, translation.Y,
                m02 * scale.Z, m12 * scale.Z, m22 * scale.Z, translation.Z,
                0f, 0f, 0f, 1f,
            )
        }

        public fun TRS(translation: Matrix, rotation: Matrix, scale: Matrix): Matrix {
            return translation.Mul(rotation).Mul(scale)
        }

        public fun Ortho(
            left: Float,
            right: Float,
            bottom: Float,
            top: Float,
            zNear: Float,
            zFar: Float,
        ): Matrix {
            val xScale = 2f / (right - left)
            val yScale = 2f / (top - bottom)
            val zScale = -2f / (zFar - zNear)
            val xOffset = -(right + left) / (right - left)
            val yOffset = -(top + bottom) / (top - bottom)
            val zOffset = -(zFar + zNear) / (zFar - zNear)
            return Matrix(
                xScale, 0f, 0f, 0f,
                0f, yScale, 0f, 0f,
                0f, 0f, zScale, 0f,
                xOffset, yOffset, zOffset, 1f,
            )
        }

        public fun Frustum(
            left: Float,
            right: Float,
            bottom: Float,
            top: Float,
            zNear: Float,
            zFar: Float,
        ): Matrix {
            val xScale = 2f * zNear / (right - left)
            val yScale = 2f * zNear / (top - bottom)
            val zScale = -(zFar + zNear) / (zFar - zNear)
            val xOffset = (right + left) / (right - left)
            val yOffset = (top + bottom) / (top - bottom)
            val zOffset = -2f * zFar * zNear / (zFar - zNear)
            return Matrix(
                xScale, 0f, 0f, 0f,
                0f, yScale, 0f, 0f,
                xOffset, yOffset, zScale, -1f,
                0f, 0f, zOffset, 0f,
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

    }

    public fun Mul(matrix: Matrix): Matrix {
        val m00 = this.m00 * matrix.m00 + this.m01 * matrix.m10 + this.m02 * matrix.m20 + this.m03 * matrix.m30;
        val m10 = this.m10 * matrix.m00 + this.m11 * matrix.m10 + this.m12 * matrix.m20 + this.m13 * matrix.m30;
        val m20 = this.m20 * matrix.m00 + this.m21 * matrix.m10 + this.m22 * matrix.m20 + this.m23 * matrix.m30;
        val m30 = this.m30 * matrix.m00 + this.m31 * matrix.m10 + this.m32 * matrix.m20 + this.m33 * matrix.m30;
        val m01 = this.m00 * matrix.m01 + this.m01 * matrix.m11 + this.m02 * matrix.m21 + this.m03 * matrix.m31;
        val m11 = this.m10 * matrix.m01 + this.m11 * matrix.m11 + this.m12 * matrix.m21 + this.m13 * matrix.m31;
        val m21 = this.m20 * matrix.m01 + this.m21 * matrix.m11 + this.m22 * matrix.m21 + this.m23 * matrix.m31;
        val m31 = this.m30 * matrix.m01 + this.m31 * matrix.m11 + this.m32 * matrix.m21 + this.m33 * matrix.m31;
        val m02 = this.m00 * matrix.m02 + this.m01 * matrix.m12 + this.m02 * matrix.m22 + this.m03 * matrix.m32;
        val m12 = this.m10 * matrix.m02 + this.m11 * matrix.m12 + this.m12 * matrix.m22 + this.m13 * matrix.m32;
        val m22 = this.m20 * matrix.m02 + this.m21 * matrix.m12 + this.m22 * matrix.m22 + this.m23 * matrix.m32;
        val m32 = this.m30 * matrix.m02 + this.m31 * matrix.m12 + this.m32 * matrix.m22 + this.m33 * matrix.m32;
        val m03 = this.m00 * matrix.m03 + this.m01 * matrix.m13 + this.m02 * matrix.m23 + this.m03 * matrix.m33;
        val m13 = this.m10 * matrix.m03 + this.m11 * matrix.m13 + this.m12 * matrix.m23 + this.m13 * matrix.m33;
        val m23 = this.m20 * matrix.m03 + this.m21 * matrix.m13 + this.m22 * matrix.m23 + this.m23 * matrix.m33;
        val m33 = this.m30 * matrix.m03 + this.m31 * matrix.m13 + this.m32 * matrix.m23 + this.m33 * matrix.m33;
        return Matrix(
            m00, m10, m20, m30,
            m01, m11, m21, m31,
            m02, m12, m22, m32,
            m03, m13, m23, m33,
        )
    }

    public fun TransformPosition(position: Position): Position {
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

}
