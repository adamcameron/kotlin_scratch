package kotest.system.kotest

import io.kotest.assertions.throwables.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldBeTypeOf
import kotlin.AssertionError

class MatcherTest : DescribeSpec({

    describe("tests of shouldThrow~ variants") {

        class MySpecificException(message: String) : Exception(message)
        class MyDifferentException(message: String) : Exception(message)

        it("expects an exception") {
            shouldThrowAny {
                throw Exception("any old exception")
            }
        }

        it("expects a specific exception") {
            shouldThrow<MySpecificException> {
                throw MySpecificException("My specific exception")
            }
        }

        it("enforces a specific exception") {
            shouldThrow<AssertionError> {
                shouldThrow<MySpecificException> {
                    throw Exception("My specific exception")
                }
            }
        }

        it("expects an exception to not be thrown") {
            shouldNotThrowAny {
                // NOP
            }
        }

        it("expects a specific exception to not be thrown") {
            shouldNotThrow<MySpecificException> {
                // NOP
            }
        }

        it("expects a specific exception to not be thrown, but bubbles up any different exception") {
            shouldThrow<MyDifferentException> {
                shouldNotThrow<MySpecificException> {
                    throw MyDifferentException("My different exception")
                }
            }
        }

        it("deals with test failures when using the special handling of shouldNotThrow<Any> above") {
            shouldThrow<AssertionError> {
                shouldNotThrow<MySpecificException> {
                    throw MySpecificException("This specific exception is NOT expected, so should cause an AssertionError (which the test expects, so still passes")
                }
            }
        }

        it("should throw an exception with a specific message") {
            class InquisitionException(message: String?) : Exception(message)

            shouldThrowMessage("No-one expects the... InquisitionException") {
                throw InquisitionException("No-one expects the... InquisitionException")
            }
        }
    }

    describe("Tests of type-checking") {
        it("checks exact type with shouldBeTypeOf") {
            val i = 24

            i.shouldBeTypeOf<Int>()
        }

        it("checks subtype with shouldBeInstanceOf") {
            val i = 15

            shouldThrow<AssertionError> {
                i.shouldBeTypeOf<Number>()
            }
            i.shouldBeInstanceOf<Number>()
        }
    }
})
