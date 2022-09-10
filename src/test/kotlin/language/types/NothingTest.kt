package language.types

import com.github.stefanbirkner.systemlambda.SystemLambda
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.lang.IllegalArgumentException

class NothingTest : DescribeSpec( {
    describe("Tests of the Nothing type") {
        val EOL = System.lineSeparator()

        val output = SystemLambda.tapSystemOut {
            try {
                outputUntilDie(3)
            } catch (e: Exception) {
                println(e.message)
            }
        }
        output.trim() shouldBe "Iteration 1${EOL}Iteration 2${EOL}Iteration 3${EOL}It died"
    }
})

fun outputUntilDie(max :Int) :Nothing {
    var current = 0
    while (true) {
        current++
        println("Iteration $current")
        if (current >= max) {
            throw Exception("It died")
        }
    }
}
