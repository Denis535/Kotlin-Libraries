package com.denis535.game_engine_pro.video

public class VideoClip {
    public val Format: VideoFormat
    public val FramesPerSecond: Double // 24, 30, 60...
    public val Width: Int
    public val Height: Int
    public val FrameCount: Int
    public val Duration: Double
        get() = this.FrameCount.toDouble() / this.FramesPerSecond
    public val Frames: List<ByteArray>

    public constructor(format: VideoFormat, framesPerSecond: Double, width: Int, height: Int, frameCount: Int, frames: List<ByteArray>) {
        this.Format = format
        this.FramesPerSecond = framesPerSecond
        this.Width = width
        this.Height = height
        this.FrameCount = frameCount
        this.Frames = frames
    }

}

public enum class VideoFormat {
    RGB,
    RGBA,
}
