package dev.codetwister.injektion

import dev.codetwister.injektion.testdata.TestInjected
import dev.codetwister.injektion.testdata.TestInjectionHost
import dev.codetwister.injektion.testdata.TestInjectionHostNamed
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class TestInjeKtion {

    @AfterEach
    fun tearDown() {
        GlobalInjeKtionScope.clear()
    }

    @Test
    fun `check factories are returning a new instance every time`() {
        var index = 0
        factory<TestInjected> {
            TestInjected("param${++index}")
        }
        val injectionHost1 = TestInjectionHost()
        val injectionHost2 = TestInjectionHost()
        injectionHost1.injected.param shouldBeEqualTo "param1"
        injectionHost2.injected.param shouldBeEqualTo "param2"
    }

    @Test
    fun `check single is returning the same instance every time`() {
        var index = 0
        single<TestInjected> {
            TestInjected("param${++index}")
        }
        val injectionHost1 = TestInjectionHost()
        val injectionHost2 = TestInjectionHost()
        injectionHost1.injected shouldBe injectionHost2.injected
    }

    @Test
    fun `check named factories are returning appropriate instances`() {
        factory(named = "test1") { TestInjected("injected1") }
        factory(named = "test2") { TestInjected("injected2") }
        val injectionHost = TestInjectionHostNamed()
        injectionHost.injected1.param shouldBeEqualTo "injected1"
        injectionHost.injected2.param shouldBeEqualTo "injected2"
    }
}