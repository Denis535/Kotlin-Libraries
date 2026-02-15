package com.denis535.game_framework_pro

import kotlin.reflect.*

public interface DependencyProvider {
    public companion object {

        internal var Instance: DependencyProvider? = null
            set(value) {
                if (field != null) {
                    require(value == null)
                } else {
                    require(value != null)
                }
                field = value
            }
    }

    fun GetDependencyInternal(clazz: KClass<*>, argument: Any?): Any?

}

public fun <T : Any> DependencyProvider.GetDependency(clazz: KClass<*>, argument: Any? = null): T? {
    val result = this.GetDependencyInternal(clazz, argument) as T?
    return result
}

public fun <T : Any> DependencyProvider.RequireDependency(clazz: KClass<*>, argument: Any? = null): T {
    val result = this.GetDependencyInternal(clazz, argument) as T?
    if (result != null) {
        return result
    }
    error("Dependency is missing: $clazz (${argument ?: "Null"})")
}
