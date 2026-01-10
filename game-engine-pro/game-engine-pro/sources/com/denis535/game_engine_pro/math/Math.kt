package com.denis535.game_engine_pro.math

import kotlin.math.*

public object Math {

    public const val PI = kotlin.math.PI

    public fun Sign(value: Float): Float {
        return sign(value)
    }

    public fun Sign(value: Int): Int {
        if (value < 0) return -1;
        if (value > 0) return 1;
        return 0;
    }

    public fun Abs(value: Float): Float {
        return abs(value)
    }

    public fun Abs(value: Int): Int {
        return abs(value)
    }

    public fun Truncate(value: Float): Float {
        return truncate(value)
    }

    public fun Floor(value: Float): Float {
        return floor(value)
    }

    public fun Ceil(value: Float): Float {
        return ceil(value)
    }

    public fun Round(value: Float): Float {
        return round(value)
    }

    public fun Sin(value: Float): Float {
        return sin(value)
    }

    public fun Cos(value: Float): Float {
        return cos(value)
    }

    public fun Tan(value: Float): Float {
        return tan(value)
    }

    public fun Asin(value: Float): Float {
        return asin(value)
    }

    public fun Acos(value: Float): Float {
        return acos(value)
    }

    public fun Atan(value: Float): Float {
        return atan(value)
    }

    public fun Atan2(y: Float, x: Float): Float {
        return atan2(y, x)
    }

    public fun Pow(value: Float, factor: Float): Float {
        return value.pow(factor)
    }

    public fun Sqrt(value: Float): Float {
        return sqrt(value)
    }

    public fun Clamp(value: Float, min: Float, max: Float): Float {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public fun Clamp(value: Int, min: Int, max: Int): Int {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    public fun Clamp01(value: Float): Float {
        if (value < 0f) return 0f;
        if (value > 1f) return 1f;
        return value;
    }

    public fun Clamp01(value: Int): Int {
        if (value < 0) return 0;
        if (value > 1) return 1;
        return value;
    }

    public fun Repeat(value: Float, length: Float): Float {
        require(length > 0)
        val mod = value % length
        return if (mod < 0f) mod + length else mod
    }

    public fun Repeat(value: Int, length: Int): Int {
        require(length > 0)
        val mod = value % length
        return if (mod < 0) mod + length else mod
    }

    public fun PingPong(value: Float, length: Float): Float {
        require(length > 0)
        val value = Repeat(value, length * 2f)
        return length - Abs(value - length)
    }

    public fun PingPong(value: Int, length: Int): Int {
        require(length > 0)
        val value = Repeat(value, length * 2)
        return length - Abs(value - length)
    }

    public fun Lerp(v0: Float, v2: Float, t: Float): Float {
        return v0 + (v2 - v0) * t;
    }

    public fun InverseLerp(t0: Float, t1: Float, value: Float): Float {
        if (t0 == t1) return 0f
        return (value - t0) / (t1 - t0);
    }

}
