package com.denis535.game_framework_pro

public abstract class AbstractGame2 : AbstractGame {

    protected val Provider: DependencyProvider
        get() {
            check(!this.IsClosed)
            return DependencyProvider.Instance!!
        }

    public constructor()

    protected final override fun OnCloseInternal() {
        super.OnCloseInternal()
    }

}
