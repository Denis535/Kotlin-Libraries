package com.denis535.state_machine_pro

public abstract class AbstractState : AutoCloseable {

    private var Lifecycle = ELifecycle.Alive

    public val IsClosing: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closing
        }
    public val IsClosed: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closed
        }

    public abstract val Owner: Any?
    public abstract val Machine: AbstractStateMachine?

    public abstract val IsRoot: Boolean
    public abstract val Root: AbstractState

    public abstract val Parent: AbstractState?
    public abstract val Ancestors: Sequence<AbstractState>
    public abstract val AncestorsAndSelf: Sequence<AbstractState>

    public abstract val Activity: Activity

    internal constructor()

    public final override fun close() {
        check(!this.IsClosing)
        check(!this.IsClosed)
        this.Lifecycle = ELifecycle.Closing
        this.OnClose()
        if (this is ChildrenableState) {
            check(this.Children.all { it.IsClosed })
        }
        this.Lifecycle = ELifecycle.Closed
    }

    protected open fun OnClose() {
    }

    internal abstract fun Attach(machine: AbstractStateMachine, argument: Any?)
    internal abstract fun Attach(parent: AbstractState, argument: Any?)

    internal abstract fun Detach(machine: AbstractStateMachine, argument: Any?)
    internal abstract fun Detach(parent: AbstractState, argument: Any?)

    protected open fun OnAttach(argument: Any?) {
    }

    protected open fun OnDetach(argument: Any?) {
    }

    internal abstract fun Activate(argument: Any?)
    internal abstract fun Deactivate(argument: Any?)

    protected open fun OnActivate(argument: Any?) {
    }

    protected open fun OnDeactivate(argument: Any?) {
    }

    public final override fun toString(): String {
        return super.toString()
    }

}
