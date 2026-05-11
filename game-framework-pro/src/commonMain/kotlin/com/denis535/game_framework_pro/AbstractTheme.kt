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
    public class State : com.denis535.state_machine_pro.State {

        public val SubObject: AbstractPlayList
            get() {
                check(!this.IsClosed)
                return field
            }

        public constructor(subObject: AbstractPlayList) {
            this.SubObject = subObject
        }

        protected override fun OnClose() {
            this.SubObject.OnClose()
            this.SubObject.OnCloseInternal()
        }

        protected override fun OnActivate(argument: Any?) {
            this.SubObject.OnActivate(argument)
        }

        protected override fun OnDeactivate(argument: Any?) {
            this.SubObject.OnDeactivate(argument)
        }

    }

    public val BaseObject: State

    public constructor() {
        this.BaseObject = State(this)
    }

    protected abstract fun OnClose()
    protected open fun OnCloseInternal() {}

    protected abstract fun OnActivate(argument: Any?)
    protected abstract fun OnDeactivate(argument: Any?)

}
