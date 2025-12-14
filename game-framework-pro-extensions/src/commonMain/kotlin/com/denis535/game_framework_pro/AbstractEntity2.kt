package com.denis535.game_framework_pro

public abstract class AbstractEntity2 : AbstractEntity {

    protected val Provider: AbstractDependencyProvider
        get() {
            return AbstractDependencyProvider.Instance!!
        }

    public constructor()

}
