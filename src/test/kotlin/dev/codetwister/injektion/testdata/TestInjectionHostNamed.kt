package dev.codetwister.injektion.testdata

import dev.codetwister.injektion.inject

class TestInjectionHostNamed {
    val injected1 by inject<TestInjected>(named = "test1")
    val injected2 by inject<TestInjected>(named = "test2")
}