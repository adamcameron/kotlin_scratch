package junit.language.classes

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@DisplayName("Tests of data classes")
internal class DataClassTest {

    @Test
    @DisplayName("should have a toString() method")
    fun testToString() {
        data class Person(val name: String, val age: Int)
        val person = Person("John", 30)
        assertEquals("Person(name=John, age=30)", person.toString())
    }

    @Test
    @DisplayName("should have a copy() method")
    fun testCopy() {
        data class Person(val name: String, val age: Int)
        val person = Person("John", 30)
        assertEquals(person, person.copy())
        assertEquals(Person("Jane", 30), person.copy(name = "Jane"))
        assertEquals(Person("John", 31), person.copy(age = 31))
    }

    @Test
    @DisplayName("should have a componentN() method")
    fun testComponentN() {
        data class Person(val name: String, val age: Int)
        val person = Person("John", 30)
        val (name, age) = person
        assertEquals("John", name)
        assertEquals(30, age)
        assertEquals("John", person.component1())
    }

    @Test
    @DisplayName("should have discrete componentN() methods")
    fun testDiscreteComponentN() {
        data class Person(val name: String, val age: Int)
        val person = Person("John", 30)
        assertEquals("John", person.component1())
        assertEquals(30, person.component2())
    }

    @Test
    @DisplayName("should have an equals() method")
    fun testEquals() {
        data class Person(val name: String, val age: Int)
        val person = Person("John", 30)
        assertEquals(Person("John", 30), person)
        assertEquals(Person("Jane", 30), person.copy(name = "Jane"))
        assertEquals(Person("John", 31), person.copy(age = 31))
    }

    @Test
    @DisplayName("is possible to hand-crank one's own componentN methods?")
    fun testHandCrankedComponentN() {
        class Person(val name: String, val age: Int) {
            operator fun component1() = name
            operator fun component2() = age
        }
        val person = Person("John", 30)
        val (name, age) = person
        assertEquals("John", name)
        assertEquals(30, age)
    }

    @Test
    @DisplayName("can have customised ordering and logic in the componentN methods")
    fun testCustomisedComponentN() {
        class Person(val name: String, val age: Int) {
            operator fun component1() = "Age: $age"
            operator fun component2() = "Name: $name"
            operator fun component3() = "Name: $name, Age: $age"
        }
        val person = Person("John", 30)
        val (age, name, nameAge) = person
        assertEquals("Age: 30", age)
        assertEquals("Name: John", name)
        assertEquals("Name: John, Age: 30", nameAge)
    }
}
