package junit.practical

import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
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


@DisplayName("Tests of WebService class")
internal class WebServiceTest {

    val webserviceUrl = "http://localhost:8080/numbers/"

    @Serializable
    data class Number(val id: Int, val en: String, val mi: String)

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
                response.body() as List<Number> shouldBe listOf(
                    Number(1, "one", "tahi"),
                    Number(2, "two", "rua"),
                    Number(3, "three", "toru"),
                    Number(4, "four", "wha")
                )
            }
        }
    }
    @Test
    fun `It will accept a POST request of an object as JSON and return the same object as confirmation, and its URL`() {
        val five = Number(5, "five", "rima")
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
                response.body() as Number shouldBe five
                response.headers["Location"] shouldBe "${webserviceUrl}5"
            }
        }
    }
}