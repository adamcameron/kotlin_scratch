import com.github.stefanbirkner.systemlambda.SystemLambda
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class MainTest : DescribeSpec ({
    describe("Tests of Main class") {
        val EOL = System.lineSeparator()

        it("outputs G'day World & its args") {
            val testArgs = arrayOf("some arg", "some other arg")
            val output = SystemLambda.tapSystemOut {
                main(testArgs)
            }
            output.trim() shouldBe "G'day World!${EOL}Program arguments: some arg, some other arg"
        }
        it("works OK with no args") {
            val testArgs = arrayOf<String>()
            val output = SystemLambda.tapSystemOut {
                main(testArgs)
            }
            output.trim() shouldBe "G'day World!${EOL}Program arguments:"
        }
    }
})
