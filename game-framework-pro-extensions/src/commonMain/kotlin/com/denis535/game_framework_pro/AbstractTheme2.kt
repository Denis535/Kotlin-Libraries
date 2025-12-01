package com.denis535.game_framework_pro

public abstract class AbstractTheme2<TRouter, TApplication> : AbstractTheme where TRouter : AbstractRouter, TApplication : AbstractApplication {

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
        this.Router = this.Provider.RequireDependency<TRouter>(AbstractRouter::class)
        this.Application = this.Provider.RequireDependency<TApplication>(AbstractApplication::class)
    }

    protected override fun OnClose() {
        super.OnClose()
    }

}

public abstract class AbstractPlayList2 : AbstractPlayList {

    protected val Provider: AbstractDependencyProvider
        get() {
            return AbstractDependencyProvider.Instance!!
        }

    public constructor()

}
