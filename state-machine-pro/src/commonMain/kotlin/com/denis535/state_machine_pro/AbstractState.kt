package com.denis535.state_machine_pro

public interface AbstractState : AutoCloseable {

    public val IsClosing: Boolean
    public val IsClosed: Boolean

    public val Owner: Any?

    public val Machine: StateMachine?

    public val IsRoot: Boolean
    public val Root: AbstractState

    public val Parent: AbstractState?
    public val Ancestors: Sequence<AbstractState>
    public val AncestorsAndSelf: Sequence<AbstractState>

    public val Activity: EActivity

}

public abstract class AbstractStateImpl : AbstractState {

    private var Lifecycle = ELifecycle.Alive

    public override val IsClosing: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closing
        }
    public override val IsClosed: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closed
        }

    public override var Owner: Any? = null
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

    public override val Machine: StateMachine?
        get() {
            check(!this.IsClosed)
            this.Owner.let { owner ->
                return (owner as? StateMachine) ?: (owner as? AbstractState)?.Machine
            }
        }

    public override val IsRoot: Boolean
        get() {
            check(!this.IsClosed)
            return this.Parent == null
        }
    public override val Root: AbstractState
        get() {
            check(!this.IsClosed)
            return this.Parent?.Root ?: this
        }

    public override val Parent: AbstractState?
        get() {
            check(!this.IsClosed)
            return this.Owner as? AbstractState
        }
    public override val Ancestors: Sequence<AbstractState>
        get() {
            check(!this.IsClosed)
            return sequence {
                if (this@AbstractStateImpl.Parent != null) {
                    this.yield(this@AbstractStateImpl.Parent!!)
                    this.yieldAll(this@AbstractStateImpl.Parent!!.Ancestors)
                }
            }
        }
    public override val AncestorsAndSelf: Sequence<AbstractState>
        get() {
            check(!this.IsClosed)
            return sequence {
                this.yield(this@AbstractStateImpl)
                this.yieldAll(this@AbstractStateImpl.Ancestors)
            }
        }

    public override var Activity: EActivity = EActivity.Inactive
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
        check(this.Children.all { it.IsClosed })
        this.Lifecycle = ELifecycle.Closed
    }

    protected open fun OnClose() {
    }

    internal fun Attach(machine: StateMachine, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = machine
        this.OnAttach(argument)
        if (true) {
            this.Activate(argument)
        }
    }

    internal fun Attach(parent: AbstractState, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = parent
        this.OnAttach(argument)
        if (this.Parent!!.Activity == EActivity.Active) {
            this.Activate(argument)
        }
    }

    internal fun Detach(machine: StateMachine, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == machine)
        if (true) {
            this.Deactivate(argument)
        }
        this.OnDetach(argument)
        this.Owner = null
    }

    internal fun Detach(parent: AbstractState, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == parent)
        if (this.Activity == EActivity.Active) {
            this.Deactivate(argument)
        }
        this.OnDetach(argument)
        this.Owner = null
    }

    internal fun Activate(argument: Any?) {
        this.Activity = EActivity.Activating
        this.OnActivate(argument)
        for (child in this.Children.toList()) {
            (child as AbstractStateImpl).Activate(argument)
        }
        this.Activity = EActivity.Active
    }

    internal fun Deactivate(argument: Any?) {
        this.Activity = EActivity.Deactivating
        for (child in this.Children.toList().asReversed()) {
            (child as AbstractStateImpl).Deactivate(argument)
        }
        this.OnDeactivate(argument)
        this.Activity = EActivity.Inactive
    }

    protected open fun OnAttach(argument: Any?) {
    }

    protected open fun OnDetach(argument: Any?) {
    }

    protected open fun OnActivate(argument: Any?) {
    }

    protected open fun OnDeactivate(argument: Any?) {
    }

}
