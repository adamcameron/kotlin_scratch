package junit.language.types

import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.kotest.matchers.types.shouldNotBeInstanceOf
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate
import WordCollections.MaoriNumbersEnum as MI

internal class `Tests of ranges` {

    @Nested
    inner class `Tests of ranges on Ints` {

        @Test
        fun `it should return true for a value in a range`() {
            val range = 1..10
            val value = 5

            (value in range) shouldBe true
        }

        @Test
        fun `it should return false for a value not in a range`() {
            val range = 1..10
            val value = 11

            (value in range) shouldBe false
        }

        @Test
        fun `it should return true for values at the boundaries of the range`() {
            val range = 1..10

            (1 in range) shouldBe true
            (10 in range) shouldBe true
        }

        @Test
        fun `a range of integers should be an IntRange`() {
            val range = 1..10

            range.shouldBeInstanceOf<IntRange>()
        }

        @Test
        fun `it can be iterated over`() {
            val range = 1..10
            var list = mutableListOf<Int>()

            list = range.fold(list) { acc, i -> acc.apply { add(i) } }

            list shouldBe listOf(1,2,3,4,5,6,7,8,9,10)
        }

        @Nested
        inner class `Tests of ranges with steps` {

            @Test
            fun `it should return true for values at the steps`() {
                val range = 1..10 step 2

                listOf(1,3,5,7,9).forEach {
                    (it in range) shouldBe true
                }
            }

            @Test
            fun `the upper bound might not be included`() {
                val range = 1 until 10 step 2

                (10 in range) shouldBe false
            }

            @Test
            fun `it should return false for values not at the steps`() {
                val range = 1..10 step 2

                listOf(2,4,6,8,10).forEach {
                    (it in range) shouldBe false
                }
            }

            @Test
            fun `a stepped range is an IntProgression`() {
                val range = 1..10 step 2

                range.shouldNotBeInstanceOf<IntRange>()
                range.shouldBeInstanceOf<IntProgression>()
            }

            @Test
            fun `a step can be downwards`() {
                val range = 10 downTo 1 step 2

                listOf(10,8,6,4,2).forEach {
                    (it in range) shouldBe true
                }
                (1 in range) shouldBe false
            }

            @Test
            fun `it has some properties`() {
                val range = 1..10 step 2

                range.first shouldBe 1
                range.last shouldBe 9
                range.step shouldBe 2
            }

            @Test
            fun `it can be reversed`() {
                val range = 1..10 step 2
                val reversed = range.reversed()
                (reversed == (9 downTo 1 step 2)) shouldBe true
            }
        }
    }

    @Nested
    inner class `Tests of ranges on Doubles` {
        @Test
        fun `it does not implement iterator like a range of ints does`() {
            val intRange = 1..10
            intRange.shouldBeInstanceOf<Iterable<*>>()

            val doubleRange = 1.0..10.0
            doubleRange.shouldNotBeInstanceOf<Iterable<*>>()
        }

        @Test
        fun `its elements can be compared to other values`() {
            val range = 1.0..10.0
            val value = 5.5

            (value in range) shouldBe true
        }
    }

    @Nested
    inner class `Tests of ranges of Chars` {
        @Test
        fun `a range of chars can have a step`() {
            val range = 'z' downTo 'a' step 2

            listOf('z','x','v','t','r','p','n','l','j','h','f','d','b').forEach {
                (it in range) shouldBe true
            }
        }
    }

    @Nested
    inner class `Tests of ranges of Strings` {
        @Test
        fun `a range of strings can have a step`() {
            val range = "a".."f"

            listOf("a","b","c","d","e","f").forEach {
                (it in range) shouldBe true
            }
        }

        @Test
        fun `the strings in the range can be multi-character`() {
            val range = "aa".."cc"

            listOf("aa","ab","ac","ba","bb","bc","ca","cb","cc").forEach {
                (it in range) shouldBe true
            }
        }
    }

    @Nested
    inner class `Tests of ranges of enums` {
        @Test
        fun `a range of MaoriNumbersEnum should not allow other elements from the enum`() {
            val range = MI.TAHI..MI.WHA // tahi, rua, toru, wha

            (MI.RUA in range) shouldBe true
            (MI.RIMA in range) shouldBe false // rima is 5
        }

        @Test
        fun `a range of enums is not iterable` () {
            val range = MI.TAHI..MI.WHA

            range.shouldNotBeInstanceOf<Iterable<*>>()
        }
    }

    @Nested
    inner class `Tests of ranges of objects` {

        @Test
        fun `a range of objects implementing Comparable can be used as a range`() {
            val testDates = MyDate(1955, 3, 25) .. MyDate(1955, 3, 28)

            testDates.start shouldBe MyDate(1955, 3, 25)
            testDates.endInclusive shouldBe MyDate(1955, 3, 28)

            val restDay = MyDate(1955, 3, 27)
            (restDay in testDates) shouldBe true
        }

        @Test
        fun `an iterable range can be made by implementing Comparable and Iterable`() {
            val testDates = MyDateRange(MyDate(1955, 3, 25), MyDate(1955, 3, 28))

            (MyDate(1955, 3, 27) in testDates) shouldBe true
            (MyDate(1955, 3, 29) in testDates) shouldBe false

            var list = mutableListOf<MyDate>()

            list = testDates.fold(list) { acc, date -> acc.apply { add(date) } }

            list shouldBe listOf(
                MyDate(1955, 3, 25),
                MyDate(1955, 3, 26),
                MyDate(1955, 3, 27),
                MyDate(1955, 3, 28)
            )
        }
    }
}

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    private val date = LocalDate.of(year, month, dayOfMonth)

    override fun compareTo(other: MyDate): Int {
        return when {
            year != other.year -> year.compareTo(other.year)
            month != other.month -> month.compareTo(other.month)
            else -> dayOfMonth.compareTo(other.dayOfMonth)
        }
    }

    fun nextDay(): MyDate {
        val nextDate = date.plusDays(1)
        return MyDate(nextDate.year, nextDate.monthValue, nextDate.dayOfMonth)
    }
}

class MyDateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current = start

            override fun hasNext(): Boolean {
                return current <= endInclusive
            }

            override fun next(): MyDate {
                val result = current
                current = current.nextDay()
                return result
            }
        }
    }
}