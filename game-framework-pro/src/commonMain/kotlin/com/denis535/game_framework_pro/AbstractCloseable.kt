package com.denis535.game_framework_pro

public abstract class AbstractCloseable : AutoCloseable {
    private enum class ELifecycle {
        Alive, Closing, Closed,
    }

    private var Lifecycle = ELifecycle.Alive

    public val IsClosing: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closing
        }
    public val IsClosed: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closed
        }

    public constructor()

    public final override fun close() {
        check(!this.IsClosing)
        check(!this.IsClosed)
        this.Lifecycle = ELifecycle.Closing
        this.OnClose()
        this.OnCloseInternal()
        this.Lifecycle = ELifecycle.Closed
    }

    protected abstract fun OnClose()
    internal abstract fun OnCloseInternal()

}
