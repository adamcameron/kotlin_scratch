package junit.language.classes.fixtures.package2

import junit.language.classes.fixtures.package1.*

class SomeImplementation : SomeInterface {
    fun someMethod(): String {
        return "Package2.someMethod"
    }
}

//class SomeSealedImplementation(val property: String) : SomeSealedInterface