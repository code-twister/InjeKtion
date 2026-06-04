# injeKtion

Minimalistic Dependency Injection Library for Kotlin

## Gradle Dependency

### using classic gradle format
```gradle
dependencies {
    ...
    implementation("dev.codetwister:injeKtion:1.0.1")
    ...
}
```

### using version catalog

**/gradle/libs.versions.toml**
```toml
[versions]
injeKtionVersion = "1.0.1"

[libraries]
injeKtion = { module = "dev.codetwister:injeKtion", version.ref = "injeKtionVersion" }
```

**/module/build.gradle.kts**
```gradle
dependencies {
    ...
    implementation(libs.injeKtion)
    ...
}
```

## Features

**Creating bindings:**
```kotlin
factory<MyInterface>(named = "specialName") { SomeImplementation() }
factory<OtherInterface> { doSomethingHereToCreateAnInstance() }
single { AnotherClass() }
single(named = "specialString") { "Something special" }

```

**Use injection:**

```kotlin
class SomeClass {
   private val dependencyOne by inject<MyInterface>(named = "specialName")
   private val dependencyTwo: OtherInterface by inject()
   private val anotherClass by inject<AnotherClass>()
   private val test: String by inject(named = "specialString")
   // ...
}
```

## Scopes

By default, the injeKtion library works with a default "global" scope. 
What this means singletons will be held in this scope and never get cleaned up (basically static), 
unless you call `GlobalInjeKtionScope.clear()`

// TODO add more documentation on how scopes work

## Article

https://medium.com/@codetwister/about-di-frameworks-b617281421e