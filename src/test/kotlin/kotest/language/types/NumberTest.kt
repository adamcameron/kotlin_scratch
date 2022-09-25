package kotest.language.types

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import java.lang.AssertionError

class NumberTest : DescribeSpec({
    describe("General Number tests") {
        describe("Literals and equality") {
            it("demonstrates ints and longs of same 'human value' are not equal") {
                val i :Int = 24
                val l :Long = 24L

                shouldThrow<AssertionError> {
                    i.equals(l).shouldBeTrue()
                }
            }

            it("demonstrates Kotest will handle the casting between ints and longs internally") {
                val i :Int = 17
                val l :Long = 17L

                i shouldBe l
            }

            it("demonstrates Kotest will handle the casting between ints and floats internally") {
                val i :Int = 17
                val f :Float = 17.0F

                i shouldBe f
            }

            it("demonstrates Kotest will handle the casting between ints and doubles internally") {
                val i :Int = 17
                val d :Double = 17.0

                i shouldBe d
            }

            it("demonstrates one can convert an int to a double") {
                val i :Int = 17
                val d :Double = 17.0

                i.toDouble().equals(d).shouldBeTrue()
            }

            it("demonstrates literals can have _ as a separator") {
                val i :Int = 123_456_789

                i shouldBe 123456789
            }

            it("demonstrates _ is not just a 1000s separator") {
                val d :Double = 3_6_5.2_4_2_5

                d shouldBe 365.2425
            }

            it("has binary and hex literals") {
                val b = 0b1111_1111_1111_1111
                var h = 0xff_ff

                b shouldBe h
            }

            it("demonstrates binary and hex numbers of same 'human value' ARE equal") {
                val b = 0b1111_1111_1111_1111
                val h = 0xff_ff

                b.equals(h).shouldBeTrue()
                b.shouldBeTypeOf<Int>()
                h.shouldBeTypeOf<Int>()
            }
        }
    }
})
