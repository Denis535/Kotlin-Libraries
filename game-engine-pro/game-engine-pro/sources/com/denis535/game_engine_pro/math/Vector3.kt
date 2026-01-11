package com.denis535.game_engine_pro.math

public class Vector3 {
    public companion object {

        public fun Dot(v0: Vector3, v1: Vector3): Float {
            return v0.x * v1.x + v0.y * v1.y + v0.z * v1.z;
        }

        public fun Cross(v0: Vector3, v1: Vector3): Vector3 {
            return Vector3(v0.y * v1.z - v0.z * v1.y, v0.z * v1.x - v0.x * v1.z, v0.x * v1.y - v0.y * v1.x);
        }

        public fun Lerp(v0: Vector3, v1: Vector3, t: Vector3): Vector3 {
            return v0 + (v1 - v0) * t;
        }

        public fun InverseLerp(v0: Vector3, v1: Vector3, value: Vector3): Vector3 {
            return (value - v0) / (v1 - v0);
        }

        public fun Min(v0: Vector3, v1: Vector3): Vector3 {
            return Vector3(Math.Min(v0.x, v1.x), Math.Min(v0.y, v1.y), Math.Min(v0.z, v1.z));
        }

        public fun Max(v0: Vector3, v1: Vector3): Vector3 {
            return Vector3(Math.Max(v0.x, v1.x), Math.Max(v0.y, v1.y), Math.Max(v0.z, v1.z));
        }

    }

    public val x: Float
    public val y: Float
    public val z: Float

    public val Length: Float
        get() {
            return Math.Sqrt(this.x * this.x + this.y * this.y)
        }

    public val LengthSquared: Float
        get() {
            return this.x * this.x + this.y * this.y
        }

    public val Normalized: Vector3
        get() {
            val length = this.Length;
            return Vector3(this.x / length, this.y / length, this.z / length)
        }

    public constructor(x: Float, y: Float, z: Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    public operator fun unaryMinus(): Vector3 = Vector3(-this.x, -this.y, -this.z)

    public operator fun plus(value: Float): Vector3 = Vector3(this.x + value, this.y + value, this.z + value)
    public operator fun minus(value: Float): Vector3 = Vector3(this.x - value, this.y - value, this.z - value)

    public operator fun times(value: Float): Vector3 = Vector3(this.x * value, this.y * value, this.z * value)
    public operator fun div(value: Float): Vector3 = Vector3(this.x / value, this.y / value, this.z / value)

    public operator fun plus(value: Vector3): Vector3 = Vector3(this.x + value.x, this.y + value.y, this.z + value.z)
    public operator fun minus(value: Vector3): Vector3 = Vector3(this.x - value.x, this.y - value.y, this.z - value.z)

    public operator fun times(value: Vector3): Vector3 = Vector3(this.x * value.x, this.y * value.y, this.z * value.z)
    public operator fun div(value: Vector3): Vector3 = Vector3(this.x / value.x, this.y / value.y, this.z / value.z)

    public override fun toString(): String {
        return "Vector3(${this.x}, ${this.y}, ${this.z})"
    }

}
