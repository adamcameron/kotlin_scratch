package junit

import com.github.stefanbirkner.systemlambda.SystemLambda
import main
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

@DisplayName("Tests of Main class")
internal class MainTest {

    val EOL = System.lineSeparator()

    @Test
    @DisplayName("it outputs G'day World & its args")
    fun testMainOutputsCorrectly() {
        val testArgs = arrayOf("some arg", "some other arg")
        val output = SystemLambda.tapSystemOut {
            main(testArgs)
        }
        assertEquals("G'day World!${EOL}Program arguments: some arg, some other arg", output.trim())
    }

    @Test
    fun `it works OK with no args`() {
        val testArgs = arrayOf<String>()
        val output = SystemLambda.tapSystemOut {
            main(testArgs)
        }
        assertEquals("G'day World!${EOL}Program arguments:", output.trim())
    }
}
