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
    internal class Node2 : Node {

        public val Widget: AbstractWidget
            get() {
                check(!this.IsClosed)
                return field
            }

        public constructor(widget: AbstractWidget) {
            this.Widget = widget
        }

        protected override fun OnClose() {
            this.Widget.OnClose()
            this.Widget.OnCloseInternal()
        }

        protected override fun OnActivate(argument: Any?) {
            for (ancestor in this.Ancestors.map { it as Node }.toList().asReversed()) { // top-down
                ancestor.Widget.OnBeforeDescendantActivate(this, argument)
            }
            this.Widget.OnActivate(argument)
            for (ancestor in this.Ancestors.map { it as Node }.toList()) { // down-top
                ancestor.Widget.OnAfterDescendantActivate(this, argument)
            }
        }

        protected override fun OnDeactivate(argument: Any?) {
            for (ancestor in this.Ancestors.map { it as Node }.toList().asReversed()) { // top-down
                ancestor.Widget.OnBeforeDescendantDeactivate(this, argument)
            }
            this.Widget.OnDeactivate(argument)
            for (ancestor in this.Ancestors.map { it as Node }.toList()) { // down-top
                ancestor.Widget.OnAfterDescendantDeactivate(this, argument)
            }
        }

        protected override fun Sort(children: MutableList<AbstractNode>) {
            this.Widget.Sort(children)
        }

    }

    public val Node: AbstractNode
        get() = this.NodeMutable
    protected val NodeMutable: Node

    public constructor() {
        this.NodeMutable = Node2(this)
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
            check(!this.Node.IsClosed)
            return field
        }
        protected set(value) {
            check(!this.Node.IsClosed)
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
