package com.denis535.state_machine_pro

public interface AbstractState<T> : AutoCloseable where T : AbstractState<T> {

    public val IsClosing: Boolean
    public val IsClosed: Boolean

    public val Owner: Any?

    public val Machine: StateMachine<T>?

    public val IsRoot: Boolean
    public val Root: T

    public val Parent: T?
    public val Ancestors: Sequence<T>
    public val AncestorsAndSelf: Sequence<T>

    public val Activity: EActivity

    public val Children: List<T>

}

public abstract class AbstractStateImpl<T> : AbstractState<T> where T : AbstractState<T> {

    private var Lifecycle = ELifecycle.Alive

    public final override val IsClosing: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closing
        }
    public final override val IsClosed: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closed
        }

    public final override var Owner: Any? = null
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

    public final override val Machine: StateMachine<T>?
        get() {
            check(!this.IsClosed)
            this.Owner.let { owner ->
                return (owner as? StateMachine<T>) ?: (owner as? AbstractState<T>)?.Machine
            }
        }

    public final override val IsRoot: Boolean
        get() {
            check(!this.IsClosed)
            return this.Parent == null
        }
    public final override val Root: T
        get() {
            check(!this.IsClosed)
            return this.Parent?.Root ?: (this as T)
        }

    public final override val Parent: T?
        get() {
            check(!this.IsClosed)
            return this.Owner as? T
        }
    public final override val Ancestors: Sequence<T>
        get() {
            check(!this.IsClosed)
            return sequence {
                if (this@AbstractStateImpl.Parent != null) {
                    this.yield(this@AbstractStateImpl.Parent!!)
                    this.yieldAll(this@AbstractStateImpl.Parent!!.Ancestors)
                }
            }
        }
    public final override val AncestorsAndSelf: Sequence<T>
        get() {
            check(!this.IsClosed)
            return sequence {
                this.yield(this@AbstractStateImpl as T)
                this.yieldAll(this@AbstractStateImpl.Ancestors)
            }
        }

    public final override var Activity: EActivity = EActivity.Inactive
        get() {
            check(!this.IsClosed)
            return field
        }
        internal set(value) {
            check(!this.IsClosed)
            check(field != value)
            field = value
        }

    public constructor()

    public final override fun close() {
        check(!this.IsClosing)
        check(!this.IsClosed)
        this.Lifecycle = ELifecycle.Closing
        this.OnClose()
        check(this.Children.all { it.IsClosed })
        this.Lifecycle = ELifecycle.Closed
    }

    protected abstract fun OnClose()

    internal fun Attach(machine: StateMachine<T>, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = machine
        this.OnAttach(argument)
        if (true) {
            this.Activate(argument)
        }
    }

    internal fun Attach(parent: T, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = parent
        this.OnAttach(argument)
        if (this.Parent!!.Activity == EActivity.Active) {
            this.Activate(argument)
        }
    }

    internal fun Detach(machine: StateMachine<T>, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == machine)
        if (true) {
            this.Deactivate(argument)
        }
        this.OnDetach(argument)
        this.Owner = null
    }

    internal fun Detach(parent: T, argument: Any?) {
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
            (child as AbstractStateImpl<T>).Activate(argument)
        }
        this.Activity = EActivity.Active
    }

    internal fun Deactivate(argument: Any?) {
        this.Activity = EActivity.Deactivating
        for (child in this.Children.toList().asReversed()) {
            (child as AbstractStateImpl<T>).Deactivate(argument)
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
