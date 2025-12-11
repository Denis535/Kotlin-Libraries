package com.denis535.tree_machine_pro

public class Node : AbstractNode {

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

    public override val Machine: AbstractTreeMachine?
        get() {
            check(!this.IsClosed)
            return when (val owner = this.Owner) {
                is AbstractTreeMachine -> owner
                is AbstractNode -> owner.Machine as AbstractTreeMachine
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
                if (this@Node.Parent != null) {
                    this.yield(this@Node.Parent!!)
                    this.yieldAll(this@Node.Parent!!.Ancestors)
                }
            }
        }
    public override val AncestorsAndSelf: Sequence<AbstractNode>
        get() {
            check(!this.IsClosed)
            return sequence {
                this.yield(this@Node)
                this.yieldAll(this@Node.Ancestors)
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

    public override val Children: List<AbstractNode>
        get() {
            check(!this.IsClosed)
            return this.ChildrenMutable
        }
    private val ChildrenMutable: MutableList<AbstractNode> = mutableListOf()
        get() {
            check(!this.IsClosed)
            return field
        }
    public override val Descendants: Sequence<AbstractNode>
        get() {
            check(!this.IsClosed)
            return sequence {
                for (child in this@Node.Children) {
                    this.yield(child)
                    this.yieldAll(child.Descendants)
                }
            }
        }
    public override val DescendantsAndSelf: Sequence<AbstractNode>
        get() {
            check(!this.IsClosed)
            return sequence {
                this.yield(this@Node)
                this.yieldAll(this@Node.Descendants)
            }
        }

    public var SortDelegate: Proc1<MutableList<AbstractNode>>? = null
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

    public val UserData: Any?
        get() {
            check(!this.IsClosed)
            return field
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

    public constructor(userData: Any?) {
        this.UserData = userData
    }

    internal override fun Attach(machine: AbstractTreeMachine, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = machine
        this.OnAttachCallback?.invoke(argument)
        if (true) {
            this.Activate(argument)
        }
    }

    internal override fun Attach(parent: AbstractNode, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == null)
        this.Owner = parent
        this.OnAttachCallback?.invoke(argument)
        if (this.Parent!!.Activity == EActivity.Active) {
            this.Activate(argument)
        }
    }

    internal override fun Detach(machine: AbstractTreeMachine, argument: Any?) {
        check(!this.IsClosed)
        check(this.Owner == machine)
        if (true) {
            this.Deactivate(argument)
        }
        this.OnDetachCallback?.invoke(argument)
        this.Owner = null
    }

    internal override fun Detach(parent: AbstractNode, argument: Any?) {
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

    public fun AddChild(child: AbstractNode, argument: Any?) {
        check(!this.IsClosed)
        check(!this.Children.contains(child))
        this.ChildrenMutable.add(child)
        this.SortDelegate?.invoke(this.ChildrenMutable)
        child.Attach(this, argument)
    }

    public fun AddChildren(children: Array<AbstractNode>, argument: Any?) {
        check(!this.IsClosed)
        for (child in children) {
            this.AddChild(child, argument)
        }
    }

    public fun RemoveChild(child: AbstractNode, argument: Any?, callback: Proc2<AbstractNode, Any?>? = null) {
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

    public fun RemoveChildren(predicate: Predicate1<AbstractNode>, argument: Any?, callback: Proc2<AbstractNode, Any?>? = null): Int {
        check(!this.IsClosed)
        var count = 0
        for (child in this.Children.reversed().filter(predicate)) {
            this.RemoveChild(child, argument, callback)
            count++
        }
        return count
    }

    public fun RemoveSelf(argument: Any?, callback: Proc2<AbstractNode, Any?>? = null) {
        check(!this.IsClosed)
        check(this.Owner != null)
        when (val owner = this.Owner) {
            is TreeMachine -> owner.SetRoot(null, argument, callback)
            is Node -> owner.RemoveChild(this, argument, callback)
        }
    }

}
