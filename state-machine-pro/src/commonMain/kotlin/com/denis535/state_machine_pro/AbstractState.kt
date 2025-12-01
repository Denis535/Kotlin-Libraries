package com.denis535.state_machine_pro

public abstract class AbstractState<TMachineUserData, TStateUserData> : AutoCloseable {

    public abstract val IsClosing: Boolean
    public abstract val IsClosed: Boolean

    public abstract val Owner: Any?
    public abstract val Machine: AbstractStateMachine<TMachineUserData, TStateUserData>?

    public abstract val IsRoot: Boolean
    public abstract val Root: AbstractState<TMachineUserData, TStateUserData>

    public abstract val Parent: AbstractState<TMachineUserData, TStateUserData>?
    public abstract val Ancestors: Sequence<AbstractState<TMachineUserData, TStateUserData>>
    public abstract val AncestorsAndSelf: Sequence<AbstractState<TMachineUserData, TStateUserData>>

    public abstract val Activity: Activity

    public abstract val UserData: TStateUserData

    public constructor()

    internal abstract fun Attach(machine: AbstractStateMachine<TMachineUserData, TStateUserData>, argument: Any?)
    internal abstract fun Attach(parent: AbstractState<TMachineUserData, TStateUserData>, argument: Any?)

    internal abstract fun Detach(machine: AbstractStateMachine<TMachineUserData, TStateUserData>, argument: Any?)
    internal abstract fun Detach(parent: AbstractState<TMachineUserData, TStateUserData>, argument: Any?)

    internal abstract fun Activate(argument: Any?)
    internal abstract fun Deactivate(argument: Any?)

    public final override fun toString(): String {
        return this.UserData?.toString() ?: super.toString()
    }

}
