package junit.language.operators

import io.kotest.matchers.shouldBe
import org.atteo.evo.inflector.English
import org.junit.jupiter.api.Test

internal class `Operator overloading tests` {
    class Arm
    class Leg
    class Head

    class Critter(){
        private var heads = mutableListOf<Head>()
        private var arms = mutableListOf<Arm>()
        private var legs = mutableListOf<Leg>()

        operator fun plus(head: Head) : Critter {
            heads.add(head)
            return this
        }
        operator fun plus(arm: Arm) : Critter {
            arms.add(arm)
            return this
        }
        operator fun plus(leg: Leg) : Critter  {
            legs.add(leg)
            return this
        }

        fun describe()= """
                    Critter with ${heads.size} ${English.plural("head", heads.size)},
                    and ${arms.size} ${English.plural("arm", arms.size)},
                    and ${legs.size} ${English.plural("leg", legs.size)}
                """.trimIndent().replace("\n", " ")
    }

    @Test
    fun `it can overload the same operator with different operands` () {
        val critter = Critter()
        critter + Head()
        critter + Arm() + Arm()
        critter + Leg() + Leg() + Leg() + Leg()

        critter.describe() shouldBe "Critter with 1 head, and 2 arms, and 4 legs"
    }

    @Test
    fun `it can mix-up the body parts` () {
        val critter = Critter() + Head() + Arm() + Leg() + Leg() + Arm() + Leg() + Head() + Leg()

        critter.describe() shouldBe "Critter with 2 heads, and 2 arms, and 4 legs"
    }

}