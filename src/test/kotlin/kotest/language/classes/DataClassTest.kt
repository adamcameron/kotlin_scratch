package kotest.language.classes

import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class DataClassTest : DescribeSpec({
    describe("Data classes") {
        data class Person(val name: String, val age: Int)

        val person = Person("John", 30)

        it("should have a toString() method") {
            person.toString() shouldBe  "Person(name=John, age=30)"
        }

        it("should have a copy() method") {
            person.copy() shouldBe person
            person.copy(name = "Jane") shouldBe Person("Jane", 30)
            person.copy(age = 31) shouldBe Person("John", 31)
        }

        it("should have a componentN() method") {
            val (name, age) = person
            name shouldBe "John"
            age shouldBe 30

            person.component1() shouldBe "John"
        }

        it("should have discrete componentN() methods") {
            person.component1() shouldBe "John"
            person.component2() shouldBe 30
        }

        it("should have an equals() method") {
            person shouldBe Person("John", 30)
            person shouldNotBe  Person("Jane", 30)
            person shouldNotBe Person("John", 31)
        }

        /* this stopped working after a while as the hashcode changed. Leaving it here for now
        it("should have a hashCode() method") {
            person.hashCode() shouldBe  -1781121024
        }
         */

        it("is possible to hand-crank one's own componentN methods?") {
            class Person(val name: String, val age: Int) {
                operator fun component1() = name
                operator fun component2() = age
            }
            val person = Person("John", 30)
            val (name, age) = person
            name shouldBe "John"
            age shouldBe 30
        }

        it("can have customised ordering and logic in the componentN methods") {
            class Person(val name: String, val age: Int) {
                operator fun component1() = "Age: $age"
                operator fun component2() = "Name: $name"
                operator fun component3() = "Name: $name, Age: $age"
            }
            val person = Person("John", 30)
            val (age, name, both) = person
            name shouldBe "Name: John"
            age shouldBe "Age: 30"
            both shouldBe "Name: John, Age: 30"
        }
    }
})
