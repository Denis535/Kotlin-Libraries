package com.denis535.game_engine_pro.math

// Column-major
//    |  0   1   2   3
// ---+----------------
// 0  | m00 m10 m20 m30 - right,   translation.x
// 1  | m01 m11 m21 m31 - up,      translation.y
// 2  | m02 m12 m22 m32 - forward, translation.z
// 3  | m03 m13 m23 m33 - 0, 0, 0, 1

public data class Matrix(
    public val m00: Float, //
    public val m10: Float, //
    public val m20: Float, //
    public val m30: Float, //
    public val m01: Float, //
    public val m11: Float, //
    public val m21: Float, //
    public val m31: Float, //
    public val m02: Float, //
    public val m12: Float, //
    public val m22: Float, //
    public val m32: Float, //
    public val m03: Float, //
    public val m13: Float, //
    public val m23: Float, //
    public val m33: Float  //
) {
    public companion object {

        public val Identity: Matrix = Matrix(
            1f, 0f, 0f, 0f, //
            0f, 1f, 0f, 0f, //
            0f, 0f, 1f, 0f, //
            0f, 0f, 0f, 1f, //
        );

        public fun Translation(point: Point): Matrix {
            return Matrix(
                1f, 0f, 0f, point.X, //
                0f, 1f, 0f, point.Y, //
                0f, 0f, 1f, point.Z, //
                0f, 0f, 0f, 1f,      //
            );
        }

//        public fun Rotation(rotation: Rotation): Matrix {
//            val xx = rotation.X * rotation.X * 2f;
//            val xy = rotation.X * rotation.Y * 2f;
//            val xz = rotation.X * rotation.Z * 2f;
//            val xw = rotation.X * rotation.W * 2f;
//
//            val yy = rotation.Y * rotation.Y * 2f;
//            val yz = rotation.Y * rotation.Z * 2f;
//            val yw = rotation.Y * rotation.W * 2f;
//
//            val zz = rotation.Z * rotation.Z * 2f;
//            val zw = rotation.Z * rotation.W * 2f;
//
//            val m00 = 1f - (yy + zz);
//            val m10 = xy + zw;
//            val m20 = xz - yw;
//
//            val m01 = xy - zw;
//            val m11 = 1f - (xx + zz);
//            val m21 = yz + xw;
//
//            val m02 = xz + yw;
//            val m12 = yz - xw;
//            val m22 = 1f - (xx + yy);
//
//            return Matrix(
//                m00, m10, m20, 0f,
//                m01, m11, m21, 0f,
//                m02, m12, m22, 0f,
//                0f, 0f, 0f, 1f,
//            )
//        }

        public fun Scale(scale: Scale): Matrix {
            return Matrix(
                scale.X, 0f, 0f, 0f, //
                0f, scale.Y, 0f, 0f, //
                0f, 0f, scale.Z, 0f, //
                0f, 0f, 0f, 1f, //
            );
        }

    }

    public fun TransformPoint(point: Point): Point {
        val x = this.m00 * point.X + this.m01 * point.Y + this.m02 * point.Z + this.m03;
        val y = this.m10 * point.X + this.m11 * point.Y + this.m12 * point.Z + this.m13;
        val z = this.m20 * point.X + this.m21 * point.Y + this.m22 * point.Z + this.m23;
        val w = this.m30 * point.X + this.m31 * point.Y + this.m32 * point.Z + this.m33;
        return Point(
            x / w, y / w, z / w
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
