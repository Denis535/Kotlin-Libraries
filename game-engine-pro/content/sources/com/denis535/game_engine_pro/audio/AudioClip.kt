package com.denis535.game_engine_pro.audio

public class AudioClip {
    public val FrameCount: Int
    public val ChannelCount: Int // mono, stereo, ...
    public val FramesPerSecond: Int // is always 48000 for Opus
    public val Samples: ShortArray // (L, R), (L, R), ...
    public val Duration: Float
        get() = this.FrameCount.toFloat() / this.FramesPerSecond

    public constructor(frameCount: Int, channelCount: Int, framesPerSecond: Int, samples: ShortArray) {
        this.FrameCount = frameCount
        this.ChannelCount = channelCount
        this.FramesPerSecond = framesPerSecond
        this.Samples = samples
    }

}
