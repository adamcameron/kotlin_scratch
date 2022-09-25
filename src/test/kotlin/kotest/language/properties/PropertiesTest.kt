import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class PropertiesTest : DescribeSpec ({
    describe("property accessor tests") {
        class Person {
            var firstName = ""
                set(value) {
                    field = value.replaceFirstChar(Char::titlecase)
                }
            var lastName = ""
                set(value) {
                    field = value.replaceFirstChar(Char::titlecase)
                }

            val fullName
                get() = "$firstName $lastName"

            fun setBoth(firstName:String, lastName:String) {
                this.firstName = firstName
                this.lastName = lastName
            }
        }

        it("uses getters/setters") {
            var person = Person()
            person.firstName = "jane"
            person.lastName = "roe"

            person.fullName shouldBe "Jane Roe"
        }

        it("uses setters on internal property assignments") {
            var person = Person()
            person.setBoth("sojourner", "truth")

            person.fullName shouldBe "Sojourner Truth"

        }
    }

    describe("static property accessor tests") {
        it("uses getters/setters") {
            StaticPerson.firstName = "connor"
            StaticPerson.lastName = "macLeod"

            StaticPerson.fullName shouldBe "Connor MacLeod"
        }
    }
})

class StaticPerson {
    companion object OnlyOne {
        var firstName = ""
            set(value) {
                field = value.replaceFirstChar(Char::titlecase)
            }
        var lastName = ""
            set(value) {
                field = value.replaceFirstChar(Char::titlecase)
            }

        val fullName
            get() = "$firstName $lastName"
    }
}
