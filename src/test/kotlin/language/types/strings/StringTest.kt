package language.types.strings

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class StringTest : DescribeSpec({
     describe("raw string tests") {
         it("can be delimited with three quotes (and double quote is otherwise escaped with a backslash") {
             val doubleQuote = """"""" // yeah there's 7 there
             doubleQuote shouldBe "\""
         }
     }

    describe("trimIndent tests") {
         it("can handle indentation in the triple-quoted string") {
             val message = """    
                 this is the message
             """.trimIndent()

             message shouldBe "this is the message"
         }

         it("respects trailing space on trimmed lines") {
             val message = """    
                 this has four space at the end    
             """.trimIndent()

             message shouldBe "this has four space at the end    "
         }

         it("DOES NOT seem to trim indentation if the initial delimiter is not on a line by itself") {
             val message = """first line next to delimiter
                 second line indented
             """.trimIndent()

             // message shouldBe "first line next to delimiter\nsecond line indented"
             message shouldBe "first line next to delimiter\n                 second line indented"
         }

         it("DOES NOT respect trailing space on the first delimiter line") {
             // the line below still has four trailing spaces
             val message = """    
                 this is indented
             """.trimIndent()

             //message shouldBe "    \nthis is indented"
             message shouldBe "this is indented"
         }
     }

    describe("trimMargin tests") {
        it("does what it says on the tin") {
            val testString = """
                | has | as a margin character
                | | is the default btw
            """.trimMargin()

            testString shouldBe " has | as a margin character\n | is the default btw"
        }

        it("handles a different margin") {
            val testString = """
                # has "# "
                # as a margin value
            """.trimMargin("# ")

            testString shouldBe "has \"# \"\nas a margin value"
        }
    }

    describe("template expression tests") {
        it("resolves a variable in a string literal") {
            val name = "Zachary"

            "G'day $name" shouldBe "G'day Zachary"
        }

        it("resolves a variable in a raw string") {
            val name = "Joe"

            """
                G'day $name
            """.trimIndent() shouldBe "G'day Joe"
        }

        it("can use curly braces to disambiguate where the variable name ends") {
            val prefix = "SOME_PREFIX_"
            "${prefix}REST_OF_STRING" shouldBe "SOME_PREFIX_REST_OF_STRING"
        }

        it("can take more complicated expressions") {
            val name = "Zachary"

            "G'day ${name.uppercase()}" shouldBe "G'day ZACHARY"
        }

        it("can take blocks of code provided they resolve to a string?") {
            val name = "Joe"
            val case = "lower"

            "G'day ${if (case == "upper"){name.uppercase()} else {name.lowercase()}}" shouldBe "G'day joe"
        }
    }
})
