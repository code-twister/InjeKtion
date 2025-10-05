package dev.codetwister.injektion

import dev.codetwister.injektion.InjeKtion.factories
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/** A simple dependency injection framework for Kotlin.
 *
 * Usage:
 * ```
 * // Define a singleton
 * single { MyService() }
 *
 * // Define a factory
 * factory { MyRepository() }
 *
 * // Inject dependencies
 * class MyController {
 *     val service: MyService by inject()
 *     val repository: MyRepository by inject()
 * }
 * ```
 */
object InjeKtion {
    val factories = mutableMapOf<Pair<KClass<out Any>, String?>, () -> Any>()
}

/**
 * Property delegate to inject dependencies.
 */
inline fun <reified T: Any> inject(named: String? = null) =
    object: ReadOnlyProperty<Any, T> {
        private val value: T by lazy {
            @Suppress("UNCHECKED_CAST")
            factories.getValue(T::class to named).invoke() as T
        }
        override fun getValue(thisRef: Any, property: KProperty<*>): T = value
    }

/**
 * Register a factory for creating instances of type [T].
 *
 * @param named Optional name to distinguish between multiple factories of the same type.
 * @param block Lambda that creates an instance of [T].
 */
inline fun <reified T: Any> factory(named: String? = null, noinline block: () -> T) {
    factories[T::class to named] = block
}

/**
 * Register a singleton instance of type [T].
 *
 * @param named Optional name to distinguish between multiple singletons of the same type.
 * @param block Lambda that creates an instance of [T].
 */
inline fun <reified T: Any> single(named: String? = null, noinline block: () -> T) {
    block.invoke().let { factory(named) { it } }
}
