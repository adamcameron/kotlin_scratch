package kotest.language.types

import com.github.stefanbirkner.systemlambda.SystemLambda
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.text.Collator
import java.util.Locale

class FunctionsTest : DescribeSpec ({
    describe("Testing lambdas") {
        class Number1(var value: Int, var en: String, var mi: String) {}

        class Number {
            var value = 0
            var en = ""
            var mi = ""

            constructor(value: Int, en: String, mi: String) {
                this.value = value
                this.en = en
                this.mi = mi
            }
        }

        it("is a basic PoC test") {
            val strings = listOf("whero", "kākāriki", "kikorangi")
            val lengths = strings.map {it.length}

            lengths.joinToString(",") shouldBe "5,8,9"
        }

        it("chains some together") {
            val jumbledNumbers = listOf(
                Number(2, "two", "rua"),
                Number(4, "four", "wha"),
                Number(3, "three", "toru"),
                Number(1, "one", "tahi")
            )
            val maoriNumbers = jumbledNumbers.sortedBy{it.value}.map{it.mi}

            maoriNumbers.joinToString(",") shouldBe "tahi,rua,toru,wha"
        }

        it("shows using _ to ignore a param in a lambda") {
            val numbers = mapOf(
                Pair("one", "tahi"),
                Pair("two", "rua"),
                Pair("three", "toru"),
                Pair("four", "wha")
            )
            val output = SystemLambda.tapSystemOut {
                numbers.forEach{(_, maori) -> print("$maori")}
            }

            output.trim() shouldBe "tahiruatoruwha"
        }

        it("uses an anonymous function") {
            val jumbledNumbers = listOf(
                Number(2, "two", "rua"),
                Number(4, "four", "wha"),
                Number(3, "three", "toru"),
                Number(1, "one", "tahi")
            )

            val maoriNumbers = jumbledNumbers.sortedWith(fun(e1, e2) = e1.value - e2.value).map{it.mi}

            maoriNumbers.joinToString(",") shouldBe "tahi,rua,toru,wha"
        }

        it("is an example of a fold") {
            val rgb = listOf("whero", "kākāriki", "kikorangi")

            val colours = rgb.fold("") {
                colours, colour -> if (colours.isEmpty()) colour else "$colours,$colour"
            }

            colours shouldBe "whero,kākāriki,kikorangi"
        }

        it("is an example of a reduce") {
            val redWhiteAndBlue = listOf("whero", "mā", "kikorangi")

            val colours = redWhiteAndBlue.reduce {colours, colour -> "$colours,$colour"}

            colours shouldBe "whero,mā,kikorangi"
        }

        it("groups the words by their first character, capitalising each word in the result") {
            val words = listOf(
                "tahi","rua","toru","wha","rima","ono","whitu","waru","iwa","tekau", // 1-10
                "whero","karaka","kōwhai","kākāriki","kikorangi","poropango","papura", // ROYGBIV
                "rāhina","rātū","rāapa","rāpare","rāmere","rāhoroi","rātapu" // Mon-Sun
            ).sortedWith(Collator.getInstance(Locale("mi_NZ")))

            val groupedByLetter = words.groupBy(
                { word -> word.first()},
                {word ->  word.replaceFirstChar(Char::titlecase)}
            ).toSortedMap()

            groupedByLetter shouldBe mapOf(
                Pair('i', listOf("Iwa")),
                Pair('k', listOf("Kākāriki", "Karaka", "Kikorangi", "Kōwhai")),
                Pair('o', listOf("Ono")),
                Pair('p', listOf("Papura", "Poropango")),
                Pair('r', listOf("Rāapa", "Rāhina", "Rāhoroi", "Rāmere", "Rāpare", "Rātapu", "Rātū", "Rima", "Rua")),
                Pair('t', listOf("Tahi", "Tekau", "Toru")),
                Pair('w', listOf("Waru", "Wha", "Whero", "Whitu"))
            )
        }
    }
})
