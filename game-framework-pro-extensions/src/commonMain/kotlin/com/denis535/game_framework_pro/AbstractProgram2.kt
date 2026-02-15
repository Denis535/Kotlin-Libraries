package com.denis535.game_framework_pro

import kotlin.reflect.*

public abstract class AbstractProgram2<TTheme, TScreen, TRouter, TApplication> : AbstractProgram, DependencyProvider where TTheme : AbstractTheme, TScreen : AbstractScreen, TRouter : AbstractRouter, TApplication : AbstractApplication {

    protected val Provider: DependencyProvider
        get() {
            check(!this.IsClosed)
            return DependencyProvider.Instance!!
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
        DependencyProvider.Instance = this
    }

    protected final override fun OnCloseInternal() {
        this.Theme!!.close()
        this.Screen!!.close()
        this.Router!!.close()
        this.Application!!.close()
        DependencyProvider.Instance = null
        super.OnCloseInternal()
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
