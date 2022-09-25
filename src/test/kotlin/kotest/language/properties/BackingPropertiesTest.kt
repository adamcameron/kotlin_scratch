package kotest.language.properties

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class BackingPropertiesTest : DescribeSpec({
    it("should take a MutableList and return an ImmutableList") {
        val mutableList = mutableListOf("tahi", "rua", "toru")
        val myList = MyList()
        myList.mutable = mutableList

        myList.immutable.shouldBeInstanceOf<List<String>>()
        myList.immutable shouldBe listOf("tahi", "rua", "toru")

        mutableList.add("wha")
        myList.immutable.shouldBeInstanceOf<List<String>>()
        myList.immutable shouldBe listOf("tahi", "rua", "toru", "wha")
    }
})

class MyList {
    private var _myList = mutableListOf<String>()
    val immutable: List<String>
        get() = _myList
    var mutable: MutableList<String> = mutableListOf()
        set(value) {
            field = value
            _myList = value
        }
}
