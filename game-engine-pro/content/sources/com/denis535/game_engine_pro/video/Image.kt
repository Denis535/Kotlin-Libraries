package com.denis535.game_engine_pro.video

public class Image {
    public enum class PixelFormat {
        RGB,
        RGBA,
    }

    public val Format: PixelFormat
    public val Width: Int
    public val Height: Int
    public val Pixels: ByteArray

    public constructor(format: PixelFormat, width: Int, height: Int, pixels: ByteArray) {
        this.Format = format
        this.Width = width
        this.Height = height
        this.Pixels = pixels
    }

}
