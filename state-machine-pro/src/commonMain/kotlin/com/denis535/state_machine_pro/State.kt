package com.denis535.state_machine_pro

public class State<TMachineUserData, TStateUserData> : AbstractState<TMachineUserData, TStateUserData> {

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
        private set(value) {
            check(!this.IsClosed)
            if (value != null) {
                check(field == null)
            } else {
                check(field != null)
            }
            field = value
        }

    public override val Machine: AbstractStateMachine<TMachineUserData, TStateUserData>?
        get() {
            check(!this.IsClosed)
            return when (val owner = this.Owner) {
                is AbstractStateMachine<*, *> -> owner as AbstractStateMachine<TMachineUserData, TStateUserData>
                is AbstractState<*, *> -> owner.Machine as AbstractStateMachine<TMachineUserData, TStateUserData>
                else -> null
            }
        }

    public override val IsRoot: Boolean
        get() {
            check(!this.IsClosed)
            return this.Parent == null
        }
    public override val Root: AbstractState<TMachineUserData, TStateUserData>
        get() {
            check(!this.IsClosed)
            return this.Parent?.Root ?: this
        }

    public override val Parent: AbstractState<TMachineUserData, TStateUserData>?
        get() {
            check(!this.IsClosed)
            return this.Owner as? AbstractState<TMachineUserData, TStateUserData>
        }
    public override val Ancestors: Sequence<AbstractState<TMachineUserData, TStateUserData>>
        get() {
            check(!this.IsClosed)
            return sequence {
                if (this@State.Parent != null) {
                    this.yield(this@State.Parent!!)
                    this.yieldAll(this@State.Parent!!.Ancestors)
                }
            }
        }
    public override val AncestorsAndSelf: Sequence<AbstractState<TMachineUserData, TStateUserData>>
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

    public override val UserData: TStateUserData
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

    public constructor(userData: TStateUserData) {
        this.UserData = userData
    }

    public override fun close() {
        check(!this.IsClosing)
        check(!this.IsClosed)
        when (val owner = this.Owner) {
            is AbstractStateMachine<*, *> -> check(owner.IsClosing)
            is AbstractState<*, *> -> check(owner.IsClosing)
        }
        this.Lifecycle = ELifecycle.Closing
        this.OnCloseCallback?.invoke()
        this.Lifecycle = ELifecycle.Closed
    }

    internal override fun Attach(machine: AbstractStateMachine<TMachineUserData, TStateUserData>, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = machine
        this.OnAttachCallback?.invoke(argument)
        if (true) {
            this.Activate(argument)
        }
    }

    internal override fun Attach(parent: AbstractState<TMachineUserData, TStateUserData>, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = parent
        this.OnAttachCallback?.invoke(argument)
        if (this.Parent!!.Activity == EActivity.Active) {
            this.Activate(argument)
        }
    }

    internal override fun Detach(machine: AbstractStateMachine<TMachineUserData, TStateUserData>, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == machine)
        if (true) {
            this.Deactivate(argument)
        }
        this.OnDetachCallback?.invoke(argument)
        this.Owner = null
    }

    internal override fun Detach(parent: AbstractState<TMachineUserData, TStateUserData>, argument: Any?) {
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
