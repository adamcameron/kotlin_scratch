package kotest.system.kotest

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class DataDrivenTest : DescribeSpec({
    describe("Data-driven tests") {
        val numbers = mapOf(
            Pair("one", "tahi"),
            Pair("two", "rua"),
            Pair("three", "toru"),
            Pair("four", "wha")
        )

        data class TestCase(val input: String, val expected: String)
        withData(
            TestCase("one", "tahi"),
            TestCase("two", "rua"),
            TestCase("three", "toru"),
            TestCase("four", "wha")
        ) { (input, expected) -> numbers[input] shouldBe expected }
    }
})
