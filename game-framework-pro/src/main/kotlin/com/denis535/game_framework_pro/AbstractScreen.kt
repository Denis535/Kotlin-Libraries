package com.denis535.game_framework_pro

import com.denis535.tree_machine_pro.*

public abstract class AbstractScreen : AbstractCloseable {

    protected val Machine: TreeMachine<AbstractScreen, AbstractWidget>
        get() {
            check(!this.IsClosed)
            return field
        }

    public constructor() {
        this.Machine = TreeMachine<AbstractScreen, AbstractWidget>(this)
    }

    protected override fun OnClose() {
        this.Machine.close()
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

    protected val Screen: AbstractScreen?
        get() {
            check(!this.IsClosed)
            return this.NodeMutable.Machine?.UserData
        }
    public val Node: AbstractNode<AbstractScreen, AbstractWidget>
        get() {
            check(!this.IsClosed)
            return this.NodeMutable
        }
    protected val NodeMutable: Node<AbstractScreen, AbstractWidget>
        get() {
            check(!field.IsClosed)
            return field
        }

    public var OnBeforeDescendantActivateCallback: Proc2<AbstractNode<AbstractScreen, AbstractWidget>, Any?>? = null
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
    public var OnAfterDescendantActivateCallback: Proc2<AbstractNode<AbstractScreen, AbstractWidget>, Any?>? = null
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

    public var OnBeforeDescendantDeactivateCallback: Proc2<AbstractNode<AbstractScreen, AbstractWidget>, Any?>? = null
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
    public var OnAfterDescendantDeactivateCallback: Proc2<AbstractNode<AbstractScreen, AbstractWidget>, Any?>? = null
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

    public constructor() {
        this.NodeMutable = Node<AbstractScreen, AbstractWidget>(this).apply {
            this.SortDelegate = this@AbstractWidget::Sort
            this.OnCloseCallback = this@AbstractWidget::OnClose
            this.OnActivateCallback = { argument ->
                for (ancestor in this@AbstractWidget.Node.Ancestors.toList().asReversed()) { // root-down
                    ancestor.UserData.OnBeforeDescendantActivateCallback?.invoke(this@AbstractWidget.Node, argument)
                    ancestor.UserData.OnBeforeDescendantActivate(this@AbstractWidget.Node, argument)
                }
                this@AbstractWidget.OnActivate(argument)
                for (ancestor in this@AbstractWidget.Node.Ancestors.toList()) { // down-root
                    ancestor.UserData.OnAfterDescendantActivate(this@AbstractWidget.Node, argument)
                    ancestor.UserData.OnAfterDescendantActivateCallback?.invoke(this@AbstractWidget.Node, argument)
                }
            }
            this.OnDeactivateCallback = { argument ->
                for (ancestor in this@AbstractWidget.Node.Ancestors.toList().asReversed()) { // root-down
                    ancestor.UserData.OnBeforeDescendantDeactivateCallback?.invoke(this@AbstractWidget.Node, argument)
                    ancestor.UserData.OnBeforeDescendantDeactivate(this@AbstractWidget.Node, argument)
                }
                this@AbstractWidget.OnDeactivate(argument)
                for (ancestor in this@AbstractWidget.Node.Ancestors.toList()) { // down-root
                    ancestor.UserData.OnAfterDescendantDeactivate(this@AbstractWidget.Node, argument)
                    ancestor.UserData.OnAfterDescendantDeactivateCallback?.invoke(this@AbstractWidget.Node, argument)
                }
            }
        }
    }

    protected abstract fun OnClose()

    protected abstract fun OnActivate(argument: Any?)
    protected abstract fun OnDeactivate(argument: Any?)

    protected open fun OnBeforeDescendantActivate(descendant: AbstractNode<AbstractScreen, AbstractWidget>, argument: Any?) {
    }

    protected open fun OnAfterDescendantActivate(descendant: AbstractNode<AbstractScreen, AbstractWidget>, argument: Any?) {
    }

    protected open fun OnBeforeDescendantDeactivate(descendant: AbstractNode<AbstractScreen, AbstractWidget>, argument: Any?) {
    }

    protected open fun OnAfterDescendantDeactivate(descendant: AbstractNode<AbstractScreen, AbstractWidget>, argument: Any?) {
    }

    protected open fun Sort(children: List<AbstractNode<AbstractScreen, AbstractWidget>>) {
    }

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

public val AbstractNode<AbstractScreen, AbstractWidget>.Widget: AbstractWidget
    get() {
        return this.UserData
    }

public fun <T> AbstractNode<AbstractScreen, AbstractWidget>.Widget(): T where  T : AbstractWidget {
    return this.UserData as T
}
