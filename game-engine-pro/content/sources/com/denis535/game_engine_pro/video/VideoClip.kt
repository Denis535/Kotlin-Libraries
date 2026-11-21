package com.denis535.game_engine_pro.video

public class VideoClip {
    public enum class PixelFormat {
        RGB,
        RGBA,
    }

    public val Format: PixelFormat
    public val FramesPerSecond: Double // 24, 30, 60...
    public val Width: Int
    public val Height: Int
    public val FrameCount: Int
    public val Pixels: ByteArray
    public val Duration: Double
        get() = this.FrameCount.toDouble() / this.FramesPerSecond

    public constructor(format: PixelFormat, framesPerSecond: Double, width: Int, height: Int, frameCount: Int, pixels: ByteArray) {
        this.Format = format
        this.FramesPerSecond = framesPerSecond
        this.Width = width
        this.Height = height
        this.FrameCount = frameCount
        this.Pixels = pixels
    }

}
