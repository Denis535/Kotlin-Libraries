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

    public var OnCloseCallback: Proc? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        set(value) {
            check(!this.IsClosed)
            if (value != null) {
                check(field == null)
            } else {
                check(field != null)
            }
            field = value
        }

    internal constructor()

    public final override fun close() {
        check(!this.IsClosing)
        check(!this.IsClosed)
        this.Lifecycle = ELifecycle.Closing
        this.OnCloseCallback?.invoke()
        check(this.Root == null || this.Root!!.IsClosed)
//        this.Root?.let { check(it.IsClosed) }
        this.Lifecycle = ELifecycle.Closed
    }

    public final override fun toString(): String {
        return super.toString()
    }

}
