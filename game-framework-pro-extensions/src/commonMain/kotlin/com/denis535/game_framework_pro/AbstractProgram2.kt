package com.denis535.game_framework_pro

import kotlin.reflect.KClass

public abstract class AbstractProgram2<TTheme, TScreen, TRouter, TApplication> : AbstractProgram, AbstractDependencyProvider where TTheme : AbstractTheme, TScreen : AbstractScreen, TRouter : AbstractRouter, TApplication : AbstractApplication {

    protected val Provider: AbstractDependencyProvider
        get() {
            return AbstractDependencyProvider.Instance!!
        }
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
            if (clazz.isInstance(program)) {
                return program
            }
        }
        this.Theme?.let { theme ->
            if (clazz.isInstance(theme)) {
                return theme
            }
        }
        this.Screen?.let { screen ->
            if (clazz.isInstance(screen)) {
                return screen
            }
        }
        this.Router?.let { router ->
            if (clazz.isInstance(router)) {
                return router
            }
        }
        this.Application?.let { application ->
            if (clazz.isInstance(application)) {
                return application
            }
        }
        return null
    }

}
