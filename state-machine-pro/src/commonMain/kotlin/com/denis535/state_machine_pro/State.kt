package com.denis535.state_machine_pro

public open class State : AbstractState {

    public final override val Children: List<AbstractState>
        get() {
            check(!this.IsClosed)
            return listOf()
        }

    public constructor()

}
