package com.denis535.game_framework_pro

import com.denis535.state_machine_pro.*

public abstract class AbstractTheme : AbstractCloseable {

    protected val Machine: StateMachine
        get() {
            check(!this.IsClosed)
            return field
        }

    public constructor() {
        this.Machine = StateMachine()
    }

    protected override fun OnClose() {
        this.Machine.close()
    }

}

internal class State(public val PlayList: AbstractPlayList) : com.denis535.state_machine_pro.State() {

    protected override fun OnClose() {
        this.PlayList.OnCloseInternal()
    }

    protected override fun OnActivate(argument: Any?) {
        this.PlayList.OnActivateInternal(argument)
    }

    protected override fun OnDeactivate(argument: Any?) {
        this.PlayList.OnDeactivateInternal(argument)
    }

}

public abstract class AbstractPlayList {

    public val IsClosing: Boolean
        get() {
            return this.StateMutable.IsClosing
        }
    public val IsClosed: Boolean
        get() {
            return this.StateMutable.IsClosed
        }

    public val State: AbstractState
        get() {
            check(!this.IsClosed)
            return this.StateMutable
        }
    protected val StateMutable: com.denis535.state_machine_pro.State
        get() {
            check(!field.IsClosed)
            return field
        }

    public constructor() {
        this.StateMutable = State(this)
    }

    internal fun OnCloseInternal() = this.OnClose()
    protected abstract fun OnClose()

    internal fun OnActivateInternal(argument: Any?) = this.OnActivate(argument)
    internal fun OnDeactivateInternal(argument: Any?) = this.OnDeactivate(argument)
    protected abstract fun OnActivate(argument: Any?)
    protected abstract fun OnDeactivate(argument: Any?)

}

public val AbstractState.PlayList: AbstractPlayList
    get() {
        return (this as State).PlayList
    }

public inline fun <reified T> AbstractState.PlayList(): T where  T : AbstractPlayList {
    return this.PlayList as T
}
