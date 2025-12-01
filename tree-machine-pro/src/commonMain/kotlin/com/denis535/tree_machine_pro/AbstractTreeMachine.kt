package com.denis535.tree_machine_pro

public abstract class AbstractTreeMachine<TMachineUserData, TNodeUserData> : AutoCloseable {

    public abstract val IsClosing: Boolean
    public abstract val IsClosed: Boolean

    public abstract val Root: AbstractNode<TMachineUserData, TNodeUserData>?

    public abstract val UserData: TMachineUserData

    public constructor()

    public final override fun toString(): String {
        return this.UserData?.toString() ?: super.toString()
    }

}
