package junit.language.stdlib

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests of the TODO function")
internal class TodoTest {
    @Test
    fun `It should throw a NotImplementedError`() {
        shouldThrow<NotImplementedError> {
            TODO()
        }
    }

    @Test
    fun `It should throw a NotImplementedError with a message`() {
        shouldThrow<NotImplementedError> {
            TODO("This is a message")
        }.message shouldBe "An operation is not implemented: This is a message"
    }

}