package dev.codetwister.injektion

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

sealed class InjeKtionScope {
    val factories = mutableMapOf<Pair<KClass<out Any>, String?>, () -> Any>()

    inline fun <reified T: Any> inject(named: String? = null) =
        object: ReadOnlyProperty<Any, T> {
            private val value: T by lazy {
                @Suppress("UNCHECKED_CAST")
                factories.getValue(T::class to named).invoke() as T
            }
            override fun getValue(thisRef: Any, property: KProperty<*>): T = value
        }

    internal fun getFactoryRecursive(key: Pair<KClass<out Any>, String?>): () -> Any {
        return factories[key]
            ?: (this as? InjeKtionScopeNamed)
                ?.parent
                ?.getFactoryRecursive(key)
            ?: throw IllegalStateException("No factory found for $key")
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

    fun createInjeKtionScope(name: String, initialize: InjeKtionScope.() -> Unit): InjeKtionScope {
        return InjeKtionScopeNamed(
            parent = this,
            name = name,
        ).also {
            initialize()
        }
    }

    fun clear() {
        factories.clear()
    }
}

object GlobalInjeKtionScope : InjeKtionScope()

internal data class InjeKtionScopeNamed(
    val name: String,
    val parent: InjeKtionScope = GlobalInjeKtionScope,
) : InjeKtionScope()
