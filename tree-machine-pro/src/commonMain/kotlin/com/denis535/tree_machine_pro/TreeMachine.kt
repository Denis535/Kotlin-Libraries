package com.denis535.tree_machine_pro

public class TreeMachine : AbstractTreeMachine {

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
