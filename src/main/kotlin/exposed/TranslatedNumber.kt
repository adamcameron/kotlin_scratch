package exposed

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

class TranslatedNumber(id: EntityID<Int>) : IntEntity(id) {
    object TranslatedNumbers : IntIdTable("numbers") {
        val en: Column<String> = varchar("en", 50)
        val mi: Column<String> = varchar("mi", 50)
    }
    companion object : IntEntityClass<TranslatedNumber>(TranslatedNumbers)

    var en by TranslatedNumbers.en
    var mi by TranslatedNumbers.mi
}