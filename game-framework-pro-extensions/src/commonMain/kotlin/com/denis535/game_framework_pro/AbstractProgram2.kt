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

    public override fun GetDependency(clazz: KClass<*>, argument: Any?): Any? {
        check(!this.IsClosed)
        return when {
            this.Theme != null && clazz.java.isAssignableFrom(this.Theme!!::class.java) -> this.Theme
            this.Screen != null && clazz.java.isAssignableFrom(this.Screen!!::class.java) -> this.Screen
            this.Router != null && clazz.java.isAssignableFrom(this.Router!!::class.java) -> this.Router
            this.Application != null && clazz.java.isAssignableFrom(this.Application!!::class.java) -> this.Application
            else -> null
        }
    }

}
