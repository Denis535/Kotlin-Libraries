package com.denis535.game_engine_pro.video

public class Image {
    public val Width: Int
    public val Height: Int
    public val Format: ImageFormat
    public val Pixels: ByteArray

    public constructor(width: Int, height: Int, format: ImageFormat, pixels: ByteArray) {
        this.Width = width
        this.Height = height
        this.Format = format
        this.Pixels = pixels
    }

}

public class AnimatedImage {
    public class Frame(
        public val TimestampMs: Int,
        public val Pixels: ByteArray,
    )

    public val Width: Int
    public val Height: Int
    public val Duration: Double
        get() = this.Frames.last().TimestampMs / 1000.0
    public val Format: ImageFormat
    public val Frames: List<Frame>

    public constructor(width: Int, height: Int, format: ImageFormat, frames: List<Frame>) {
        this.Width = width
        this.Height = height
        this.Format = format
        this.Frames = frames
    }

}

public enum class ImageFormat {
    RGB,
    RGBA,
}
