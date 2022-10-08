package junit.practical

import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

@DisplayName("Tests of WebService class")
internal class WebServiceTest {

    @Test
    fun `it returns a 200`() {
        runBlocking {
            HttpClient().use { client ->
                val response = client.get("https://jsonplaceholder.typicode.com/todos/1")
                response.status shouldBe HttpStatusCode.OK
            }
        }
    }
}