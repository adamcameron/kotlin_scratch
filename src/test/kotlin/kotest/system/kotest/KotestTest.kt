package kotest.system.kotest

import io.kotest.assertions.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.kotest.matchers.types.shouldBeTypeOf

class KotestTest : DescribeSpec({
    describe("Misc Kotest tests") {
        it("emits a failure message") {
            try {
                fail("this is a failure message")
            } catch (e :AssertionError) {
                e.message shouldBe "this is a failure message"
            }
        }

        it("can fail with a clue") {
            try {
                withClue("this is the clue") {
                    fail("this is a failure message")
                }
            } catch (e :AssertionError) {
                e.message shouldBe """
                    this is the clue
                    this is a failure message
                """.trimIndent()
            }
        }

        it("can attach asClue to an object to use it for multiple expectations") {
            val colours = listOf("whero","karaka","kōwhai","kākāriki","kikorangi","poropango","papura")

            try {
                colours.asClue {
                    withClue("should not contain green") {
                        it.none{it == "kākāriki"}.shouldBeTrue()
                    }
                }
            } catch (e :AssertionError) {
                e.message shouldBe """
                    [whero, karaka, kōwhai, kākāriki, kikorangi, poropango, papura]
                    should not contain green
                    expected:<true> but was:<false>
                """.trimIndent()
            }
        }
    }

    describe("Soft assertions") {
        it("can use soft assertions to let multiple assertions fail and report back on all of them") {
            val actual :Int = 15

            try {
                assertSoftly {
                    actual.shouldBeTypeOf<Number>()
                    withClue("should be 15.0") {
                        actual.equals(15.0).shouldBeTrue()
                    }
                    actual shouldBe 15
                    actual shouldBe 16
                }
            } catch (e :MultiAssertionError) {
                assertSoftly(e.message) {
                    shouldContain("1) 15 should be of type kotlin.Number")
                    shouldContain("2) should be 15.0")
                    shouldContain("expected:<true> but was:<false>")
                    shouldContain("3) expected:<16> but was:<15>")
                }
            }
        }
    }
})
