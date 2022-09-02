package language.strings

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
})
