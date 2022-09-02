package language.variables

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class VariablesTest : DescribeSpec({

    describe("Checking how scoping of val works") {
        val testVal = "set in describe"

        it("can be accessed in a callback from an outer scope") {
            testVal shouldBe "set in describe"
        }

        it("can be overridden in a callback") {
            val testVal = "set in it"

            testVal shouldBe "set in it"
        }
    }
})
