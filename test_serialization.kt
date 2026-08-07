import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import io.ktor.http.encodeURLParameter

fun main() {
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    // Sample order (from OrderDetailsScreen.kt)
    val sampleOrder = com.exquisite.a_mobile_kmm.feature.order.domain.model.CustomerOrder(
        order = com.exquisite.a_mobile_kmm.feature.order.domain.model.Order(
            id = 1,
            status = "Processing",
            ref = "ORD-56EE2",
            amount = 10244.00,
            taxAmount = 768.30,
            totalAmount = 13782.30,
            address = com.exquisite.a_mobile_kmm.feature.order.domain.model.Address(
                id = 1,
                address = "Thomas\n14 Asajon way, Lekki, Nigeria",
                phone = null,
                addressCode = 0,
                createdAt = "",
                updatedAt = ""
            ),
            createdAt = "2025-08-21 14:30:00",
            updatedAt = ""
        ),
        orderDetails = listOf(
            // ... simplified
        ),
        shipping = null
    )

    val jsonString = json.encodeToString(sampleOrder)
    println("JSON length: ${jsonString.length}")
    println("JSON: $jsonString")

    val encoded = jsonString.encodeURLParameter()
    println("\nEncoded length: ${encoded.length}")
    println("Encoded: ${encoded.take(200)}...")
}
