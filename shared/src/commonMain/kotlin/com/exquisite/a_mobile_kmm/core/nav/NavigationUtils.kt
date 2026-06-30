package com.exquisite.a_mobile_kmm.core.nav

import io.ktor.http.encodeURLParameter
import io.ktor.http.decodeURLQueryComponent
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

object NavigationUtils {
    @PublishedApi
    internal val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    inline fun <reified T> encodeObject(obj: T): String {
        val jsonString = json.encodeToString(serializer<T>(), obj)
        return jsonString.encodeURLParameter()
    }

    inline fun <reified T> decodeObject(encodedString: String): T {
        val decodedString = encodedString.decodeURLQueryComponent()
        return json.decodeFromString(serializer<T>(), decodedString)
    }
}
