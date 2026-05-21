package com.denis535.tree_machine_pro

public open class Node : AbstractNodeImpl {

    public constructor()

    public fun AddChild(child: AbstractNode, argument: Any?) {
        check(!this.IsClosed)
        check(!this.Children.contains(child))
        this.ChildrenMutable.add(child)
        this.Sort(this.ChildrenMutable)
        (child as AbstractNodeImpl).Attach(this, argument)
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
        (child as AbstractNodeImpl).Detach(this, argument)
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
        this.Owner.let { owner ->
            if (owner is TreeMachine) {
                owner.SetRoot(null, argument, callback)
            } else {
                (owner as Node).RemoveChild(this, argument, callback)
            }
        }
    }

    protected open fun Sort(children: MutableList<AbstractNode>) {
    }

}
