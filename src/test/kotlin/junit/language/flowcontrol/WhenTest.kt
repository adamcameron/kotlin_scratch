package junit.language.flowcontrol

import WordCollections
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import WordCollections.*
import com.github.stefanbirkner.systemlambda.SystemLambda
import org.junit.jupiter.api.Nested

@DisplayName("Tests of the when expression")
internal class WhenTest {

    @Nested
    @DisplayName("Tests of non-exhaustive when expressions")
    inner class NonExhaustiveWhenTest {
        @Test
        fun `It should return the correct value`() {
            val en = "one"
            val mi = translateNumber(en)
            mi shouldBe "tahi"
        }

        @Test
        fun `It should throw an exception when the value is not known`() {
            val en = "five"
            shouldThrow<IllegalArgumentException> {
                translateNumber(en)
            }.message shouldBe "Unknown number five"
        }

        private fun translateNumber(en: String): String {
            val mi = when (en) {
                "one" -> "tahi"
                "two" -> "rua"
                "three" -> "toru"
                "four" -> "wha"
                else -> throw IllegalArgumentException("Unknown number $en")
            }
            return mi
        }
    }

    @Nested
    @DisplayName("Tests of exhaustive when expressions")
    inner class ExhaustiveWhenTest {
        @Test
        fun `It should return the correct value for a colour`() {
            val mi = MaoriColoursEnum.KĀKĀRIKI
            val en = translateColour(mi)
            en shouldBe "green"
        }

        private fun translateColour(mi: MaoriColoursEnum): String {
            val en = when (mi) {
                MaoriColoursEnum.WHERO -> "red"
                MaoriColoursEnum.KARAKA -> "orange"
                MaoriColoursEnum.KŌWHAI -> "yellow"
                MaoriColoursEnum.KĀKĀRIKI -> "green"
                MaoriColoursEnum.KIKORANGI -> "blue"
                MaoriColoursEnum.TŪĀURI -> "indigo"
                MaoriColoursEnum.PAPURA -> "violet"
            }
            return en
        }
    }

    @Nested
    @DisplayName("Tests of when statements")
    inner class WhenStatementTest {
        @Test
        fun `It should print the correct English day for the passed-in Maori day`() {
            val rāmere = MaoriDaysEnum.RĀMERE
            val output = SystemLambda.tapSystemOut {
                printEnglishDayFromMaori(rāmere)
            }
            output shouldBe "Friday"
        }

        private fun printEnglishDayFromMaori(mi: MaoriDaysEnum) {
            when (mi) {
                MaoriDaysEnum.RĀHINA -> print("Monday")
                MaoriDaysEnum.RĀTŪ -> print("Tuesday")
                MaoriDaysEnum.RĀAPA -> print("Wednesday")
                MaoriDaysEnum.RĀPARE -> print("Thursday")
                MaoriDaysEnum.RĀMERE -> print("Friday")
                MaoriDaysEnum.RĀHOROI -> print("Saturday")
                MaoriDaysEnum.RĀTAPU -> print("Sunday")
            }
        }
    }
}