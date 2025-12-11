package com.denis535.tree_machine_pro

public class TreeMachine : AbstractTreeMachine {

    public override var Root: AbstractNode? = null
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

    public constructor()

    public fun SetRoot(root: AbstractNode?, argument: Any?, callback: Proc2<AbstractNode, Any?>? = null) {
        check(!this.IsClosed)
        if (this.Root != null) {
            this.RemoveRoot(this.Root!!, argument, callback)
        }
        if (root != null) {
            this.AddRoot(root, argument)
        }
    }

    private fun AddRoot(root: AbstractNode, argument: Any?) {
        check(!this.IsClosed)
        check(this.Root == null)
        this.Root = root
        this.Root!!.Attach(this, argument)
    }

    private fun RemoveRoot(root: AbstractNode, argument: Any?, callback: Proc2<AbstractNode, Any?>? = null) {
        check(!this.IsClosed)
        check(this.Root == root)
        this.Root!!.Detach(this, argument)
        this.Root = null
        if (callback != null) {
            callback.invoke(root, argument)
        } else {
            root.close()
        }
    }

}
