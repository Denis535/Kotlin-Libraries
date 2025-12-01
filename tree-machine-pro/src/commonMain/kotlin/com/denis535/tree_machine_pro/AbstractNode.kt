package com.denis535.tree_machine_pro

public abstract class AbstractNode<TMachineUserData, TNodeUserData> : AutoCloseable {

    public abstract val IsClosing: Boolean
    public abstract val IsClosed: Boolean

    public abstract val Owner: Any?
    public abstract val Machine: AbstractTreeMachine<TMachineUserData, TNodeUserData>?

    public abstract val IsRoot: Boolean
    public abstract val Root: AbstractNode<TMachineUserData, TNodeUserData>

    public abstract val Parent: AbstractNode<TMachineUserData, TNodeUserData>?
    public abstract val Ancestors: Sequence<AbstractNode<TMachineUserData, TNodeUserData>>
    public abstract val AncestorsAndSelf: Sequence<AbstractNode<TMachineUserData, TNodeUserData>>

    public abstract val Activity: Activity

    public abstract val Children: List<AbstractNode<TMachineUserData, TNodeUserData>>
    public abstract val Descendants: Sequence<AbstractNode<TMachineUserData, TNodeUserData>>
    public abstract val DescendantsAndSelf: Sequence<AbstractNode<TMachineUserData, TNodeUserData>>

    public abstract val UserData: TNodeUserData

    public constructor()

    internal abstract fun Attach(machine: AbstractTreeMachine<TMachineUserData, TNodeUserData>, argument: Any?)
    internal abstract fun Attach(parent: AbstractNode<TMachineUserData, TNodeUserData>, argument: Any?)

    internal abstract fun Detach(machine: AbstractTreeMachine<TMachineUserData, TNodeUserData>, argument: Any?)
    internal abstract fun Detach(parent: AbstractNode<TMachineUserData, TNodeUserData>, argument: Any?)

    internal abstract fun Activate(argument: Any?)
    internal abstract fun Deactivate(argument: Any?)

    public final override fun toString(): String {
        return this.UserData?.toString() ?: super.toString()
    }

}
