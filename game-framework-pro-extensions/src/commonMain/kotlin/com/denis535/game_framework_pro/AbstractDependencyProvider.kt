package com.denis535.game_framework_pro

import kotlin.reflect.KClass

public interface AbstractDependencyProvider {
    public companion object {

        internal var Instance: AbstractDependencyProvider? = null
            set(value) {
                if (value != null) {
                    check(field == null)
                    field = value
                } else {
                    check(field != null)
                    field = null
                }
            }
    }

    fun GetDependencyInternal(clazz: KClass<*>, argument: Any? = null): Any?

}

fun <T : Any> AbstractDependencyProvider.GetDependency(clazz: KClass<*>, argument: Any? = null): T? {
    return this.GetDependencyInternal(clazz, argument) as T?
}

fun <T : Any> AbstractDependencyProvider.RequireDependency(clazz: KClass<*>, argument: Any? = null): T {
    return this.GetDependencyInternal(clazz, argument) as T? ?: error("Dependency $clazz (${argument ?: "Null"}) was not found")
}
