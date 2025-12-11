package com.denis535.state_machine_pro

public class State : AbstractState {

    public override var Owner: Any? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        private set(value) {
            check(!this.IsClosed)
            if (value != null) {
                check(field == null)
            } else {
                check(field != null)
            }
            field = value
        }

    public override val Machine: AbstractStateMachine?
        get() {
            check(!this.IsClosed)
            return when (val owner = this.Owner) {
                is AbstractStateMachine -> owner
                is AbstractState -> owner.Machine as AbstractStateMachine
                else -> null
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
                if (this@State.Parent != null) {
                    this.yield(this@State.Parent!!)
                    this.yieldAll(this@State.Parent!!.Ancestors)
                }
            }
        }
    public override val AncestorsAndSelf: Sequence<AbstractState>
        get() {
            check(!this.IsClosed)
            return sequence {
                this.yield(this@State)
                this.yieldAll(this@State.Ancestors)
            }
        }

    public override var Activity: EActivity = EActivity.Inactive
        get() {
            check(!this.IsClosed)
            return field
        }
        private set(value) {
            check(!this.IsClosed)
            check(field != value)
            field = value
        }

    public constructor(userData: Any?) : super(userData)

    internal override fun Attach(machine: AbstractStateMachine, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = machine
        this.OnAttachCallback?.invoke(argument)
        if (true) {
            this.Activate(argument)
        }
    }

    internal override fun Attach(parent: AbstractState, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = parent
        this.OnAttachCallback?.invoke(argument)
        if (this.Parent!!.Activity == EActivity.Active) {
            this.Activate(argument)
        }
    }

    internal override fun Detach(machine: AbstractStateMachine, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == machine)
        if (true) {
            this.Deactivate(argument)
        }
        this.OnDetachCallback?.invoke(argument)
        this.Owner = null
    }

    internal override fun Detach(parent: AbstractState, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == parent)
        if (this.Activity == EActivity.Active) {
            this.Deactivate(argument)
        }
        this.OnDetachCallback?.invoke(argument)
        this.Owner = null
    }

    internal override fun Activate(argument: Any?) {
        this.Activity = EActivity.Activating
        this.OnActivateCallback?.invoke(argument)
        this.Activity = EActivity.Active
    }

    internal override fun Deactivate(argument: Any?) {
        this.Activity = EActivity.Deactivating
        this.OnDeactivateCallback?.invoke(argument)
        this.Activity = EActivity.Inactive
    }

}
