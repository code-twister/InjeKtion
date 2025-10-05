# injeKtion

Minimalistic Dependency Injection Library for Kotlin

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
   private val dependency_one by inject<MyInterface>(named = "specialName")
   private val dependency_two: OtherInterface by inject()
   private val anotherClass by inject<AnotherClass>()
   private val test: String by inject(named = "specialString")
   // ...
}
```

## Article

https://medium.com/@codetwister/about-di-frameworks-b617281421e