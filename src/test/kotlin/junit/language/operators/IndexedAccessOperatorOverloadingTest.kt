package junit.language.operators

import WordCollections.MaoriNumbersEnum as MI
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests of indexed access operator")
internal class IndexedAccessOperatorOverloadingTest {

    data class MaoriNumbers(val numbers: List<MI>) {
        operator fun get(index: Int): MI {
            return numbers[index]
        }
    }

    @Test
    fun `it should return the correct value for the given index`() {
        val numbers = MaoriNumbers(listOf(MI.TAHI,MI.RUA,MI.TORU,MI.WHA))

        numbers[0] shouldBe MI.TAHI
        numbers[1] shouldBe MI.RUA
        numbers[2] shouldBe MI.TORU
        numbers[3] shouldBe MI.WHA
    }
}