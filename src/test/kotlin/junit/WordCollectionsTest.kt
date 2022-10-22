package junit

import WordCollections.*
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests of the WordCollections class")
internal class WordCollectionsTest {

    @Test
    fun `It should return a list of Maori colours`() {
        val colours = WordCollections.listOfMaoriColours
        colours.size shouldBe  7
        colours[0] shouldBe "whero"
        colours[1] shouldBe "karaka"
        colours[2] shouldBe "kōwhai"
        colours[3] shouldBe "kākāriki"
        colours[4] shouldBe "kikorangi"
        colours[5] shouldBe "tūāuri"
        colours[6] shouldBe "papura"
    }

    @Test
    fun `It should return a list of Maori days`() {
        val days = WordCollections.listOfMaoriDays
        days.size shouldBe  7
        days[0] shouldBe "rāhina"
        days[1] shouldBe "rātū"
        days[2] shouldBe "rāapa"
        days[3] shouldBe "rāpare"
        days[4] shouldBe "rāmere"
        days[5] shouldBe "rāhoroi"
        days[6] shouldBe "rātapu"
    }

    @Test
    fun `It should return a list of Maori numbers`() {
        val numbers = WordCollections.listOfMaoriNumbers
        numbers.size shouldBe  10
        numbers[0] shouldBe "tahi"
        numbers[1] shouldBe "rua"
        numbers[2] shouldBe "toru"
        numbers[3] shouldBe "wha"
        numbers[4] shouldBe "rima"
        numbers[5] shouldBe "ono"
        numbers[6] shouldBe "whitu"
        numbers[7] shouldBe "waru"
        numbers[8] shouldBe "iwa"
        numbers[9] shouldBe "tekau"
    }

    @Test
    fun `It should return a map of English and Maori numbers`() {
        val numbers = WordCollections.mapOfEnglishAndMaoriNumbers
        numbers.size shouldBe  10
        numbers["one"] shouldBe "tahi"
        numbers["two"] shouldBe "rua"
        numbers["three"] shouldBe "toru"
        numbers["four"] shouldBe "wha"
        numbers["five"] shouldBe "rima"
        numbers["six"] shouldBe "ono"
        numbers["seven"] shouldBe "whitu"
        numbers["eight"] shouldBe "waru"
        numbers["nine"] shouldBe "iwa"
        numbers["ten"] shouldBe "tekau"
    }

    @Test
    fun `It should return a map of Maori and English numbers`() {
        val numbers = WordCollections.mapOfMaoriAndEnglishNumbers
        numbers.size shouldBe  10
        numbers["tahi"] shouldBe "one"
        numbers["rua"] shouldBe "two"
        numbers["toru"] shouldBe "three"
        numbers["wha"] shouldBe "four"
        numbers["rima"] shouldBe "five"
        numbers["ono"] shouldBe "six"
        numbers["whitu"] shouldBe "seven"
        numbers["waru"] shouldBe "eight"
        numbers["iwa"] shouldBe "nine"
        numbers["tekau"] shouldBe "ten"
    }

    @Test
    fun `It should return a map of English and Maori colours`() {
        val colours = WordCollections.mapOfEnglishAndMaoriColours
        colours.size shouldBe  7
        colours["red"] shouldBe "whero"
        colours["orange"] shouldBe "karaka"
        colours["yellow"] shouldBe "kōwhai"
        colours["green"] shouldBe "kākāriki"
        colours["blue"] shouldBe "kikorangi"
        colours["indigo"] shouldBe "tūāuri"
        colours["violet"] shouldBe "papura"
    }

    @Test
    fun `It should return a map of Maori and English colours`() {
        val colours = WordCollections.mapOfMaoriAndEnglishColours
        colours.size shouldBe  7
        colours["whero"] shouldBe "red"
        colours["karaka"] shouldBe "orange"
        colours["kōwhai"] shouldBe "yellow"
        colours["kākāriki"] shouldBe "green"
        colours["kikorangi"] shouldBe "blue"
        colours["tūāuri"] shouldBe "indigo"
        colours["papura"] shouldBe "violet"
    }

    @Test
    fun `It should return a map of English and Maori days`() {
        val days = WordCollections.mapOfEnglishAndMaoriDays
        days.size shouldBe  7
        days["Monday"] shouldBe "rāhina"
        days["Tuesday"] shouldBe "rātū"
        days["Wednesday"] shouldBe "rāapa"
        days["Thursday"] shouldBe "rāpare"
        days["Friday"] shouldBe "rāmere"
        days["Saturday"] shouldBe "rāhoroi"
        days["Sunday"] shouldBe "rātapu"
    }

    @Test
    fun `It should return a map of Maori and English days`() {
        val days = WordCollections.mapOfMaoriAndEnglishDays
        days.size shouldBe  7
        days["rāhina"] shouldBe "Monday"
        days["rātū"] shouldBe "Tuesday"
        days["rāapa"] shouldBe "Wednesday"
        days["rāpare"] shouldBe "Thursday"
        days["rāmere"] shouldBe "Friday"
        days["rāhoroi"] shouldBe "Saturday"
        days["rātapu"] shouldBe "Sunday"
    }

    @Test
    fun `It should expose an enum of Maori numbers`() {
        val numbers = MaoriNumbersEnum.values()
        numbers.size shouldBe  10
        numbers[0] shouldBe MaoriNumbersEnum.TAHI
        numbers[1] shouldBe MaoriNumbersEnum.RUA
        numbers[2] shouldBe MaoriNumbersEnum.TORU
        numbers[3] shouldBe MaoriNumbersEnum.WHA
        numbers[4] shouldBe MaoriNumbersEnum.RIMA
        numbers[5] shouldBe MaoriNumbersEnum.ONO
        numbers[6] shouldBe MaoriNumbersEnum.WHITU
        numbers[7] shouldBe MaoriNumbersEnum.WARU
        numbers[8] shouldBe MaoriNumbersEnum.IWA
        numbers[9] shouldBe MaoriNumbersEnum.TEKAU
    }

    @Test
    fun `It should expose an enum of Maori colours`() {
        val colours = MaoriColoursEnum.values()
        colours.size shouldBe  7
        colours[0] shouldBe MaoriColoursEnum.WHERO
        colours[1] shouldBe MaoriColoursEnum.KARAKA
        colours[2] shouldBe MaoriColoursEnum.KŌWHAI
        colours[3] shouldBe MaoriColoursEnum.KĀKĀRIKI
        colours[4] shouldBe MaoriColoursEnum.KIKORANGI
        colours[5] shouldBe MaoriColoursEnum.TŪĀURI
        colours[6] shouldBe MaoriColoursEnum.PAPURA
    }

    @Test
    fun `It should expose an enum of Maori days`() {
        val days = MaoriDaysEnum.values()
        days.size shouldBe  7
        days[0] shouldBe MaoriDaysEnum.RĀHINA
        days[1] shouldBe MaoriDaysEnum.RĀTŪ
        days[2] shouldBe MaoriDaysEnum.RĀAPA
        days[3] shouldBe MaoriDaysEnum.RĀPARE
        days[4] shouldBe MaoriDaysEnum.RĀMERE
        days[5] shouldBe MaoriDaysEnum.RĀHOROI
        days[6] shouldBe MaoriDaysEnum.RĀTAPU
    }

    @Test
    fun `It should expose an enum of Maori colours by RGB`() {
        MaoriColoursByRgbEnum.WHERO.rgb shouldBe 0xFF0000
        MaoriColoursByRgbEnum.KARAKA.rgb shouldBe 0xFFA500
        MaoriColoursByRgbEnum.KŌWHAI.rgb shouldBe 0xFFFF00
        MaoriColoursByRgbEnum.KĀKĀRIKI.rgb shouldBe 0x00FF00
        MaoriColoursByRgbEnum.KIKORANGI.rgb shouldBe 0x0000FF
        MaoriColoursByRgbEnum.TŪĀURI.rgb shouldBe 0x4B0082
        MaoriColoursByRgbEnum.PAPURA.rgb shouldBe 0x800080
    }
}