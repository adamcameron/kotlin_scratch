package junit.language.classes

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import junit.language.classes.fixtures.package1.*
import org.junit.jupiter.api.Test

@DisplayName("Tests of sealed classes")
class SealedClassesTest {

    @Test
    fun `A sealed class does not need an else in a when`() {
        fun handleSomeSealedInterfaceInstance(obj: SomeSealedInterface): String {
            return when (obj) {
                is SomeSealedImplementation -> obj.useThisMethod()
                is SomeOtherSealedImplementation -> obj.useThisOtherMethod()
            }
        }
        val testObj = SomeSealedImplementation("some property value")

        val result = handleSomeSealedInterfaceInstance(testObj)

        result shouldBe "some property value"
    }
}
