package junit.language.classes.fixtures.package1

interface SomeInterface

sealed interface SomeSealedInterface

class SomeSealedImplementation(val property: String) : SomeSealedInterface {
    fun useThisMethod(): String {
        return property
    }
}

class SomeOtherSealedImplementation(val property: String) : SomeSealedInterface {
    fun useThisOtherMethod(): String {
        return property
    }
}
