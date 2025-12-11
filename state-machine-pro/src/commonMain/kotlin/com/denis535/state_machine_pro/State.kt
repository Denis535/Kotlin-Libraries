package com.denis535.state_machine_pro

public open class State : AbstractState {

    public final override val Children: List<AbstractState>
        get() {
            check(!this.IsClosed)
            return listOf()
        }

    public constructor()

    internal override fun Attach(machine: StateMachine, argument: Any?) {
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

    internal override fun Detach(machine: StateMachine, argument: Any?) {
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
        this.Activity = EActivity.Active
    }

    internal override fun Deactivate(argument: Any?) {
        this.Activity = EActivity.Deactivating
        this.OnDeactivate(argument)
        this.Activity = EActivity.Inactive
    }

}
