package junit.language.coroutines

import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.delay
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

@DisplayName("Tests of basic coroutine operations")
internal class BaselineTest {

    @Test
    fun `a coroutine can be created`() = runBlocking {
        val result = suspend {
            "Hello"
        }
        result() shouldBe "Hello"
    }

    @Test
    fun `a coroutine with a delay runs asynchronously`() = runBlocking {
        val result = suspend {
            delay(1000)
            "Hello"
        }
        result() shouldBe "Hello"
    }

    @Test
    fun `two coroutines can be run in parallel`() = runBlocking {
        val result1 = suspend {
            delay(1000)
            "Hello"
        }
        val result2 = suspend {
            delay(1000)
            "World"
        }
        var greeting =""
        val timeInMillis = measureTimeMillis {
            greeting = result1()
            greeting += " " + result2()
        }

        greeting shouldBe "Hello World"
        timeInMillis shouldBe 1000L
    }

}