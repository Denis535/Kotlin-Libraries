package com.denis535.tree_machine_pro

public class TreeMachine<T> : AutoCloseable where  T : AbstractNode<T> {

    private var Lifecycle = ELifecycle.Alive

    public val IsClosing: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closing
        }
    public val IsClosed: Boolean
        get() {
            return this.Lifecycle == ELifecycle.Closed
        }

    public var Root: T? = null
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

    public constructor()

    public override fun close() {
        check(!this.IsClosing)
        check(!this.IsClosed)
        this.Lifecycle = ELifecycle.Closing
        check(this.Root.let { it == null || it.IsClosed })
        this.Lifecycle = ELifecycle.Closed
    }

    public fun SetRoot(root: T?, argument: Any?, callback: Proc2<T, Any?>? = null) {
        check(!this.IsClosed)
        if (this.Root != null) {
            this.RemoveRoot(this.Root!!, argument, callback)
        }
        if (root != null) {
            this.AddRoot(root, argument)
        }
    }

    private fun AddRoot(root: T, argument: Any?) {
        check(!this.IsClosed)
        check(this.Root == null)
        this.Root = root
        (this.Root as AbstractNodeImpl<T>).Attach(this, argument)
    }

    internal fun RemoveRoot(root: T, argument: Any?, callback: Proc2<T, Any?>? = null) {
        check(!this.IsClosed)
        check(this.Root == root)
        (this.Root as AbstractNodeImpl<T>).Detach(this, argument)
        this.Root = null
        if (callback != null) {
            callback.invoke(root, argument)
        } else {
            root.close()
        }
    }

}
