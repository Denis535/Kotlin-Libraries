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

    public var Owner: Any? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        internal set(value) {
            check(!this.IsClosed)
            if (value != null) {
                check(field == null)
            } else {
                check(field != null)
            }
            field = value
        }

    public val Machine: TreeMachine?
        get() {
            check(!this.IsClosed)
            return when (val owner = this.Owner) {
                is TreeMachine -> owner
                is AbstractNode -> owner.Machine as TreeMachine
                else -> null
            }
        }

    public val IsRoot: Boolean
        get() {
            check(!this.IsClosed)
            return this.Parent == null
        }
    public val Root: AbstractNode
        get() {
            check(!this.IsClosed)
            return this.Parent?.Root ?: this
        }

    public val Parent: AbstractNode?
        get() {
            check(!this.IsClosed)
            return this.Owner as? AbstractNode
        }
    public val Ancestors: Sequence<AbstractNode>
        get() {
            check(!this.IsClosed)
            return sequence {
                if (this@AbstractNode.Parent != null) {
                    this.yield(this@AbstractNode.Parent!!)
                    this.yieldAll(this@AbstractNode.Parent!!.Ancestors)
                }
            }
        }
    public val AncestorsAndSelf: Sequence<AbstractNode>
        get() {
            check(!this.IsClosed)
            return sequence {
                this.yield(this@AbstractNode)
                this.yieldAll(this@AbstractNode.Ancestors)
            }
        }

    public var Activity: EActivity = EActivity.Inactive
        get() {
            check(!this.IsClosed)
            return field
        }
        internal set(value) {
            check(!this.IsClosed)
            check(field != value)
            field = value
        }

    public val Children: List<AbstractNode>
        get() {
            check(!this.IsClosed)
            return this.ChildrenMutable
        }
    internal val ChildrenMutable: MutableList<AbstractNode> = mutableListOf()
        get() {
            check(!this.IsClosed)
            return field
        }
    public val Descendants: Sequence<AbstractNode>
        get() {
            check(!this.IsClosed)
            return sequence {
                for (child in this@AbstractNode.Children) {
                    this.yield(child)
                    this.yieldAll(child.Descendants)
                }
            }
        }
    public val DescendantsAndSelf: Sequence<AbstractNode>
        get() {
            check(!this.IsClosed)
            return sequence {
                this.yield(this@AbstractNode)
                this.yieldAll(this@AbstractNode.Descendants)
            }
        }

    internal constructor()

    public final override fun close() {
        check(!this.IsClosing)
        check(!this.IsClosed)
        this.Lifecycle = ELifecycle.Closing
        this.OnClose()
        check(this.Children.all { it.IsClosed })
        this.Lifecycle = ELifecycle.Closed
    }

    protected open fun OnClose() {
    }

    internal fun Attach(machine: TreeMachine, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = machine
        this.OnAttach(argument)
        if (true) {
            this.Activate(argument)
        }
    }

    internal fun Attach(parent: AbstractNode, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = parent
        this.OnAttach(argument)
        if (this.Parent!!.Activity == EActivity.Active) {
            this.Activate(argument)
        }
    }

    internal fun Detach(machine: TreeMachine, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == machine)
        if (true) {
            this.Deactivate(argument)
        }
        this.OnDetach(argument)
        this.Owner = null
    }

    internal fun Detach(parent: AbstractNode, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == parent)
        if (this.Activity == EActivity.Active) {
            this.Deactivate(argument)
        }
        this.OnDetach(argument)
        this.Owner = null
    }

    internal fun Activate(argument: Any?) {
        this.Activity = EActivity.Activating
        this.OnActivate(argument)
        for (child in this.Children.toList()) {
            child.Activate(argument)
        }
        this.Activity = EActivity.Active
    }

    internal fun Deactivate(argument: Any?) {
        this.Activity = EActivity.Deactivating
        for (child in this.Children.toList().asReversed()) {
            child.Deactivate(argument)
        }
        this.OnDeactivate(argument)
        this.Activity = EActivity.Inactive
    }

    protected open fun OnAttach(argument: Any?) {
    }

    protected open fun OnDetach(argument: Any?) {
    }

    protected open fun OnActivate(argument: Any?) {
    }

    protected open fun OnDeactivate(argument: Any?) {
    }

}
