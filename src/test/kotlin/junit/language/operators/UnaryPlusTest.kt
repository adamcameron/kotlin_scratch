package junit.language.operators

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Tests of unary plus operator")
internal class UnaryPlusTest {

    @Nested
    @DisplayName("Tests of unary plus operator on Ints")
    inner class IntUnaryPlusTest {

        @Test
        fun `it does nothing to integer values`() {
            +1 shouldBe 1
        }

        @Test
        fun `it throws a NPE for null Ints`() {
            val i: Int? = null

            shouldThrow<NullPointerException> {
                +i!!
            }
        }
    }

    @Nested
    @DisplayName("Tests of unary plus operator on Doubles")
    inner class DoubleUnaryPlusTest {

        @Test
        fun `it does nothing to double values`() {
            +1.0 shouldBe 1.0
        }

        @Test
        fun `it throws a NPE for null Doubles`() {
            val d: Double? = null

            shouldThrow<NullPointerException> {
                +d!!
            }
        }
    }

    @Nested
    @DisplayName("Tests of unary plus operator on Floats")
    inner class FloatUnaryPlusTest {

        @Test
        fun `it does nothing to float values`() {
            +1.0f shouldBe 1.0f
        }

        @Test
        fun `it throws a NPE for null Floats`() {
            val f: Float? = null

            shouldThrow<NullPointerException> {
                +f!!
            }
        }
    }

    @Nested
    @DisplayName("Tests of unary plus operator on Longs")
    inner class LongUnaryPlusTest {

        @Test
        fun `it does nothing to long values`() {
            +1L shouldBe 1L
        }

        @Test
        fun `it throws a NPE for null Longs`() {
            val l: Long? = null

            shouldThrow<NullPointerException> {
                +l!!
            }
        }
    }

    @Nested
    @DisplayName("Tests of unary plus operator on Shorts")
    inner class ShortUnaryPlusTest {

        @Test
        fun `it does nothing to short values`() {
            val s :Short = 1
            +s shouldBe 1
        }

        @Test
        fun `it throws a NPE for null Shorts`() {
            val s: Short? = null

            shouldThrow<NullPointerException> {
                +s!!
            }
        }
    }

    @Nested
    @DisplayName("Tests of unary plus operator on Bytes")
    inner class ByteUnaryPlusTest {

        @Test
        fun `it does nothing to byte values`() {
            val b :Byte = 1
            +b shouldBe 1
        }

        @Test
        fun `it throws a NPE for null Bytes`() {
            val b: Byte? = null

            shouldThrow<NullPointerException> {
                +b!!
            }
        }
    }

    @Nested
    @DisplayName("Tests of overloaded unary plus operator on Chars")
    inner class CharUnaryPlusTest {
        operator fun Char?.unaryPlus(): Int? {
            return this?.code
        }

        @Test
        fun `it returns the character code of the char as an int`() {
            +'a' shouldBe 97
        }

        @Test
        fun `it returns null if the Char is null`() {
            val c: Char? = null

            val result = +c

            result.shouldBeNull()
        }

        @Test
        fun `it handles double-byte chars`() {
            +'€' shouldBe 8364
        }

        // NB: emoji will not fit into a Char see https://stackoverflow.com/q/70152643/894061
    }

    @Nested
    @DisplayName("Tests of overloaded unary plus operator on Booleans")
    inner class BooleanUnaryPlusTest {
        operator fun Boolean?.unaryPlus(): Int {
            return if (this == true) 1 else 0
        }

        @Test
        fun `it returns 1 if the boolean is true`() {
            +true shouldBe 1
        }

        @Test
        fun `it returns 0 if the boolean is false`() {
            +false shouldBe 0
        }

        @Test
        fun `it returns zero if the boolean is null`() {
            val b: Boolean? = null

            val result = +b

            result shouldBe 0
        }
    }

    @Nested
    @DisplayName("Tests of overloaded unary plus operator on Strings")
    inner class StringUnaryPlusTest {
        operator fun String?.unaryPlus(): Number? {
            return this?.toIntOrNull() ?: this?.toDoubleOrNull()
        }

        @Test
        fun `it converts a string integer to an Int`() {
            val result = +"42"

            result.shouldBeInstanceOf<Int>()
            result shouldBe 42
        }

        @Test
        fun `it handles negative numbers`() {
            +"-42" shouldBe -42
        }

        @Test
        fun `it converts a string double to a Double`() {
            val result = +"3.14"

            result.shouldBeInstanceOf<Double>()
            result shouldBe 3.14
        }

        @Test
        fun `it handles a null string`() {
            val s: String? = null

            val result = +s

            result.shouldBeNull()
        }

        @Test
        fun `it handles a string that is not a number`() {
            val s = "not a number"

            val result : Number? = +s

            result.shouldBeNull()
        }
    }


    // everything below here suggested by Copilot

    @Test
    fun `unary plus is not implemented on Array values, but can be overloaded`() {
        operator fun Array<Int>.unaryPlus(): Int? {
            return this.sum()
        }
        +arrayOf(1, 2, 3) shouldBe 6
    }

    @Test
    fun `unary plus is not implemented on List values, but can be overloaded`() {
        operator fun List<Int>.unaryPlus(): Int? {
            return this.sum()
        }
        +listOf(1, 2, 3) shouldBe 6
    }

    @Test
    fun `unary plus is not implemented on Set values, but can be overloaded`() {
        operator fun Set<Int>.unaryPlus(): Int? {
            return this.sum()
        }
        +setOf(1, 2, 3) shouldBe 6
    }

    @Test
    fun `unary plus is not implemented on Map values, but can be overloaded`() {
        operator fun Map<Int, Int>.unaryPlus(): Int? {
            return this.values.sum()
        }
        +mapOf(1 to 1, 2 to 2, 3 to 3) shouldBe 6
    }

    @Test
    fun `unary plus is not implemented on Sequence values, but can be overloaded`() {
        operator fun Sequence<Int>.unaryPlus(): Int? {
            return this.sum()
        }
        +sequenceOf(1, 2, 3) shouldBe 6
    }

    @Test
    fun `unary plus is not implemented on IntRange values, but can be overloaded`() {
        operator fun IntRange.unaryPlus(): Int? {
            return this.sum()
        }
        +(1..3) shouldBe 6
    }

    @Test
    fun `unary plus is not implemented on LongRange values, but can be overloaded`() {
        operator fun LongRange.unaryPlus(): Long? {
            return this.sum()
        }
        +(1L..3L) shouldBe 6L
    }

    @Test
    fun `unary plus is not implemented on CharRange values, but can be overloaded`() {
        operator fun CharRange.unaryPlus(): Int? {
            return this.sumOf { it.code }
        }
        +('a'..'c') shouldBe 294
    }
}




