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

    internal override fun OnCloseInternal() {
        this.Machine.close()
    }

}

internal class Node(public val Widget: AbstractWidget) : com.denis535.tree_machine_pro.Node() {

    protected override fun OnClose() {
        this.Widget.OnCloseInternal()
    }

    protected override fun OnActivate(argument: Any?) {
        for (ancestor in this.Ancestors.map { it as Node }.toList().asReversed()) { // top-down
            ancestor.Widget.OnBeforeDescendantActivateInternal(this, argument)
        }
        this.Widget.OnActivateInternal(argument)
        for (ancestor in this.Ancestors.map { it as Node }.toList()) { // down-top
            ancestor.Widget.OnAfterDescendantActivateInternal(this, argument)
        }
    }

    protected override fun OnDeactivate(argument: Any?) {
        for (ancestor in this.Ancestors.map { it as Node }.toList().asReversed()) { // top-down
            ancestor.Widget.OnBeforeDescendantDeactivateInternal(this, argument)
        }
        this.Widget.OnDeactivateInternal(argument)
        for (ancestor in this.Ancestors.map { it as Node }.toList()) { // down-top
            ancestor.Widget.OnAfterDescendantDeactivateInternal(this, argument)
        }
    }

    protected override fun Sort(children: MutableList<AbstractNode>) {
        this.Widget.SortInternal(children)
    }

}

public abstract class AbstractWidget {

    public val IsClosing: Boolean
        get() {
            return this.NodeMutable.IsClosing
        }
    public val IsClosed: Boolean
        get() {
            return this.NodeMutable.IsClosed
        }

    public val Node: AbstractNode
        get() {
            check(!this.IsClosed)
            return this.NodeMutable
        }
    protected val NodeMutable: com.denis535.tree_machine_pro.Node
        get() {
            check(!field.IsClosed)
            return field
        }

    public constructor() {
        this.NodeMutable = Node(this)
    }

    internal fun OnCloseInternal() = this.OnClose()
    protected abstract fun OnClose()

    internal fun OnActivateInternal(argument: Any?) = this.OnActivate(argument)
    internal fun OnDeactivateInternal(argument: Any?) = this.OnDeactivate(argument)
    protected abstract fun OnActivate(argument: Any?)
    protected abstract fun OnDeactivate(argument: Any?)

    internal open fun OnBeforeDescendantActivateInternal(descendant: AbstractNode, argument: Any?) = this.OnBeforeDescendantActivate(descendant, argument)
    internal open fun OnAfterDescendantActivateInternal(descendant: AbstractNode, argument: Any?) = this.OnAfterDescendantActivate(descendant, argument)
    internal open fun OnBeforeDescendantDeactivateInternal(descendant: AbstractNode, argument: Any?) = this.OnBeforeDescendantDeactivate(descendant, argument)
    internal open fun OnAfterDescendantDeactivateInternal(descendant: AbstractNode, argument: Any?) = this.OnAfterDescendantDeactivate(descendant, argument)

    protected open fun OnBeforeDescendantActivate(descendant: AbstractNode, argument: Any?) {}
    protected open fun OnAfterDescendantActivate(descendant: AbstractNode, argument: Any?) {}
    protected open fun OnBeforeDescendantDeactivate(descendant: AbstractNode, argument: Any?) {}
    protected open fun OnAfterDescendantDeactivate(descendant: AbstractNode, argument: Any?) {}

    internal fun SortInternal(children: List<AbstractNode>) = this.Sort(children)
    protected open fun Sort(children: List<AbstractNode>) {}

}

public abstract class AbstractViewableWidget : AbstractWidget {

    public var View: Any? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        protected set(value) {
            check(!this.IsClosed)
            field = value
        }

    public constructor()

}

public val AbstractNode.Widget: AbstractWidget
    get() {
        return (this as Node).Widget
    }

public inline fun <reified T> AbstractNode.Widget(): T where  T : AbstractWidget {
    return this.Widget as T
}
