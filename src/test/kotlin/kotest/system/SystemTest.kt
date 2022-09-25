package kotest.system

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import com.github.stefanbirkner.systemlambda.SystemLambda.*

class SystemTest : DescribeSpec({
    describe("Tests of kotest installation") {
        it("should return the size of a string") {
            "hello".length shouldBe 5
        }
        it("should test for the prefix of a string") {
            "world".shouldStartWith("wor")
        }
    }
    describe("tests of system-lambda") {
        it("checks system-lambda is working OK") {
            val testString = "string to capture"
            val output = tapSystemOut {
                print(testString)
            }
            output shouldBe testString
        }
    }
})
