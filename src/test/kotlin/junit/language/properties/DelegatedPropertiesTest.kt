package junit.language.properties

import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.withClue
import io.kotest.matchers.ints.shouldNotBeGreaterThan
import io.kotest.matchers.longs.shouldBeGreaterThan
import io.kotest.matchers.longs.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.properties.Delegates

@DisplayName("tests of delegated Properties")
internal class DelegatedPropertiesTest {

    @Nested
    @DisplayName("Tests of property delegates")
    inner class PropertyDelegates {

        @Test
        fun `It can delegate the Maori translation of a word`() {
            val one = Number(1)
            one.mi shouldBe "tahi"
            one.en shouldBe "one"
        }

        @Test
        fun `It throws an exception when the translations are not found`() {
            val eleven = Number(11)
            shouldThrow<IllegalArgumentException> {
                eleven.mi
            }.message shouldBe "No Maori translation for 11"
            shouldThrow<IllegalArgumentException> {
                eleven.en
            }.message shouldBe "No English translation for 11"
        }
    }

    @Nested
    @DisplayName("Tests of lazy properties")
    inner class LazyProperties {
        inner class TestClass {
            inner class TelemetryEntry(val message: String, val lap: Long = System.currentTimeMillis()){}
            val telemetryLog = mutableListOf(TelemetryEntry("Log Initialised"))
            val normalProperty: String
                get() {
                    Thread.sleep(100)
                    telemetryLog.add(TelemetryEntry("normalProperty initialised"))
                    return "normalProperty"
                }

            val lazyProperty: String by lazy {
                Thread.sleep(100)
                telemetryLog.add(TelemetryEntry("lazyProperty initialised"))
                "lazyProperty"
            }

            private lateinit var _handCrankedLazyProperty: String
            val handCrankedLazyProperty: String
                get() {
                    if (::_handCrankedLazyProperty.isInitialized) {
                        telemetryLog.add(TelemetryEntry("handCrankedLazyProperty getter accessed"))
                        return _handCrankedLazyProperty
                    }
                    Thread.sleep(100)
                    telemetryLog.add(TelemetryEntry("handCrankedLazyProperty initialised"))
                    _handCrankedLazyProperty = "handCrankedLazyProperty"
                    return _handCrankedLazyProperty
                }
        }
        @Test
        fun `It is only initialise when accessed`() {
            val testClass = TestClass()
            testClass.normalProperty shouldBe "normalProperty"
            testClass.normalProperty shouldBe "normalProperty"
            testClass.lazyProperty shouldBe "lazyProperty"
            testClass.lazyProperty shouldBe "lazyProperty"
            testClass.handCrankedLazyProperty shouldBe "handCrankedLazyProperty"
            testClass.handCrankedLazyProperty shouldBe "handCrankedLazyProperty"

            val laps = testClass.telemetryLog.zipWithNext { a, b -> b.lap - a.lap }
            val events = testClass.telemetryLog.drop(1)
            val eventTimes = events.zip(laps) { a, b -> a.message to b }

            eventTimes.asClue {
                it.size shouldBe 5
                it[0].first shouldBe "normalProperty initialised"
                it[0].second shouldBeGreaterThan 100
                it[1].first shouldBe "normalProperty initialised"
                it[1].second shouldBeGreaterThan 100
                it[2].first shouldBe "lazyProperty initialised"
                it[2].second shouldBeGreaterThan 100
                it[3].first shouldBe "handCrankedLazyProperty initialised"
                it[3].second shouldBeGreaterThan 100
                it[4].first shouldBe "handCrankedLazyProperty getter accessed"
                it[4].second shouldBeLessThan 100
            }
        }

        @Test
        fun `It can throw an exception on initialisation but will continue to attempt to initialise the property on subsequent calls`() {
            class TestClass {
                private var initAttempts = 0

                val lazyProperty: String by lazy {
                    initAttempts++
                    telemetry.add("lazyProperty initialisation called ($initAttempts)")
                    if (initAttempts <= 2) {
                        throw IllegalStateException("lazyProperty initialisation failed ($initAttempts)")
                    }
                    telemetry.add("lazyProperty initialisation succeeded ($initAttempts)")
                    "lazyProperty"
                }
                val telemetry: MutableList<String> = mutableListOf("initialising telemetry")
            }

            val testClass = TestClass()
            testClass.telemetry[0] shouldBe "initialising telemetry"

            shouldThrow<RuntimeException> {
                testClass.lazyProperty
            }.message shouldBe "lazyProperty initialisation failed (1)"

            testClass.telemetry.size shouldBe 2
            testClass.telemetry[1] shouldBe "lazyProperty initialisation called (1)"

            shouldThrow<RuntimeException> {
                testClass.lazyProperty
            }.message shouldBe "lazyProperty initialisation failed (2)"
            testClass.telemetry.size shouldBe 3
            testClass.telemetry[2] shouldBe "lazyProperty initialisation called (2)"

            shouldNotThrow<RuntimeException> {
                testClass.lazyProperty
            }
            testClass.telemetry.size shouldBe 5
            testClass.telemetry[3] shouldBe "lazyProperty initialisation called (3)"
            testClass.telemetry[4] shouldBe "lazyProperty initialisation succeeded (3)"

            shouldNotThrow<RuntimeException> {
                testClass.lazyProperty
            }
            testClass.telemetry.size shouldNotBeGreaterThan 5
            testClass.telemetry.last() shouldBe "lazyProperty initialisation succeeded (3)"
        }
    }

    @Nested
    @DisplayName("Tests of observable properties")
    inner class ObservableProperties {
        inner class TestClass {
            var observableProperty: String by Delegates.observable("initial value") {
                property, oldValue, newValue ->
                telemetry.add("${property.name} changed from $oldValue to $newValue")
            }
            var standardProperty : String = "initial value"
                set(value) {
                    telemetry.add("standardProperty changed from $field to $value")
                    field = value
                }
            val telemetry: MutableList<String> = mutableListOf("initialising telemetry")
        }

        @Test
        fun `It shows that observable properties can be used to track changes to a property`() {
            val testClass = TestClass()

            testClass.telemetry.first() shouldBe "initialising telemetry"
            testClass.observableProperty shouldBe "initial value"
            testClass.telemetry.size shouldBe 1

            testClass.observableProperty = "new value"
            testClass.telemetry.size shouldBe 2
            testClass.telemetry[1] shouldBe "observableProperty changed from initial value to new value"
            testClass.observableProperty shouldBe "new value"

            testClass.observableProperty = "newer value"
            testClass.telemetry.size shouldBe 3
            testClass.telemetry[2] shouldBe "observableProperty changed from new value to newer value"
            testClass.observableProperty shouldBe "newer value"

            testClass.observableProperty = "newer value"
            testClass.telemetry.size shouldBe 4
            testClass.telemetry[3] shouldBe "observableProperty changed from newer value to newer value"
            testClass.observableProperty shouldBe "newer value"
        }

        @Test
        fun `It shows that standard properties can be used to track changes to a property`() {
            val testClass = TestClass()

            testClass.telemetry.first() shouldBe "initialising telemetry"
            testClass.standardProperty shouldBe "initial value"
            testClass.telemetry.size shouldBe 1

            testClass.standardProperty = "new value"
            testClass.telemetry.size shouldBe 2
            testClass.telemetry[1] shouldBe "standardProperty changed from initial value to new value"
            testClass.standardProperty shouldBe "new value"

            testClass.standardProperty = "newer value"
            testClass.telemetry.size shouldBe 3
            testClass.telemetry[2] shouldBe "standardProperty changed from new value to newer value"
            testClass.standardProperty shouldBe "newer value"

            testClass.standardProperty = "newer value"
            testClass.telemetry.size shouldBe 4
            testClass.telemetry[3] shouldBe "standardProperty changed from newer value to newer value"
            testClass.standardProperty shouldBe "newer value"
        }

        @Test
        fun `It shows that observable properties can be used to track changes to a property with a custom setter`() {
            class TestClass {
                var observableProperty: String = "initial value"
                    set(value) {
                        telemetry.add("observableProperty changed from $field to $value")
                        field = value
                    }
                val telemetry: MutableList<String> = mutableListOf("initialising telemetry")
            }

            val testClass = TestClass()
            testClass.telemetry.first() shouldBe "initialising telemetry"
            testClass.observableProperty shouldBe "initial value"
            testClass.telemetry.size shouldBe 1

            testClass.observableProperty = "new value"
            testClass.telemetry.size shouldBe 2
            testClass.telemetry[1] shouldBe "observableProperty changed from initial value to new value"
            testClass.observableProperty shouldBe "new value"

            testClass.observableProperty = "newer value"
            testClass.telemetry.size shouldBe 3
            testClass.telemetry[2] shouldBe "observableProperty changed from new value to newer value"
            testClass.observableProperty shouldBe "newer value"

            testClass.observableProperty = "newer value"
            testClass.telemetry.size shouldBe 4
            testClass.telemetry[3] shouldBe "observableProperty changed from newer value to newer value"
            testClass.observableProperty shouldBe "newer value"
        }

        @Test
        fun `It shows that observable properties can be used to track changes to a property with a custom setter and a custom getter`() {
            class TestClass {
                var observableProperty: String = "initial value"
                    set(value) {
                        telemetry.add("observableProperty changed from $field to $value")
                        field = value
                    }
                    get() {
                        telemetry.add("observableProperty accessed")
                        return field
                    }
                val telemetry: MutableList<String> = mutableListOf("initialising telemetry")
            }

            val testClass = TestClass()
            testClass.telemetry.first() shouldBe "initialising telemetry"

            testClass.observableProperty shouldBe "initial value"
            testClass.telemetry.size shouldBe 2
            testClass.telemetry[1] shouldBe "observableProperty accessed"

            testClass.observableProperty = "new value"
            testClass.telemetry.size shouldBe 3
            testClass.telemetry[2] shouldBe "observableProperty changed from initial value to new value"

            testClass.observableProperty shouldBe "new value"
            testClass.telemetry.size shouldBe 4
            testClass.telemetry[3] shouldBe "observableProperty accessed"
        }

        @Test
        fun `It shows that observable properties can be used to track changes to a property with a custom setter and a custom getter that throws an exception`() {
            class TestClass {
                var observableProperty: String = "initial value"
                    set(value) {
                        telemetry.add("observableProperty changed from $field to $value")
                        field = value
                    }
                    get() {
                        telemetry.add("observableProperty accessed")
                        throw RuntimeException("observableProperty exception")
                    }
                val telemetry: MutableList<String> = mutableListOf("initialising telemetry")
            }

            val testClass = TestClass()
            testClass.telemetry.first() shouldBe "initialising telemetry"

            shouldThrow<RuntimeException> {
                testClass.observableProperty
            }.message shouldBe "observableProperty exception"
            testClass.telemetry.size shouldBe 2
            testClass.telemetry[1] shouldBe "observableProperty accessed"

            testClass.observableProperty = "new value"
            testClass.telemetry.size shouldBe 3
            testClass.telemetry[2] shouldBe "observableProperty changed from initial value to new value"
        }

        @Test
        fun `It shows that observable properties can be used to track changes to a property with a custom setter and a custom getter that throws an exception and then succeeds`() {
            class TestClass {
                var observableProperty: String = "initial value"
                    set(value) {
                        telemetry.add("observableProperty changed from $field to $value")
                        field = value
                    }
                    get() {
                        telemetry.add("observableProperty accessed")
                        if (telemetry.size == 2) {
                            throw RuntimeException("observableProperty exception")
                        }
                        return field
                    }
                val telemetry: MutableList<String> = mutableListOf("initialising telemetry")
            }

            val testClass = TestClass()
            testClass.telemetry.first() shouldBe "initialising telemetry"

            shouldThrow<RuntimeException> {
                testClass.observableProperty
            }.message shouldBe "observableProperty exception"
            testClass.telemetry.size shouldBe 2
            testClass.telemetry[1] shouldBe "observableProperty accessed"

            testClass.observableProperty shouldBe "initial value"
            testClass.telemetry.size shouldBe 3
            testClass.telemetry[2] shouldBe "observableProperty accessed"
        }
    }

    @Nested
    @DisplayName("Tests of vetoable properties")
    inner class VetoableProperties {
        @Test
        fun `It shows that vetoable properties can be used to prevent changes to a property`() {
            class TestClass {
                var vetoableProperty: String by Delegates.vetoable("initial value") {
                    property, oldValue, newValue ->
                    telemetry.add("${property.name} changed from $oldValue to $newValue")
                    newValue != "veto value"
                }
                val telemetry: MutableList<String> = mutableListOf("initialising telemetry")
            }

            val testClass = TestClass()
            testClass.telemetry.first() shouldBe "initialising telemetry"
            testClass.vetoableProperty shouldBe "initial value"
            testClass.telemetry.size shouldBe 1

            testClass.vetoableProperty = "new value"
            testClass.telemetry.size shouldBe 2
            testClass.telemetry[1] shouldBe "vetoableProperty changed from initial value to new value"
            testClass.vetoableProperty shouldBe "new value"

            testClass.vetoableProperty = "veto value"
            testClass.telemetry.size shouldBe 3
            testClass.telemetry[2] shouldBe "vetoableProperty changed from new value to veto value"
            testClass.vetoableProperty shouldBe "new value"

            testClass.vetoableProperty = "newer value"
            testClass.telemetry.size shouldBe 4
            testClass.telemetry[3] shouldBe "vetoableProperty changed from new value to newer value"
            testClass.vetoableProperty shouldBe "newer value"
        }
    }

    @Nested
    @DisplayName("Tests of map properties")
    inner class MapProperties {
        @Test
        fun `It shows that map properties can be used to access a map by key`() {
            class Number(val map: Map<String, Any?>) {
                val id: Int by map
                val en: String by map
                val mi: String by map
            }

            val number = Number(
                mapOf(
                    "id" to 1,
                    "en" to "one",
                    "mi" to "tahi"
                )
            )
            number.id shouldBe 1
            number.en shouldBe "one"
            number.mi shouldBe "tahi"
        }

        @Test
        fun `It shows that map properties can be used to access a map by key with a default value from a function`() {
            class Number(val map: Map<String, Any?>) {
                val id: Int by map
                val en: String by map
                val mi: String by map
                val ga: String by map.withDefault { key -> "unknown $key" }
            }

            val number = Number(
                mapOf(
                    "id" to 1,
                    "en" to "one",
                    "mi" to "tahi"
                )
            )
            number.id shouldBe 1
            number.en shouldBe "one"
            number.mi shouldBe "tahi"
            number.ga shouldBe "unknown ga"
        }

        @Test
        fun `It shows that map properties can be used to access a map by key with a default value from a function that throws an exception`() {
            class Number(val map: Map<String, Any?>) {
                val id: Int by map
                val en: String by map
                val mi: String by map
                val ga: String by map.withDefault { key -> throw IllegalArgumentException("unknown key $key") }
            }

            val number = Number(
                mapOf(
                    "id" to 1,
                    "en" to "one",
                    "mi" to "tahi"
                )
            )
            number.id shouldBe 1
            number.en shouldBe "one"
            number.mi shouldBe "tahi"
            shouldThrow<IllegalArgumentException> {
                number.ga
            }.message shouldBe "unknown key ga"
        }
    }

    class Number(val id: Int) {
        val mi: String by MaoriNumber()
        val en: String by EnglishNumber()
    }

    class MaoriNumber {
        operator fun getValue(thisRef: Number, property: kotlin.reflect.KProperty<*>): String {
            return when (thisRef.id) {
                1 -> "tahi"
                2 -> "rua"
                3 -> "toru"
                4 -> "wha"
                5 -> "rima"
                6 -> "ono"
                7 -> "whitu"
                8 -> "waru"
                9 -> "iwa"
                10 -> "tekau"
                else -> throw IllegalArgumentException("No Maori translation for ${thisRef.id}")
            }
        }
    }

    class EnglishNumber {
        operator fun getValue(thisRef: Number, property: kotlin.reflect.KProperty<*>): String {
            return when (thisRef.id) {
                1 -> "one"
                2 -> "two"
                3 -> "three"
                4 -> "four"
                5 -> "five"
                6 -> "six"
                7 -> "seven"
                8 -> "eight"
                9 -> "nine"
                10 -> "ten"
                else -> throw IllegalArgumentException("No English translation for ${thisRef.id}")
            }
        }
    }
}