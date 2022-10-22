package junit.language.functions

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests of extension functions")
class ExtensionFunctionsTest {

    @Test
    fun `an extension function can be called on an instance of a class`() {
        class TranslationPair(val en: String, val mi: String)

        fun TranslationPair.getBoth(): String {
            return "$en $mi"
        }

        val green = TranslationPair("green", "kākāriki")

        val result = green.getBoth()
        result shouldBe "green kākāriki"
    }

    @Test
    fun `an extension function can be added to an inbuilt Kotlin class`() {
        fun String.toMaori(): String {
            return when (this) {
                "one" -> "tahi"
                "two" -> "rua"
                "three" -> "toru"
                "four" -> "wha"
                else -> throw IllegalArgumentException("Unknown number $this")
            }
        }

        val result = "four".toMaori()
        result shouldBe "wha"
    }
}