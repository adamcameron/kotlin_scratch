package kotest.language.classes

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class InheritanceTest : DescribeSpec({
  describe("Inheritance") {
    it("is a thing") {
      open class Person(val name: String) {
        open fun introduce() = "Hello, my name is $name"
      }

      class Student(name: String, val studentId: Int) : Person(name) {
        override fun introduce() = "Hello, my name is $name and my student id is $studentId"
      }

      val person = Person("John")
      person.introduce() shouldBe "Hello, my name is John"

      val student = Student("John", 123)
      student.introduce() shouldBe "Hello, my name is John and my student id is 123"
    }
  }
})
