package com.denis535.state_machine_pro

public open class ChildrenableState : AbstractState {

    public final override var Owner: Any? = null
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

    public final override val Machine: AbstractStateMachine?
        get() {
            check(!this.IsClosed)
            return when (val owner = this.Owner) {
                is AbstractStateMachine -> owner
                is AbstractState -> owner.Machine as AbstractStateMachine
                else -> null
            }
        }

    public final override val IsRoot: Boolean
        get() {
            check(!this.IsClosed)
            return this.Parent == null
        }
    public final override val Root: AbstractState
        get() {
            check(!this.IsClosed)
            return this.Parent?.Root ?: this
        }

    public final override val Parent: AbstractState?
        get() {
            check(!this.IsClosed)
            return this.Owner as? AbstractState
        }
    public final override val Ancestors: Sequence<AbstractState>
        get() {
            check(!this.IsClosed)
            return sequence {
                if (this@ChildrenableState.Parent != null) {
                    this.yield(this@ChildrenableState.Parent!!)
                    this.yieldAll(this@ChildrenableState.Parent!!.Ancestors)
                }
            }
        }
    public final override val AncestorsAndSelf: Sequence<AbstractState>
        get() {
            check(!this.IsClosed)
            return sequence {
                this.yield(this@ChildrenableState)
                this.yieldAll(this@ChildrenableState.Ancestors)
            }
        }

    public final override var Activity: EActivity = EActivity.Inactive
        get() {
            check(!this.IsClosed)
            return field
        }
        private set(value) {
            check(!this.IsClosed)
            check(field != value)
            field = value
        }

    public final val Children: List<AbstractState>
        get() {
            check(!this.IsClosed)
            return this.ChildrenMutable
        }
    private final val ChildrenMutable: MutableList<AbstractState> = mutableListOf()
        get() {
            check(!this.IsClosed)
            return field
        }

    public final var SortDelegate: Proc1<MutableList<AbstractState>>? = null
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

    public constructor()

    internal override fun Attach(machine: AbstractStateMachine, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = machine
        this.OnAttach(argument)
        if (true) {
            this.Activate(argument)
        }
    }

    internal override fun Attach(parent: AbstractState, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = parent
        this.OnAttach(argument)
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
        this.OnDetach(argument)
        this.Owner = null
    }

    internal override fun Detach(parent: AbstractState, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == parent)
        if (this.Activity == EActivity.Active) {
            this.Deactivate(argument)
        }
        this.OnDetach(argument)
        this.Owner = null
    }

    internal override fun Activate(argument: Any?) {
        this.Activity = EActivity.Activating
        this.OnActivate(argument)
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
        this.OnDeactivate(argument)
        this.Activity = EActivity.Inactive
    }

    public fun AddChild(child: AbstractState, argument: Any?) {
        check(!this.IsClosed)
        check(!this.Children.contains(child))
        this.ChildrenMutable.add(child)
        this.SortDelegate?.invoke(this.ChildrenMutable)
        child.Attach(this, argument)
    }

    public fun AddChildren(children: Array<AbstractState>, argument: Any?) {
        check(!this.IsClosed)
        for (child in children) {
            this.AddChild(child, argument)
        }
    }

    public fun RemoveChild(child: AbstractState, argument: Any?, callback: Proc2<AbstractState, Any?>? = null) {
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

    public fun RemoveChildren(predicate: Predicate1<AbstractState>, argument: Any?, callback: Proc2<AbstractState, Any?>? = null): Int {
        check(!this.IsClosed)
        var count = 0
        for (child in this.Children.reversed().filter(predicate)) {
            this.RemoveChild(child, argument, callback)
            count++
        }
        return count
    }

}
