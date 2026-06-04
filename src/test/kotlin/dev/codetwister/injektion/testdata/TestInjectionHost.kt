package dev.codetwister.injektion.testdata

import dev.codetwister.injektion.inject

class TestInjectionHost {
    val injected by inject<TestInjected>()
}