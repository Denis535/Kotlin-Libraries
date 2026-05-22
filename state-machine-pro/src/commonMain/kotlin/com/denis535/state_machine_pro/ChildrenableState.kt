package com.denis535.state_machine_pro

public abstract class ChildrenableState<T> : AbstractStateImpl<T> where T : AbstractState<T> {

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
        (child as AbstractStateImpl<T>).Attach(this as T, argument)
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
        (child as AbstractStateImpl<T>).Detach(this as T, argument)
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

    protected open fun Sort(children: MutableList<T>) {
    }

}
