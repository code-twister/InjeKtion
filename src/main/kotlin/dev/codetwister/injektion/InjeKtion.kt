package dev.codetwister.injektion

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
/**
 * Property delegate to inject dependencies.
 */
inline fun <reified T: Any> inject(named: String? = null) = GlobalInjeKtionScope.inject<T>(named)

/**
 * Register a factory for creating instances of type [T].
 *
 * @param named Optional name to distinguish between multiple factories of the same type.
 * @param block Lambda that creates an instance of [T].
 */
inline fun <reified T: Any> factory(named: String? = null, noinline block: () -> T) = GlobalInjeKtionScope.factory(named, block)

/**
 * Register a singleton instance of type [T].
 *
 * @param named Optional name to distinguish between multiple singletons of the same type.
 * @param block Lambda that creates an instance of [T].
 */
inline fun <reified T: Any> single(named: String? = null, noinline block: () -> T) = GlobalInjeKtionScope.single(named, block)

fun createInjeKtionScope(named: String, initialize: InjeKtionScope.() -> Unit): InjeKtionScope =
    GlobalInjeKtionScope.createInjeKtionScope(named, initialize)

fun scoped(name: String): InjeKtionScope =
    GlobalInjeKtionScope.findScopeRecursive(name) ?: throw IllegalStateException("Scope $name not found")
