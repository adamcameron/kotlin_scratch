package junit.language.classes

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import WordCollections.*
import io.kotest.matchers.comparables.shouldBeGreaterThan

@DisplayName("Tests of the enums")
internal class EnumTest {

    @Test
    fun `enum elements have a name`() {
        MaoriNumbersEnum.TAHI.name
        MaoriNumbersEnum.TAHI.name shouldBe "TAHI"
    }

    @Test
    fun `enum elements have an ordinal`() {
        MaoriDaysEnum.RĀAPA.ordinal shouldBe 2
    }

    @Test
    fun `subsequent element are greater than previous elements`() {
        MaoriDaysEnum.RĀAPA shouldBeGreaterThan MaoriDaysEnum.RĀTŪ // Weds > Tues
    }

    @Test
    fun `enum elements can have a value`() {
        MaoriColoursByRgbEnum.KĀKĀRIKI.rgb shouldBe 0x00FF00
    }
}