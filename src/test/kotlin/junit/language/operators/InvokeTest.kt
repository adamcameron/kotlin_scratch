package junit.language.operators

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

internal class `Testing overloading of invoke operator` {

    @Test
    fun `it should execute its invoke method when called as a function`() {
        class Logger {
            private var _log = mutableListOf<String>()
            val log: List<String>
                get() = _log

            operator fun invoke(message: String) {
                _log += message
            }
        }

        val logMessage = Logger()
        logMessage("This is a log message")
        logMessage("This is another log message")

        logMessage.log shouldBe listOf("This is a log message", "This is another log message")
    }

    @Test
    fun `it should work with a variadic invoke handler` () {
        class Task(var lambda : (args: Array<String>) -> List<String>) {
            operator fun invoke(vararg args: String) : List<String> {
                return lambda(arrayOf(*args))
            }
        }

        val task = Task { args -> args.toList() }

        val taskResult = task("This", "is", "a", "variadic", "invoke", "handler")
        taskResult shouldBe listOf("This", "is", "a", "variadic", "invoke", "handler")
    }
}