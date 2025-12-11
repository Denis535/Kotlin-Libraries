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

    public var Owner: Any? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        internal set(value) {
            check(!this.IsClosed)
            if (value != null) {
                check(field == null)
            } else {
                check(field != null)
            }
            field = value
        }

    public val Machine: StateMachine?
        get() {
            check(!this.IsClosed)
            return when (val owner = this.Owner) {
                is StateMachine -> owner
                is AbstractState -> owner.Machine as StateMachine
                else -> null
            }
        }

    public val IsRoot: Boolean
        get() {
            check(!this.IsClosed)
            return this.Parent == null
        }
    public val Root: AbstractState
        get() {
            check(!this.IsClosed)
            return this.Parent?.Root ?: this
        }

    public val Parent: AbstractState?
        get() {
            check(!this.IsClosed)
            return this.Owner as? AbstractState
        }
    public val Ancestors: Sequence<AbstractState>
        get() {
            check(!this.IsClosed)
            return sequence {
                if (this@AbstractState.Parent != null) {
                    this.yield(this@AbstractState.Parent!!)
                    this.yieldAll(this@AbstractState.Parent!!.Ancestors)
                }
            }
        }
    public val AncestorsAndSelf: Sequence<AbstractState>
        get() {
            check(!this.IsClosed)
            return sequence {
                this.yield(this@AbstractState)
                this.yieldAll(this@AbstractState.Ancestors)
            }
        }

    public var Activity: EActivity = EActivity.Inactive
        get() {
            check(!this.IsClosed)
            return field
        }
        internal set(value) {
            check(!this.IsClosed)
            check(field != value)
            field = value
        }

    public abstract val Children: List<AbstractState>

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

    internal abstract fun Attach(machine: StateMachine, argument: Any?)
    internal abstract fun Attach(parent: AbstractState, argument: Any?)

    internal abstract fun Detach(machine: StateMachine, argument: Any?)
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

}
