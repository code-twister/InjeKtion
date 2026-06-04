package dev.codetwister.injektion

import org.junit.jupiter.api.Test

class TestInjeKtionScoped {

    @Test
    fun test() {
        // top level
        single {

        }
        factory {

        }
        val injectionScope1 = createInjeKtionScope("scopeName1") {
            factory {

            }
            single {

            }
        }
        val injectionScope2 = injectionScope1.createInjeKtionScope("scopeName2") {
            factory {

            }
            single {

            }
        }
    }
}