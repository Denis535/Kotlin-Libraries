package com.denis535.tree_machine_pro

public abstract class AbstractNode : AutoCloseable {

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
    public abstract val Machine: AbstractTreeMachine?

    public abstract val IsRoot: Boolean
    public abstract val Root: AbstractNode

    public abstract val Parent: AbstractNode?
    public abstract val Ancestors: Sequence<AbstractNode>
    public abstract val AncestorsAndSelf: Sequence<AbstractNode>

    public abstract val Activity: Activity

    public abstract val Children: List<AbstractNode>
    public abstract val Descendants: Sequence<AbstractNode>
    public abstract val DescendantsAndSelf: Sequence<AbstractNode>

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

    public constructor()

    public final override fun close() {
        check(!this.IsClosing)
        check(!this.IsClosed)
        when (val owner = this.Owner) {
            is AbstractTreeMachine -> check(owner.IsClosing)
            is AbstractNode -> check(owner.IsClosing)
        }
        this.Lifecycle = ELifecycle.Closing
        this.OnCloseCallback?.invoke()
        check(this.Children.all { it.IsClosed })
        this.Lifecycle = ELifecycle.Closed
    }

    internal abstract fun Attach(machine: AbstractTreeMachine, argument: Any?)
    internal abstract fun Attach(parent: AbstractNode, argument: Any?)

    internal abstract fun Detach(machine: AbstractTreeMachine, argument: Any?)
    internal abstract fun Detach(parent: AbstractNode, argument: Any?)

    internal abstract fun Activate(argument: Any?)
    internal abstract fun Deactivate(argument: Any?)

    public final override fun toString(): String {
        return super.toString()
    }

}
