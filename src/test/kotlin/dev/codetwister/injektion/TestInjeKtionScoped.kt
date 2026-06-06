package dev.codetwister.injektion

import dev.codetwister.injektion.testdata.TestInjected
import org.junit.jupiter.api.Test

class TestInjeKtionScoped {

    @Test
    fun test() {
        // top level
        factory("n1") { TestInjected(param = "1") }
        factory("n2") { TestInjected(param = "2") }
        factory("n3") { TestInjected(param = "3") }
        factory("n4") { TestInjected(param = "4") }
        val sessionScope = createInjeKtionScope("UserSessionScope") {
            factory("n1") { TestInjected(param = "1") }
            factory("n2") { TestInjected(param = "2") }
        }
        val viewModelScope1 = scoped("UserSessionScope").createInjeKtionScope("viewModel1") {
            factory("n1") { TestInjected(param = "1") }
            factory("n2") { TestInjected(param = "2") }
            factory("n3") { TestInjected(param = "3") }
        }
        val viewModelScope2 = scoped("UserSessionScope").createInjeKtionScope("viewModel2") {
            factory("n1") { TestInjected(param = "1") }
        }

        GlobalInjeKtionScope.printRecursive()
    }
}