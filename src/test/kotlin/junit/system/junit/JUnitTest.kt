package junit.system.junit

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.fail

@DisplayName("Misc JUnit tests that compare with equivalent Kotest functionality")
class JUnitTest {

    @Nested
    @DisplayName("Misc JUnit functionality")
    inner class MiscJUnitFunctionality {
        @Test
        fun `It has a fail method and its exception can be caught and asserted against`() {
            val exception = assertThrows<AssertionError> {
                fail("This is a failure")
            }
            assertEquals("This is a failure", exception.message)
        }

        @Test
        fun `A message can be supplied with the assertion to make failures clearer`() {
            val exception = assertThrows<AssertionError> {
                assertEquals(1, 2, "the two numbers should be equal")
            }
            assertEquals("the two numbers should be equal ==> expected: <1> but was: <2>", exception.message)
        }

        @TestFactory
        fun testPrimeFactorsOf210() = listOf(2, 3, 5, 7).map {
            DynamicTest.dynamicTest("Dividing by $it") {
                assertEquals(0, 210 % it)
            }
        }
    }

    @Nested
    @DisplayName("Groups of assertions with assertAll")
    inner class GroupsOfAssertionsWithAssertAll {
        @Test
        fun `It can use soft assertions to let multiple assertions fail and report back on all of them`() {
            val actual: Int = 15
            val exception = assertThrows<AssertionError> {
                assertAll(
                    { assertEquals(Number::class, actual::class) },
                    { assertEquals(15.0, actual) },
                    { assertEquals(15, actual) }, // this one is OK
                    { assertEquals(16, actual) }
                )
            }
            assertTrue(exception.message!!.contains("expected: <class kotlin.Number> but was: <class kotlin.Int>"))
            assertTrue(exception.message!!.contains("expected: <15.0> but was: <15>"))
            assertTrue(exception.message!!.contains("expected: <16> but was: <15>"))
        }
    }
}
