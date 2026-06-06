# Scoped Injection

By default, the injeKtion library works with a default "global" scope (`GlobalInjeKtionScope`).
What this means singletons will be held in this scope and never get cleaned up (basically static),
unless you call `GlobalInjeKtionScope.clear()`.

## Disclaimer

The lifecycle of a scope is as important to maintain as managing any other static content within your code.
Singletons and factories are registered within a scope and will remain there until the scope is explicitly cleared.
The library does not provide automatic lifecycle management or "hand-holding" regarding memory management;
the user is responsible for properly clearing scopes (e.g., `scope.clear()`) to avoid memory leaks and ensure the quality of their own codebase.


## Creating Scopes

You can create named scopes to better manage the lifecycle of your dependencies. Scopes can be nested.

```kotlin
val myScope = createInjeKtionScope("MyScope") {
    single { MyScopedService() }
    factory { MyScopedFactory() }
}

// Or nested
createInjeKtionScope("Parent") {
    createInjeKtionScope("Child") {
        factory { "Child dependency" }
    }
}
```

## Using Scopes

To inject from a specific scope, use the `inject` delegate from that scope instance:

```kotlin
class MyController(scope: InjeKtionScope) {
    private val scopedService by scope.inject<MyScopedService>()
}
```

## Recursive Resolution

If a dependency is not found in the current scope, injeKtion will look up in its parent scope, recursively, until it reaches the `GlobalInjeKtionScope`.

```kotlin
single { "Global Dependency" }

val myScope = createInjeKtionScope("MyScope") {
    // This scope doesn't define String, so it will look in GlobalInjeKtionScope
}

class MyClass {
    val data by myScope.inject<String>() // Returns "Global Dependency"
}
```

## Finding Scopes

You can retrieve a scope by its name from anywhere. This is particularly useful for injecting dependencies in deeply nested class structures where passing the scope through constructors would be cumbersome:

```kotlin
// Define a scope somewhere
createInjeKtionScope("UserSession") {
    single { UserProfile(name = "John Doe") }
}

// Deeply nested class that doesn't have direct access to the scope object
class DeeplyNestedComponent {
    private val userProfile by scoped("UserSession").inject<UserProfile>()
    
    fun printUser() = println(userProfile.name)
}
```

## Clearing Scopes

Clearing a scope will remove all its registered factories and also clear and remove all its child scopes.

```kotlin
myScope.clear()
// or clear everything
GlobalInjeKtionScope.clear()
```
