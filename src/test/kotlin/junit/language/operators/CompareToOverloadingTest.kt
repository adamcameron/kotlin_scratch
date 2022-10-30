package junit.language.operators

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Tests of overloading the compareTo operator")
internal class CompareToOverloadingTest {

    @Nested
    @DisplayName("Tests of overloading the compareTo operator on MyDate objects")
    inner class MyDateCompareToOverloadingTest {
        @Test
        fun `it should returns 0 when the two MyDate objects are equal`() {
            val date1 = MyDate(2011, 3, 24)
            val date2 = MyDate(2011, 3, 24)

            date1.compareTo(date2) shouldBe 0
        }

        @Test
        fun `it should returns -1 when the first MyDate object is less than the second MyDate object`() {
            val date1 = MyDate(2011, 3, 24)
            val date2 = MyDate(2016, 8, 17)

            date1.compareTo(date2) shouldBe -1
        }

        @Test
        fun `it should returns 1 when the first MyDate object is greater than the second MyDate object`() {
            val date1 = MyDate(2016, 8, 17)
            val date2 = MyDate(2011, 3, 24)

            date1.compareTo(date2) shouldBe 1
        }

        @Test
        fun `it should work with the less-than operator`() {
            val date1 = MyDate(2011, 3, 24)
            val date2 = MyDate(2016, 8, 17)

            (date1 < date2) shouldBe true
        }
    }

    data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
        override fun compareTo(other: MyDate): Int {
            return when {
                year != other.year -> year.compareTo(other.year)
                month != other.month -> month.compareTo(other.month)
                else -> dayOfMonth.compareTo(other.dayOfMonth)
            }
        }
    }
}


