package com.denis535.game_engine_pro.video

public class VideoClip {
    public val Width: Int
    public val Height: Int
    public val Duration: Double
        get() = this.FrameCount.toDouble() / this.FramesPerSecond
    public val FrameCount: Int
    public val Format: VideoFormat
    public val FramesPerSecond: Double // 24, 30, 60...
    public val Frames: List<ByteArray>

    public constructor(width: Int, height: Int, format: VideoFormat, framesPerSecond: Double, frameCount: Int, frames: List<ByteArray>) {
        this.Width = width
        this.Height = height
        this.FrameCount = frameCount
        this.Format = format
        this.FramesPerSecond = framesPerSecond
        this.Frames = frames
    }

}

public enum class VideoFormat {
    RGB,
    RGBA,
}
