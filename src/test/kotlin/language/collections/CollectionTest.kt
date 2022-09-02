package language.collections

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class CollectionTest : DescribeSpec ({
    describe("Tests of joinToString") {

        val numbers = listOf<String>("tahi", "rua", "toru", "wha")

        it("takes separator (defaults to comma-space), prefix, postfix named parameters") {
            numbers.joinToString(postfix = ">", prefix = "<") shouldBe "<tahi, rua, toru, wha>"
        }

        it("takes a transformer callback which modifies each element") {
            numbers.joinToString("|", "<", ">") { "\"$it\""  } shouldBe """<"tahi"|"rua"|"toru"|"wha">"""
        }
    }
})
