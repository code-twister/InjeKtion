package dev.codetwister.injektion

import dev.codetwister.injektion.testdata.TestInjected
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test

class TestInjeKtionScoped {

    @AfterEach
    fun tearDown() {
        GlobalInjeKtionScope.clear()
    }

    @Test
    fun `check scope creation and retrieval`() {
        val createdScope = createInjeKtionScope("MyScope") { }
        val retrievedScope = scoped("MyScope")
        retrievedScope shouldBe createdScope
    }

    @Test
    fun `check nested scope creation and retrieval`() {
        val parentScope = createInjeKtionScope("Parent") {
            createInjeKtionScope("Child") { }
        }
        val retrievedChild = scoped("Child")
        val directChild = parentScope.childScopes["Child"]
        retrievedChild shouldBe directChild
    }

    @Test
    fun `check local factory resolution within a scope`() {
        val myScope = createInjeKtionScope("MyScope") {
            factory { TestInjected("local") }
        }

        class Host {
            val injected by myScope.inject<TestInjected>()
        }

        val host = Host()
        host.injected.param shouldBeEqualTo "local"
    }

    @Test
    fun `check recursive factory resolution from parent scopes`() {
        factory { TestInjected("global") }
        createInjeKtionScope("Parent") {
            factory(named = "parent") { TestInjected("parent-val") }
            createInjeKtionScope("Child") { }
        }

        val childScope = scoped("Child")

        class Host {
            val global by childScope.inject<TestInjected>()
            val parent by childScope.inject<TestInjected>(named = "parent")
        }

        val host = Host()
        host.global.param shouldBeEqualTo "global"
        host.parent.param shouldBeEqualTo "parent-val"
    }

    @Test
    fun `check scope isolation`() {
        createInjeKtionScope("ScopeA") {
            factory { TestInjected("A") }
        }
        val scopeB = createInjeKtionScope("ScopeB") { }

        class Host {
            val injected by scopeB.inject<TestInjected>()
        }

        val host = Host()
        val action = { host.injected }
        action shouldThrow IllegalStateException::class
    }

    @Test
    fun `check overriding parent factories in child scopes`() {
        factory { TestInjected("global") }
        val childScope = createInjeKtionScope("Child") {
            factory { TestInjected("local") }
        }

        class ChildHost {
            val injected by childScope.inject<TestInjected>()
        }

        class GlobalHost {
            val injected by inject<TestInjected>()
        }

        ChildHost().injected.param shouldBeEqualTo "local"
        GlobalHost().injected.param shouldBeEqualTo "global"
    }

    @Test
    fun `check clearing scopes and its effect on child scopes`() {
        createInjeKtionScope("Parent") {
            createInjeKtionScope("Child") { }
        }

        scoped("Child") // Should not throw

        GlobalInjeKtionScope.clear()

        val action = { scoped("Child") }
        action shouldThrow IllegalStateException::class
    }
}
