# injeKtion

Minimalistic Dependency Injection Library for Kotlin

[CHANGELOG.md](docs/CHANGELOG.md)

## Gradle Dependency

### using classic Gradle format
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
injeKtionVersion = "1.1.0"

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

**Using scopes:**

Detailed information here: [ScopedInjection.md](docs/ScopedInjection.md)

```kotlin
val myScope = createInjeKtionScope("MyScope") {
    single { MyScopedService() }
    factory { MyScopedFactory() }
}

class SomeClass {
    private val dependencyOne by scoped("MyScope").inject<MyScopedService>()
}
// or
class OtherClass(scope: InjeKtionScope) {
    private val dependencyOne by scope.inject<MyScopedService>()
}
```

## Article

https://medium.com/@codetwister/about-di-frameworks-b617281421e