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

    protected override fun OnCloseInternal() {
        this.Machine.close()
    }

}

public abstract class AbstractPlayList {
    internal class State2 : State {

        public val PlayList: AbstractPlayList
            get() {
                check(!this.IsClosed)
                return field
            }

        public constructor(playList: AbstractPlayList) {
            this.PlayList = playList
        }

        protected override fun OnClose() {
            this.PlayList.OnClose()
            this.PlayList.OnCloseInternal()
        }

        protected override fun OnActivate(argument: Any?) {
            this.PlayList.OnActivate(argument)
        }

        protected override fun OnDeactivate(argument: Any?) {
            this.PlayList.OnDeactivate(argument)
        }

    }

    public val State: AbstractState
        get() = this.StateMutable
    protected val StateMutable: State

    public constructor() {
        this.StateMutable = State2(this)
    }

    protected abstract fun OnClose()
    protected open fun OnCloseInternal() {}

    protected abstract fun OnActivate(argument: Any?)
    protected abstract fun OnDeactivate(argument: Any?)

}
