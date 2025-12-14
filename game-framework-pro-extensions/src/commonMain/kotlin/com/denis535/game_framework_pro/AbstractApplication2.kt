package com.denis535.game_framework_pro

public abstract class AbstractApplication2 : AbstractApplication {

    protected val Provider: AbstractDependencyProvider
        get() {
            return AbstractDependencyProvider.Instance!!
        }

    public constructor()

}
