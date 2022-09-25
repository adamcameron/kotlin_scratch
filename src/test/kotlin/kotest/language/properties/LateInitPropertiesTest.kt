package kotest.language.properties

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class LateInitPropertiesTest : DescribeSpec({
    it("allows me to create a colour object without an mi property") {
        val red  = Colour("red")
        red.en shouldBe "red"
    }

    it("lets me set the mi property later") {
        val orange  = Colour("orange")
        orange.setMaori("karaka")

        orange.mi shouldBe "karaka"
    }

    it("throws UninitializedPropertyAccessException if access mi before it's been set") {
        val yellow  = Colour("yellow")

        val exception = shouldThrow<UninitializedPropertyAccessException> {
            yellow.mi shouldNotBe "kōwhai"
        }
        exception.message shouldBe "lateinit property mi has not been initialized"
    }

    it("throws UninitializedPropertyAccessException if use mi before it's been set") {
        val green  = Colour("green")

        val exception = shouldThrow<UninitializedPropertyAccessException> {
            green.getMaori() shouldNotBe "kakariki"
        }
        exception.message shouldBe "lateinit property mi has not been initialized"
    }

    it("can have initialisation status checked") {
        val green  = Colour("green")

        green.isMiInitialized() shouldBe false
        green.setMaori("kakariki")
        green.isMiInitialized() shouldBe true
    }
})

class Colour(val en: String) {
    lateinit var mi: String

    fun setMaori(value: String) {
        mi = value
    }

    fun getMaori() = mi

    fun isMiInitialized() = ::mi.isInitialized
}
