package com.denis535.tree_machine_pro

public interface AbstractNode : AutoCloseable {

    public val IsClosing: Boolean
    public val IsClosed: Boolean

    public val Owner: Any?

    public val Machine: TreeMachine?

    public val IsRoot: Boolean
    public val Root: AbstractNode

    public val Parent: AbstractNode?
    public val Ancestors: Sequence<AbstractNode>
    public val AncestorsAndSelf: Sequence<AbstractNode>

    public val Activity: EActivity

    public val Children: List<AbstractNode>
    public val Descendants: Sequence<AbstractNode>
    public val DescendantsAndSelf: Sequence<AbstractNode>

}

public abstract class AbstractNodeImpl : AbstractNode {

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
        internal set(value) {
            check(!this.IsClosed)
            if (value != null) {
                check(field == null)
            } else {
                check(field != null)
            }
            field = value
        }

    public override val Machine: TreeMachine?
        get() {
            check(!this.IsClosed)
            return when (val owner = this.Owner) {
                is TreeMachine -> owner
                is AbstractNode -> owner.Machine as TreeMachine
                else -> null
            }
        }

    public override val IsRoot: Boolean
        get() {
            check(!this.IsClosed)
            return this.Parent == null
        }
    public override val Root: AbstractNode
        get() {
            check(!this.IsClosed)
            return this.Parent?.Root ?: this
        }

    public override val Parent: AbstractNode?
        get() {
            check(!this.IsClosed)
            return this.Owner as? AbstractNode
        }
    public override val Ancestors: Sequence<AbstractNode>
        get() {
            check(!this.IsClosed)
            return sequence {
                if (this@AbstractNodeImpl.Parent != null) {
                    this.yield(this@AbstractNodeImpl.Parent!!)
                    this.yieldAll(this@AbstractNodeImpl.Parent!!.Ancestors)
                }
            }
        }
    public override val AncestorsAndSelf: Sequence<AbstractNode>
        get() {
            check(!this.IsClosed)
            return sequence {
                this.yield(this@AbstractNodeImpl)
                this.yieldAll(this@AbstractNodeImpl.Ancestors)
            }
        }

    public override var Activity: EActivity = EActivity.Inactive
        get() {
            check(!this.IsClosed)
            return field
        }
        internal set(value) {
            check(!this.IsClosed)
            check(field != value)
            field = value
        }

    public override val Children: List<AbstractNode>
        get() {
            check(!this.IsClosed)
            return this.ChildrenMutable
        }
    internal val ChildrenMutable: MutableList<AbstractNode> = mutableListOf()
        get() {
            check(!this.IsClosed)
            return field
        }
    public override val Descendants: Sequence<AbstractNode>
        get() {
            check(!this.IsClosed)
            return sequence {
                for (child in this@AbstractNodeImpl.Children) {
                    this.yield(child)
                    this.yieldAll(child.Descendants)
                }
            }
        }
    public override val DescendantsAndSelf: Sequence<AbstractNode>
        get() {
            check(!this.IsClosed)
            return sequence {
                this.yield(this@AbstractNodeImpl)
                this.yieldAll(this@AbstractNodeImpl.Descendants)
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

    public fun Attach(machine: TreeMachine, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = machine
        this.OnAttach(argument)
        if (true) {
            this.Activate(argument)
        }
    }

    public fun Attach(parent: AbstractNode, argument: Any?) {
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
        for (child in this.ChildrenMutable.toList()) {
            (child as AbstractNodeImpl).Activate(argument)
        }
        this.Activity = EActivity.Active
    }

    internal fun Deactivate(argument: Any?) {
        this.Activity = EActivity.Deactivating
        for (child in this.ChildrenMutable.toList().asReversed()) {
            (child as AbstractNodeImpl).Deactivate(argument)
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
