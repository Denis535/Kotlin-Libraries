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

    public val UserData: Any?
        get() {
            check(!this.IsClosed)
            return field
        }

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

    public var OnAttachCallback: Proc1<Any?>? = null
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
    public var OnDetachCallback: Proc1<Any?>? = null
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

    public var OnActivateCallback: Proc1<Any?>? = null
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
    public var OnDeactivateCallback: Proc1<Any?>? = null
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

    internal constructor(userData: Any?) {
        this.UserData = userData
    }

    public final override fun close() {
        check(!this.IsClosing)
        check(!this.IsClosed)
        when (val owner = this.Owner) {
            is AbstractStateMachine -> check(owner.IsClosing)
            is AbstractState -> check(owner.IsClosing)
        }
        this.Lifecycle = ELifecycle.Closing
        this.OnCloseCallback?.invoke()
        if (this is ChildrenableState) {
            check(this.Children.all { it.IsClosed })
        }
        this.Lifecycle = ELifecycle.Closed
    }

    internal abstract fun Attach(machine: AbstractStateMachine, argument: Any?)
    internal abstract fun Attach(parent: AbstractState, argument: Any?)

    internal abstract fun Detach(machine: AbstractStateMachine, argument: Any?)
    internal abstract fun Detach(parent: AbstractState, argument: Any?)

    internal abstract fun Activate(argument: Any?)
    internal abstract fun Deactivate(argument: Any?)

    public final override fun toString(): String {
        return super.toString()
    }

}
