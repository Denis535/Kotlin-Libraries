package com.denis535.game_engine_pro

public interface Transform {

    public val IsStatic: Boolean
    public val Position: Vector3
    public val Rotation: Quaternion
    public val Scale: Vector3

    public fun TransformPoint(point: Vector3): Vector3 {
        return TransformPoint(this.Position, this.Rotation, this.Scale, point.X, point.Y, point.Z)
    }

    public fun TransformPoint(x: Float, y: Float, z: Float): Vector3 {
        return TransformPoint(this.Position, this.Rotation, this.Scale, x, y, z)
    }

    public fun InverseTransformPoint(point: Vector3): Vector3 {
        return InverseTransformPoint(this.Position, this.Rotation, this.Scale, point.X, point.Y, point.Z)
    }

    public fun InverseTransformPoint(x: Float, y: Float, z: Float): Vector3 {
        return InverseTransformPoint(this.Position, this.Rotation, this.Scale, x, y, z)
    }

}

public class TransformImpl(
    public override var Position: Vector3,
    public override var Rotation: Quaternion,
    public override var Scale: Vector3,
) : Transform {
    public override val IsStatic = false
}

public class StaticTransformImpl(
    public override val Position: Vector3,
    public override val Rotation: Quaternion,
    public override val Scale: Vector3,
) : Transform {
    public override val IsStatic = true
}

private fun InverseTransformPoint(position: Vector3, rotation: Quaternion, scale: Vector3, x: Float, y: Float, z: Float): Vector3 {
    var x = x - position.X
    var y = y - position.Y
    var z = z - position.Z

    val tx = 2f * (-rotation.Y * z - -rotation.Z * y)
    val ty = 2f * (-rotation.Z * x - -rotation.X * z)
    val tz = 2f * (-rotation.X * y - -rotation.Y * x)

    x += rotation.W * tx + (-rotation.Y * tz - -rotation.Z * ty)
    y += rotation.W * ty + (-rotation.Z * tx - -rotation.X * tz)
    z += rotation.W * tz + (-rotation.X * ty - -rotation.Y * tx)

    x /= scale.X
    y /= scale.Y
    z /= scale.Z
    return Vector3(x, y, z)
}

private fun TransformPoint(position: Vector3, rotation: Quaternion, scale: Vector3, x: Float, y: Float, z: Float): Vector3 {
    var x = x * scale.X
    var y = y * scale.Y
    var z = z * scale.Z

    // t = 2 * cross(rotation.xyz, point)
    val tx = 2f * (rotation.Y * z - rotation.Z * y)
    val ty = 2f * (rotation.Z * x - rotation.X * z)
    val tz = 2f * (rotation.X * y - rotation.Y * x)

    // result = point + rotation.w * t + cross(rotation.xyz, t)
    x += rotation.W * tx + (rotation.Y * tz - rotation.Z * ty)
    y += rotation.W * ty + (rotation.Z * tx - rotation.X * tz)
    z += rotation.W * tz + (rotation.X * ty - rotation.Y * tx)

    x += position.X
    y += position.Y
    z += position.Z
    return Vector3(x, y, z)
}
