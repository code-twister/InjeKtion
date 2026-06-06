package dev.codetwister.injektion.testdata

import dev.codetwister.injektion.inject
import dev.codetwister.injektion.scoped

class TestInjectionHostScoped {
    val injected1 by scoped("TestScope").inject<TestInjected>(named = "test1")
    val injected2 by inject<TestInjected>(named = "test2")
}