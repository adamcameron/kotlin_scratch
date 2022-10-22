class WordCollections {
    companion object {
        val listOfMaoriNumbers = listOf(
            MI.ONE.value,
            MI.TWO.value,
            MI.THREE.value,
            MI.FOUR.value,
            MI.FIVE.value,
            MI.SIX.value,
            MI.SEVEN.value,
            MI.EIGHT.value,
            MI.NINE.value,
            MI.TEN.value
        )

        val listOfMaoriColours = listOf(
            MI.RED.value,
            MI.ORANGE.value,
            MI.YELLOW.value,
            MI.GREEN.value,
            MI.BLUE.value,
            MI.INDIGO.value,
            MI.VIOLET.value
        )
        val listOfMaoriDays = listOf(
            MI.MONDAY.value,
            MI.TUESDAY.value,
            MI.WEDNESDAY.value,
            MI.THURSDAY.value,
            MI.FRIDAY.value,
            MI.SATURDAY.value,
            MI.SUNDAY.value
        )

        val mapOfEnglishAndMaoriNumbers = mapOf(
            EN.ONE.value to MI.ONE.value,
            EN.TWO.value to MI.TWO.value,
            EN.THREE.value to MI.THREE.value,
            EN.FOUR.value to MI.FOUR.value,
            EN.FIVE.value to MI.FIVE.value,
            EN.SIX.value to MI.SIX.value,
            EN.SEVEN.value to MI.SEVEN.value,
            EN.EIGHT.value to MI.EIGHT.value,
            EN.NINE.value to MI.NINE.value,
            EN.TEN.value to MI.TEN.value
        )

        val mapOfMaoriAndEnglishNumbers = mapOf(
            MI.ONE.value to EN.ONE.value,
            MI.TWO.value to EN.TWO.value,
            MI.THREE.value to EN.THREE.value,
            MI.FOUR.value to EN.FOUR.value,
            MI.FIVE.value to EN.FIVE.value,
            MI.SIX.value to EN.SIX.value,
            MI.SEVEN.value to EN.SEVEN.value,
            MI.EIGHT.value to EN.EIGHT.value,
            MI.NINE.value to EN.NINE.value,
            MI.TEN.value to EN.TEN.value
        )

        val mapOfEnglishAndMaoriColours = mapOf(
            EN.RED.value to MI.RED.value,
            EN.ORANGE.value to MI.ORANGE.value,
            EN.YELLOW.value to MI.YELLOW.value,
            EN.GREEN.value to MI.GREEN.value,
            EN.BLUE.value to MI.BLUE.value,
            EN.INDIGO.value to MI.INDIGO.value,
            EN.VIOLET.value to MI.VIOLET.value
        )

        val mapOfMaoriAndEnglishColours = mapOf(
            MI.RED.value to EN.RED.value,
            MI.ORANGE.value to EN.ORANGE.value,
            MI.YELLOW.value to EN.YELLOW.value,
            MI.GREEN.value to EN.GREEN.value,
            MI.BLUE.value to EN.BLUE.value,
            MI.INDIGO.value to EN.INDIGO.value,
            MI.VIOLET.value to EN.VIOLET.value
        )

        val mapOfEnglishAndMaoriDays = mapOf(
            EN.MONDAY.value to MI.MONDAY.value,
            EN.TUESDAY.value to MI.TUESDAY.value,
            EN.WEDNESDAY.value to MI.WEDNESDAY.value,
            EN.THURSDAY.value to MI.THURSDAY.value,
            EN.FRIDAY.value to MI.FRIDAY.value,
            EN.SATURDAY.value to MI.SATURDAY.value,
            EN.SUNDAY.value to MI.SUNDAY.value
        )

        val mapOfMaoriAndEnglishDays = mapOf(
            MI.MONDAY.value to EN.MONDAY.value,
            MI.TUESDAY.value to EN.TUESDAY.value,
            MI.WEDNESDAY.value to EN.WEDNESDAY.value,
            MI.THURSDAY.value to EN.THURSDAY.value,
            MI.FRIDAY.value to EN.FRIDAY.value,
            MI.SATURDAY.value to EN.SATURDAY.value,
            MI.SUNDAY.value to EN.SUNDAY.value
        )
    }

    enum class MaoriNumbersEnum {
        TAHI, RUA, TORU, WHA, RIMA, ONO, WHITU, WARU, IWA, TEKAU
    }

    enum class MaoriColoursEnum {
        WHERO, KARAKA, KŌWHAI, KĀKĀRIKI, KIKORANGI, TŪĀURI, PAPURA
    }

    enum class MaoriDaysEnum {
        RĀHINA, RĀTŪ, RĀAPA, RĀPARE, RĀMERE, RĀHOROI, RĀTAPU
    }

    enum class MaoriColoursByRgbEnum(val rgb: Int) {
        WHERO(0xFF0000),
        KARAKA(0xFFA500),
        KŌWHAI(0xFFFF00),
        KĀKĀRIKI(0x00FF00),
        KIKORANGI(0x0000FF),
        TŪĀURI(0x4B0082),
        PAPURA(0x800080)
    }

    enum class MI(val value: String) {
        ONE("tahi"),
        TWO("rua"),
        THREE("toru"),
        FOUR("wha"),
        FIVE("rima"),
        SIX("ono"),
        SEVEN("whitu"),
        EIGHT("waru"),
        NINE("iwa"),
        TEN("tekau"),
        RED("whero"),
        ORANGE("karaka"),
        YELLOW("kōwhai"),
        GREEN("kākāriki"),
        BLUE("kikorangi"),
        INDIGO("tūāuri"),
        VIOLET("papura"),
        MONDAY("rāhina"),
        TUESDAY("rātū"),
        WEDNESDAY("rāapa"),
        THURSDAY("rāpare"),
        FRIDAY("rāmere"),
        SATURDAY("rāhoroi"),
        SUNDAY("rātapu")
    }

    enum class EN(val value: String) {
        ONE("one"),
        TWO("two"),
        THREE("three"),
        FOUR("four"),
        FIVE("five"),
        SIX("six"),
        SEVEN("seven"),
        EIGHT("eight"),
        NINE("nine"),
        TEN("ten"),
        RED("red"),
        ORANGE("orange"),
        YELLOW("yellow"),
        GREEN("green"),
        BLUE("blue"),
        INDIGO("indigo"),
        VIOLET("violet"),
        MONDAY("Monday"),
        TUESDAY("Tuesday"),
        WEDNESDAY("Wednesday"),
        THURSDAY("Thursday"),
        FRIDAY("Friday"),
        SATURDAY("Saturday"),
        SUNDAY("Sunday")
    }
}