package com.denis535.game_engine_pro.audio

public class AudioClip {
    public val Duration: Double
        get() = this.FrameCount.toDouble() / this.FramesPerSecond
    public val FrameCount: Int
    public val Format: AudioFormat
    public val FramesPerSecond: Int // is always 48000 for Opus
    public val Frames: ShortArray // (L, R), (L, R), ...

    public constructor(frameCount: Int, format: AudioFormat, framesPerSecond: Int, frames: ShortArray) {
        this.FrameCount = frameCount
        this.Format = format
        this.FramesPerSecond = framesPerSecond
        this.Frames = frames
    }

}

public enum class AudioFormat {
    Mono,
    Stereo,
}
