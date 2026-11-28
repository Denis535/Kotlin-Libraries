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

public class AnimatedImage {
    public class Frame(
        public val TimestampMs: Int,
        public val Pixels: ByteArray,
    )

    public val Format: ImageFormat
    public val Width: Int
    public val Height: Int
    public val Duration: Double
        get() = this.Frames.last().TimestampMs / 1000.0
    public val Frames: List<Frame>

    public constructor(format: ImageFormat, width: Int, height: Int, frames: List<Frame>) {
        this.Format = format
        this.Width = width
        this.Height = height
        this.Frames = frames
    }

}

public enum class ImageFormat {
    RGB,
    RGBA,
}
