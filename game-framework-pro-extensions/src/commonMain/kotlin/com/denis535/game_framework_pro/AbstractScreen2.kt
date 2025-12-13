package com.denis535.game_framework_pro

public abstract class AbstractScreen2<TRouter, TApplication> : AbstractScreen where TRouter : AbstractRouter, TApplication : AbstractApplication {

    protected val Provider: AbstractDependencyProvider
        get() {
            return AbstractDependencyProvider.Instance!!
        }
    protected val Router: TRouter
        get() {
            check(!this.IsClosed)
            return field
        }
    protected val Application: TApplication
        get() {
            check(!this.IsClosed)
            return field
        }

    public constructor() {
        this.Router = this.Provider.RequireDependency(AbstractRouter::class)
        this.Application = this.Provider.RequireDependency(AbstractApplication::class)
    }

    protected override fun OnClose() {
        super.OnClose()
    }

}

public abstract class AbstractWidget2 : AbstractWidget {

    protected val Provider: AbstractDependencyProvider
        get() {
            return AbstractDependencyProvider.Instance!!
        }

    public constructor()

}

public abstract class AbstractViewableWidget2 : AbstractViewableWidget {

    protected val Provider: AbstractDependencyProvider
        get() {
            return AbstractDependencyProvider.Instance!!
        }

    public constructor()

}
