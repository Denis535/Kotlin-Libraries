package com.denis535.game_framework_pro

public abstract class AbstractRouter2<TTheme, TScreen, TApplication> : AbstractRouter where TTheme : AbstractTheme, TScreen : AbstractScreen, TApplication : AbstractApplication {

    protected val Provider: AbstractDependencyProvider
        get() {
            return AbstractDependencyProvider.Instance!!
        }
    protected val Theme: TTheme
        get() {
            check(!this.IsClosed)
            return this.Provider.RequireDependency(AbstractTheme::class)
        }
    protected val Screen: TScreen
        get() {
            check(!this.IsClosed)
            return this.Provider.RequireDependency(AbstractScreen::class)
        }
    protected val Application: TApplication
        get() {
            check(!this.IsClosed)
            return field
        }

    public constructor() {
        this.Application = this.Provider.RequireDependency(AbstractApplication::class)
    }

    protected override fun OnClose() {
        super.OnClose()
    }

}
