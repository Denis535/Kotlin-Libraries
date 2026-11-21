package com.denis535.game_engine_pro.audio

public class AudioClip {
    public enum class AudioFormat {
        Mono,
        Stereo,
    }

    public val Format: AudioFormat
    public val FramesPerSecond: Int // is always 48000 for Opus
    public val FrameCount: Int
    public val Samples: ShortArray // (L, R), (L, R), ...
    public val Duration: Double
        get() = this.FrameCount.toDouble() / this.FramesPerSecond

    public constructor(format: AudioFormat, framesPerSecond: Int, frameCount: Int, samples: ShortArray) {
        this.Format = format
        this.FramesPerSecond = framesPerSecond
        this.FrameCount = frameCount
        this.Samples = samples
    }

}
