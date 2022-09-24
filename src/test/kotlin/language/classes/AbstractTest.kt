package language.classes

import com.github.stefanbirkner.systemlambda.SystemLambda
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

abstract class Shape {
    open fun draw() = print("Shape.draw")
}

abstract class WildShape : Shape() {
    abstract override fun draw()
}

class MyWildShape : WildShape() {
     override fun draw() = print("MyWildShape.draw")
}

class AbstractTest : DescribeSpec({
    describe("abstract test") {
        it("permits concrete subclasses to override abstract methods that are not overridden in the base class") {
            val mws = MyWildShape()
            val output = SystemLambda.tapSystemOut {
                mws.draw()
            }
            output shouldBe "MyWildShape.draw"
        }
    }
})
