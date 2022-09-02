package koans

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class FunctionSyntaxTest : DescribeSpec({
    describe("Checking syntax variations") {
        it("returns a single statement's value") {
            fun test():String = "EXPECTED_VALUE"

            test() shouldBe "EXPECTED_VALUE"
        }

        it("supports named parameters, and default values") {
            fun collection2JsonString(options: Collection<String>) = options.joinToString(prefix= "[", postfix = "]")

            collection2JsonString(listOf("a", "b", "c")) shouldBe "[a, b, c]"
        }
    }
})
