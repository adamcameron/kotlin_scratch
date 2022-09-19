package language.classes

import com.github.stefanbirkner.systemlambda.SystemLambda
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Suffragist(firstName:String, lastName:String) {
    val fullName = "$firstName $lastName"
}

class Scientist(val firstName:String, val lastName:String)

class Politician(val firstName:String, val lastName:String) {
    init {
        println("First name: $firstName")
    }

    init {
        println("Last name: $lastName")
    }
}

class FilmMaker(val firstName:String, val lastName:String) {
    val fullName = "$firstName $lastName"
    constructor(names: Map<String, String>) : this(names["firstName"]!!, names["lastName"]!!)
}

class ChiefJustice {
    var firstName = ""
    var lastName = ""
    var fullName = ""

    constructor(firstName:String, lastName:String) {
        this.firstName = firstName
        this.lastName = lastName

        fullName = "$firstName $lastName"
    }

    constructor(names: List<String>) : this(names.first(), names.last())
}

class ClassesTest : DescribeSpec({
    describe("Constructor tests") {
        it("takes values that can be used in initialisation code") {
            val suffragist = Suffragist("Kate", "Sheppard")

            suffragist.fullName shouldBe "Kate Sheppard"
        }

        it("needs to qualify params to make them properties") {
            val scientist = Scientist("Siouxsie", "Wiles")

            scientist.firstName shouldBe "Siouxsie"
            scientist.lastName shouldBe "Wiles"
        }

        it("can have code in initializer blocks") {
            val EOL = System.lineSeparator()
            val output = SystemLambda.tapSystemOut {
                Politician("Jacinda", "Ardern")
            }

            output shouldBe "First name: Jacinda${EOL}Last name: Ardern${EOL}"
        }

        it("can have a secondary constructor") {
            val names = mapOf(Pair("firstName", "Jane"), "lastName" to "Campion")
            val filmMaker = FilmMaker(names)

            filmMaker.fullName shouldBe "Jane Campion"
        }

        it("can call another secondary constructor") {
            val names = listOf("Helen", "Winkelmann")
            val chiefJustice = ChiefJustice(names)

            chiefJustice.fullName shouldBe "Helen Winkelmann"
        }
    }
})
