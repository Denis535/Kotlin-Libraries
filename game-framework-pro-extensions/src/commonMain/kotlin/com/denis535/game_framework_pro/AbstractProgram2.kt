package com.denis535.game_framework_pro

import kotlin.reflect.KClass

public abstract class AbstractProgram2<TTheme, TScreen, TRouter, TApplication> : AbstractProgram, AbstractDependencyProvider where TTheme : AbstractTheme, TScreen : AbstractScreen, TRouter : AbstractRouter, TApplication : AbstractApplication {

    protected var Theme: TTheme? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        set(value) {
            check(!this.IsClosed)
            field = value
        }
    protected var Screen: TScreen? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        set(value) {
            check(!this.IsClosed)
            field = value
        }
    protected var Router: TRouter? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        set(value) {
            check(!this.IsClosed)
            field = value
        }
    protected var Application: TApplication? = null
        get() {
            check(!this.IsClosed)
            return field
        }
        set(value) {
            check(!this.IsClosed)
            field = value
        }

    public constructor() {
        AbstractDependencyProvider.Instance = this
    }

    protected override fun OnClose() {
        AbstractDependencyProvider.Instance = null
        super.OnClose()
    }

    public override fun GetDependencyInternal(clazz: KClass<*>, argument: Any?): Any? {
        check(!this.IsClosed)
        this.let { program ->
            if (clazz == program::class || clazz == AbstractProgram::class) {
                return program
            }
        }
        this.Theme?.let { theme ->
            if (clazz == theme::class || clazz == AbstractTheme::class) {
                return theme
            }
        }
        this.Screen?.let { screen ->
            if (clazz == screen::class || clazz == AbstractScreen::class) {
                return screen
            }
        }
        this.Router?.let { router ->
            if (clazz == router::class || clazz == AbstractRouter::class) {
                return router
            }
        }
        this.Application?.let { application ->
            if (clazz == application::class || clazz == AbstractApplication::class) {
                return application
            }
        }
        return null
    }

}
