package language

import com.github.stefanbirkner.systemlambda.SystemLambda
import io.kotest.assertions.asClue
import io.kotest.assertions.withClue
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class ScopeFunctionsTest : DescribeSpec ({
    describe("Test of scope functions") {
        class Colour(var maori:String? = null, var english:String? = null){
            override fun toString() = "$maori: $english"
        }

        describe("Tests of let function") {
            it("provides an object to run the statements in the lambda with") {
                val result = listOf(
                    Colour("whero"),
                    Colour("karaka"),
                    Colour("kōwhai")
                ).first()
                .let {
                    it.english = "red"
                    "${it.maori}: ${it.english}"
                }
                result shouldBe "whero: red"
            }
        }

        describe("Tests of with function") {
            it("calls the statements in the lambda with the given object") {
                val green = Colour()

                with(green) {
                    maori = "kākāriki"
                    english = "green"
                }
                "${green.maori}: ${green.english}" shouldBe "kākāriki: green"
            }

            it("will return something") {
                val purple = with(Colour()) {
                    maori = "papura"
                    english = "purple"

                    this
                }
                "${purple.maori}: ${purple.english}" shouldBe "papura: purple"
            }
        }

        describe("Tests of run function") {
            it("applies the statements in the lambda to the object") {
                val blue = listOf(
                    Colour("whero"),
                    Colour("kākāriki"),
                    Colour("kikorangi")
                ).last()
                .run {
                    english = "blue"

                    this
                }
                "${blue.maori}: ${blue.english}" shouldBe "kikorangi: blue"
            }
        }

        describe("Tests of apply function") {
            it("applies the assignments in the lambda to the object") {
                val yellow = Colour().apply {
                    maori = "kōwhai"
                    english = "yellow"
                }
                "${yellow.maori}: ${yellow.english}" shouldBe "kōwhai: yellow"
            }
        }

        describe("Tests of also function") {
            it("also runs the statements in the block with the object") {
                val indigo = Colour().also {
                    it.maori = "tūāuri"
                    it.english = "indigo"
                }
                "${indigo.maori}: ${indigo.english}" shouldBe "tūāuri: indigo"
            }

            it("can use a callable reference to use a function as the callback") {
                val orange = Colour("karaka", "orange")

                val output = SystemLambda.tapSystemOut {
                    "${orange.maori}: ${orange.english}".also(::print)
                }

                output shouldBe "karaka: orange"
            }
        }

        describe("Tests of takeIf function") {
            it("returns the object if the predicate is true, otherwise null") {
                var number = 210

                var checked = number.takeIf { it % 2 == 0 }
                checked shouldBe number

                checked = checked?.takeIf { it % 3 == 0 }
                checked shouldBe number

                checked = checked?.takeIf { it % 5 == 0 }
                checked shouldBe number

                checked = checked?.takeIf { it % 7 == 0 }
                checked shouldBe number

                checked = checked?.takeIf { it % 11 == 0 }
                checked.shouldBeNull()
            }
        }

        describe("Tests of takeUnless function") {
            it("returns the object if the predicate is false, otherwise null") {
                val number = 210

                number.asClue {
                    var checked = it.takeUnless { it % 2 != 0 }
                    withClue("should be a multiple of 2") {
                        checked shouldBe it
                    }

                    checked = checked?.takeUnless { it % 3 != 0 }
                    withClue("should be a multiple of 3") {
                        checked shouldBe it
                    }

                    checked = checked?.takeUnless { it % 5 != 0 }
                    withClue("should be a multiple of 5") {
                        checked shouldBe it
                    }

                    checked = checked?.takeUnless { it % 7 != 0 }
                    withClue("should be a multiple of 7") {
                        checked shouldBe it
                    }

                    checked = checked?.takeUnless { it % 11 != 0 }
                    withClue("should NOT be a multiple of 11") {
                        checked.shouldBeNull()
                    }

                    checked = checked?.takeUnless { it % 13 != 0 }
                    withClue("should be a multiple of 13") {
                        checked shouldBe it
                    }
                }
            }
        }
    }
})
