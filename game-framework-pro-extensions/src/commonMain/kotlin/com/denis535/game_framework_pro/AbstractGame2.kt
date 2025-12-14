package com.denis535.game_framework_pro

public abstract class AbstractGame2 : AbstractGame {

    protected val Provider: AbstractDependencyProvider
        get() {
            return AbstractDependencyProvider.Instance!!
        }

    public constructor()

}
