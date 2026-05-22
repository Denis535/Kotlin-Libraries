package com.denis535.tree_machine_pro

public abstract class Node<T> : AbstractNodeImpl<T> where  T : AbstractNode<T> {

    public final override val Children: List<T>
        get() {
            check(!this.IsClosed)
            return this.ChildrenMutable
        }
    private val ChildrenMutable: MutableList<T> = mutableListOf()
        get() {
            check(!this.IsClosed)
            return field
        }

    public constructor()

    public fun AddChild(child: T, argument: Any?) {
        check(!this.IsClosed)
        check(!this.Children.contains(child))
        this.ChildrenMutable.add(child)
        this.Sort(this.ChildrenMutable)
        (child as AbstractNodeImpl<T>).Attach(this, argument)
    }

    public fun AddChildren(children: Array<T>, argument: Any?) {
        check(!this.IsClosed)
        for (child in children) {
            this.AddChild(child, argument)
        }
    }

    public fun RemoveChild(child: T, argument: Any?, callback: Proc2<T, Any?>? = null) {
        check(!this.IsClosed)
        check(this.Children.contains(child))
        (child as AbstractNodeImpl<T>).Detach(this, argument)
        this.ChildrenMutable.remove(child)
        if (callback != null) {
            callback.invoke(child, argument)
        } else {
            child.close()
        }
    }

    public fun RemoveChildren(predicate: Predicate1<T>, argument: Any?, callback: Proc2<T, Any?>? = null): Int {
        check(!this.IsClosed)
        var count = 0
        for (child in this.Children.reversed().filter(predicate)) {
            this.RemoveChild(child, argument, callback)
            count++
        }
        return count
    }

    public fun RemoveSelf(argument: Any?, callback: Proc2<T, Any?>? = null) {
        check(!this.IsClosed)
        check(this.Owner != null)
        this.Owner.let { owner ->
            if (owner is TreeMachine<*>) {
                (owner as TreeMachine<T>).RemoveRoot(this as T, argument, callback)
            } else {
                (owner as Node<T>).RemoveChild(this as T, argument, callback)
            }
        }
    }

    protected open fun Sort(children: MutableList<T>) {
    }

}
