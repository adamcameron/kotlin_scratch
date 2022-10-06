package junit.system.junit

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

class DataDrivenTest {

    private val numbers = mapOf(
        Pair("one", "tahi"),
        Pair("two", "rua"),
        Pair("three", "toru"),
        Pair("four", "wha")
    )

    @TestFactory
    fun `Data-driven tests`() = listOf(
        "one" to "tahi",
        "two" to "rua",
        "three" to "toru",
        "four" to "wha"
    ).map { (input, expected) ->
        DynamicTest.dynamicTest("numbers[$input] should be $expected") {
            numbers[input] shouldBe expected
        }
    }
}
