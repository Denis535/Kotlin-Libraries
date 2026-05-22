package com.denis535.state_machine_pro

public abstract class State<T> : AbstractStateImpl<T> where T : AbstractState<T> {

    public final override val Children: List<T>
        get() {
            check(!this.IsClosed)
            return listOf()
        }

    public constructor() : super()

}
