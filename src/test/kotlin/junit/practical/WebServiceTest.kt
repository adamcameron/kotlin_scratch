package junit.practical

import exposed.TranslatedNumber
import io.kotest.assertions.asClue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.withClue
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.condition.EnabledIf
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import java.net.ConnectException

@DisplayName("Tests of WebService class")
@EnabledIf("isUp")
internal class WebServiceTest {

    private val webserviceUrl = webServiceBaseUrl + "numbers/"

    @Serializable
    data class SerializableNumber(val id: Int, val en: String, val mi: String)

    companion object {
        const val webServiceBaseUrl = "http://localhost:8080/"
        @JvmStatic
        fun isUp(): Boolean {
            return try {
                runBlocking {
                    HttpClient().use { client ->
                        client.head(webServiceBaseUrl)
                    }
                }
                true
            } catch (e: ConnectException) {
                false
            }
        }
    }

    @Test
    fun `It should return a 200-OK on the root URI on a valid request`() {
        runBlocking {
            HttpClient().use { client ->
                val response = client.get(webserviceUrl) {
                    header("Accept", "application/json")
                }

                response.status shouldBe HttpStatusCode.OK
            }
        }
    }
    @Test
    fun `It should return a 406-NOT-ACCEPTABLE and suggest the expected type if the Accepts header is not application-json`() {
        runBlocking {
            HttpClient().use { client ->
                val response = client.get(webserviceUrl) {
                    header("Accept", "text/plain")
                }

                response.status shouldBe HttpStatusCode.NotAcceptable
                response.body() as String shouldBe """["application/json"]"""
            }
        }
    }
    @Test
    fun `It returns an array of Numbers as a JSON array`() {
        runBlocking {
            HttpClient() {
                install(ContentNegotiation) {
                    json()
                }
            }.use { client ->
                val response = client.get(webserviceUrl) {
                    header("Accept", "application/json")
                }

                response.status shouldBe HttpStatusCode.OK
                response.body() as List<SerializableNumber> shouldBe listOf(
                    SerializableNumber(1, "one", "tahi"),
                    SerializableNumber(2, "two", "rua"),
                    SerializableNumber(3, "three", "toru"),
                    SerializableNumber(4, "four", "wha")
                )
            }
        }
    }
    @Test
    fun `It will accept a POST request of an object as JSON and return the same object as confirmation, and its URL`() {
        val five = SerializableNumber(5, "five", "rima")
        runBlocking {
            HttpClient() {
                install(ContentNegotiation) {
                    json()
                }
            }.use { client ->
                val response = client.post(webserviceUrl) {
                    contentType(ContentType.Application.Json)
                    setBody(five)
                }

                response.status shouldBe HttpStatusCode.Created
                response.body() as SerializableNumber shouldBe five

                withClue("The Location header should be the URL of the new resource") {
                    response.headers["Location"] shouldBe "$webserviceUrl${five.id}"
                }
            }
        }
    }

    @Test
    @EnabledIfEnvironmentVariable(
        named = "MARIADB_PASSWORD",
        matches = ".*",
        disabledReason = "This test requires a MariaDB database, so it needs the password"
    )
    fun `It saves the new object to the database`() {
        val six = SerializableNumber(6, "six", "ono")
        runBlocking {
            HttpClient() {
                install(ContentNegotiation) {
                    json()
                }
            }.use { client ->
                val response = client.post(webserviceUrl) {
                    contentType(ContentType.Application.Json)
                    setBody(six)
                }

                response.status shouldBe HttpStatusCode.Created

            }
            Database.connect(
                "jdbc:mysql://localhost:3308/db1",
                driver = "com.mysql.cj.jdbc.Driver",
                user = "user1",
                password = System.getenv("MARIADB_PASSWORD")
            )
            transaction {
                addLogger(StdOutSqlLogger)

                TranslatedNumber.findById(six.id).asClue {
                    it shouldNotBe null
                    it!!.en shouldBe six.en
                    it.mi shouldBe six.mi
                }
            }
        }
    }
}
