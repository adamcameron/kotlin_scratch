package language.functions

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

    describe("function literals with receivers") {
        it("is a baseline test using a simple function expression") {
            val add = {op1:Int, op2:Int -> op1 + op2}

            add(17, 24) shouldBe 41
        }

        it("uses function literals with receivers") {
            val add: Int.(Int) -> Int = { other -> this.plus(other) }

            add(17, 24) shouldBe 41
            17.add(24) shouldBe 41
        }

        it("uses function literals with receivers and implicit this") {
            val add: Int.(Int) -> Int = { other -> plus(other) }

            add(17, 24) shouldBe 41
            17.add(24) shouldBe 41
        }

        it("checks the values of the operands in the function implementation") {
            var op1 = 0
            var op2 = 0
            val add: Int.(Int) -> Int = {
                other ->
                op1 = this
                op2 = other
                this.plus(other)
            }

            17.add(24) shouldBe 41

            op1 shouldBe 17
            op2 shouldBe 24
        }

        it("clarifies the syntax a bit by using different types") {
            val repeat: String.(Int) -> String = { other -> repeat(other) }

            "Z".repeat(11) shouldBe "ZZZZZZZZZZZ"
        }
    }
})
