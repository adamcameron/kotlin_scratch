package junit.system

import com.github.stefanbirkner.systemlambda.SystemLambda
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class SystemTest {

    @Nested
    @DisplayName("Tests of JUnit installation")
    inner class JUnitInstallationTest {
        @Test
        fun `the length of the string should be 5`() {
            assertEquals(5, "hello".length)
        }

        @Test
        fun `the string should start with "wor"`() {
            assertTrue("world".startsWith("wor"))
        }
    }

    @Nested
    @DisplayName("Tests of system-lambda")
    inner class SystemLambdaTest {
        @Test
        fun `system-lambda should be able to capture stdout`() {
            val testString = "string to capture"
            val output = SystemLambda.tapSystemOut {
                print(testString)
            }
            assertEquals(testString, output)
        }
    }
}
