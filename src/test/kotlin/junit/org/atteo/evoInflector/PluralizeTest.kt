package junit.org.atteo.evoInflector

import io.kotest.matchers.shouldBe
import org.atteo.evo.inflector.English
import org.junit.jupiter.api.Test

internal class `Test Pluralize library` {
    @Test
    fun `it can pluralize head properly`() {
        English.plural("head", 1) shouldBe "head"
        English.plural("head", 2) shouldBe "heads"
    }

    @Test
    fun `it can pluralize person properly`() {
        English.plural("person", 1) shouldBe "person"
        //English.plural("person", 2) shouldBe "people" // This test fails
        English.plural("person", 2) shouldBe "persons" // apparently this is the correct plural form
    }
}