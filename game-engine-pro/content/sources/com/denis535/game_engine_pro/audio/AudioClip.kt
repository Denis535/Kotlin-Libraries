package com.denis535.game_engine_pro.audio

public class AudioClip {
    public enum class AudioFormat {
        Mono,
        Stereo,
    }

    public val Format: AudioFormat
    public val FramesPerSecond: Int // is always 48000 for Opus
    public val FrameCount: Int
    public val Duration: Double
        get() = this.FrameCount.toDouble() / this.FramesPerSecond
    public val Frames: ShortArray // (L, R), (L, R), ...

    public constructor(format: AudioFormat, framesPerSecond: Int, frameCount: Int, frames: ShortArray) {
        this.Format = format
        this.FramesPerSecond = framesPerSecond
        this.FrameCount = frameCount
        this.Frames = frames
    }

}
