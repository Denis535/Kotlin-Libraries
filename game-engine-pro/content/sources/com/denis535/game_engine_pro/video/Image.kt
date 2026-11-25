package com.denis535.game_engine_pro.video

public class Image {
    public val Format: ImageFormat
    public val Width: Int
    public val Height: Int
    public val Pixels: ByteArray

    public constructor(format: ImageFormat, width: Int, height: Int, pixels: ByteArray) {
        this.Format = format
        this.Width = width
        this.Height = height
        this.Pixels = pixels
    }

}

//public class AnimatedImage {
//    public val Format: ImageFormat
//    public val FramesPerSecond: Double // 24, 30, 60...
//    public val Width: Int
//    public val Height: Int
//    public val FrameCount: Int
//    public val Duration: Double
//        get() = this.FrameCount.toDouble() / this.FramesPerSecond
//    public val Frames: ByteArray
//
//    public constructor(format: ImageFormat, framesPerSecond: Double, width: Int, height: Int, frameCount: Int, frames: ByteArray) {
//        this.Format = format
//        this.FramesPerSecond = framesPerSecond
//        this.Width = width
//        this.Height = height
//        this.FrameCount = frameCount
//        this.Frames = frames
//    }
//
//}

public enum class ImageFormat {
    RGB,
    RGBA,
}
