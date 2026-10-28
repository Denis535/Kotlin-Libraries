package com.denis535.game_engine_pro.audio

public open class Audio : AutoCloseable {

    public var IsClosed: Boolean = false
        private set

    public constructor()

    public override fun close() {
        check(!this.IsClosed)
        this.IsClosed = true
    }

    public open fun OnFrameBegin() {
        check(!this.IsClosed)
    }

    public open fun OnFrameEnd() {
        check(!this.IsClosed)
    }

}
