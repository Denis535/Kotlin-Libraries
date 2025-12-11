package com.denis535.state_machine_pro

public abstract class AbstractStateMachine : AutoCloseable {

    private var Lifecycle = ELifecycle.Alive

    public val IsClosing: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closing
        }
    public val IsClosed: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closed
        }

    public abstract val Root: AbstractState?

    internal constructor()

    public final override fun close() {
        check(!this.IsClosing)
        check(!this.IsClosed)
        this.Lifecycle = ELifecycle.Closing
        check(this.Root.let { it == null || it.IsClosed })
        this.Lifecycle = ELifecycle.Closed
    }

    public final override fun toString(): String {
        return super.toString()
    }

}
