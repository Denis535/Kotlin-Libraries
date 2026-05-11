package com.denis535.game_framework_pro

import com.denis535.tree_machine_pro.*

public abstract class AbstractScreen : AbstractCloseable {

    protected val Machine: TreeMachine
        get() {
            check(!this.IsClosed)
            return field
        }

    public constructor() {
        this.Machine = TreeMachine()
    }

    protected override fun OnCloseInternal() {
        this.Machine.close()
    }

}

public abstract class AbstractWidget {
    public class Node : com.denis535.tree_machine_pro.Node {

        public val SubObject: AbstractWidget
            get() {
                check(!this.IsClosed)
                return field
            }

        public constructor(subObject: AbstractWidget) {
            this.SubObject = subObject
        }

        protected override fun OnClose() {
            this.SubObject.OnClose()
            this.SubObject.OnCloseInternal()
        }

        protected override fun OnActivate(argument: Any?) {
            for (ancestor in this.Ancestors.map { it as Node }.toList().asReversed()) { // top-down
                ancestor.SubObject.OnBeforeDescendantActivate(this, argument)
            }
            this.SubObject.OnActivate(argument)
            for (ancestor in this.Ancestors.map { it as Node }.toList()) { // down-top
                ancestor.SubObject.OnAfterDescendantActivate(this, argument)
            }
        }

        protected override fun OnDeactivate(argument: Any?) {
            for (ancestor in this.Ancestors.map { it as Node }.toList().asReversed()) { // top-down
                ancestor.SubObject.OnBeforeDescendantDeactivate(this, argument)
            }
            this.SubObject.OnDeactivate(argument)
            for (ancestor in this.Ancestors.map { it as Node }.toList()) { // down-top
                ancestor.SubObject.OnAfterDescendantDeactivate(this, argument)
            }
        }

        protected override fun Sort(children: MutableList<AbstractNode>) {
            this.SubObject.Sort(children)
        }

    }

    public val BaseObject: Node

    public constructor() {
        this.BaseObject = Node(this)
    }

    protected abstract fun OnClose()
    protected open fun OnCloseInternal() {}

    protected abstract fun OnActivate(argument: Any?)
    protected abstract fun OnDeactivate(argument: Any?)

    protected open fun OnBeforeDescendantActivate(descendant: AbstractNode, argument: Any?) {}
    protected open fun OnAfterDescendantActivate(descendant: AbstractNode, argument: Any?) {}
    protected open fun OnBeforeDescendantDeactivate(descendant: AbstractNode, argument: Any?) {}
    protected open fun OnAfterDescendantDeactivate(descendant: AbstractNode, argument: Any?) {}

    protected open fun Sort(children: List<AbstractNode>) {}

}

public abstract class AbstractViewableWidget : AbstractWidget {

    public var View: Any? = null
        get() {
            check(!this.BaseObject.IsClosed)
            return field
        }
        protected set(value) {
            check(!this.BaseObject.IsClosed)
            field = value
        }

    public constructor()

    protected override fun OnCloseInternal() {
        this.View?.let { view ->
            if (view is AutoCloseable) {
                view.close()
            }
        }
    }

}
