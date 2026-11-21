package com.denis535.game_engine_pro.video

public class Image {

    public enum class PixelFormat {
        RGB,
        RGBA,
    }

    public val Width: Int
    public val Height: Int
    public val Format: PixelFormat
    public val Pixels: ByteArray

    public constructor(width: Int, height: Int, format: PixelFormat, pixels: ByteArray) {
        this.Width = width
        this.Height = height
        this.Format = format
        this.Pixels = pixels
    }

}
