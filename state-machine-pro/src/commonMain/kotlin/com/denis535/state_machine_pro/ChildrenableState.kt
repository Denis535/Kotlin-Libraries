package com.denis535.state_machine_pro

public class ChildrenableState<TMachineUserData, TStateUserData> : AbstractState<TMachineUserData, TStateUserData> {

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
                if (this@ChildrenableState.Parent != null) {
                    this.yield(this@ChildrenableState.Parent!!)
                    this.yieldAll(this@ChildrenableState.Parent!!.Ancestors)
                }
            }
        }
    public override val AncestorsAndSelf: Sequence<AbstractState<TMachineUserData, TStateUserData>>
        get() {
            check(!this.IsClosed)
            return sequence {
                this.yield(this@ChildrenableState)
                this.yieldAll(this@ChildrenableState.Ancestors)
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

    public val Children: List<AbstractState<TMachineUserData, TStateUserData>>
        get() {
            check(!this.IsClosed)
            return this.ChildrenMutable
        }
    private val ChildrenMutable: MutableList<AbstractState<TMachineUserData, TStateUserData>> = mutableListOf<AbstractState<TMachineUserData, TStateUserData>>()
        get() {
            check(!this.IsClosed)
            return field
        }

    public var SortDelegate: Proc1<MutableList<AbstractState<TMachineUserData, TStateUserData>>>? = null
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
        check(this.Children.all { it.IsClosed })
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
        for (child in this.Children.toList()) {
            child.Activate(argument)
        }
        this.Activity = EActivity.Active
    }

    internal override fun Deactivate(argument: Any?) {
        this.Activity = EActivity.Deactivating
        for (child in this.Children.toList().asReversed()) {
            child.Deactivate(argument)
        }
        this.OnDeactivateCallback?.invoke(argument)
        this.Activity = EActivity.Inactive
    }

    public fun AddChild(child: AbstractState<TMachineUserData, TStateUserData>, argument: Any?) {
        check(!this.IsClosed)
        check(!this.Children.contains(child))
        this.ChildrenMutable.add(child)
        this.SortDelegate?.invoke(this.ChildrenMutable)
        child.Attach(this, argument)
    }

    public fun AddChildren(children: Array<AbstractState<TMachineUserData, TStateUserData>>, argument: Any?) {
        check(!this.IsClosed)
        for (child in children) {
            this.AddChild(child, argument)
        }
    }

    public fun RemoveChild(child: AbstractState<TMachineUserData, TStateUserData>, argument: Any?, callback: Proc2<AbstractState<TMachineUserData, TStateUserData>, Any?>? = null) {
        check(!this.IsClosed)
        check(this.Children.contains(child))
        child.Detach(this, argument)
        this.ChildrenMutable.remove(child)
        if (callback != null) {
            callback.invoke(child, argument)
        } else {
            child.close()
        }
    }

    public fun RemoveChildren(predicate: Predicate1<AbstractState<TMachineUserData, TStateUserData>>, argument: Any?, callback: Proc2<AbstractState<TMachineUserData, TStateUserData>, Any?>? = null): Int {
        check(!this.IsClosed)
        var count = 0
        for (child in this.Children.reversed().filter(predicate)) {
            this.RemoveChild(child, argument, callback)
            count++
        }
        return count
    }

}
