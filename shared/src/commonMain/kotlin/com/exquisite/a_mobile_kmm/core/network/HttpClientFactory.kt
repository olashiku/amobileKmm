package com.exquisite.a_mobile_kmm.core.network

import com.exquisite.a_mobile_kmm.core.database.datastore.AMobileDataStore
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.plugin
import io.ktor.client.request.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun createHttpClient(
        engine: HttpClientEngine,
        baseUrl: String,
        dataStore: AMobileDataStore
    ): HttpClient {


        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(HttpTimeout) {
                connectTimeoutMillis = ApiConfig.CONNECT_TIMEOUT
                requestTimeoutMillis = ApiConfig.REQUEST_TIMEOUT
                socketTimeoutMillis = ApiConfig.SOCKET_TIMEOUT
            }
            install(HttpCache)
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            install(HttpRequestRetry)


            defaultRequest {
                url(baseUrl)
                headers {
                    append("Accept", "application/json")
                }
                contentType(ContentType.Application.Json)
            }
        }.also { httpClient ->
            httpClient.plugin(HttpSend).intercept { request ->
                val token = dataStore.getAuthorization().first()
                request.headers.append("Authorization", "Bearer $token")
                execute(request)
            }
        }
    }

    /**
     * Creates an HTTP client with extended timeout settings for long-running operations.
     * This is specifically designed for operations like the Google Virtual Try-On API
     * which can take up to 90 seconds to respond.
     */
    fun createExtendedTimeoutHttpClient(engine: HttpClientEngine, baseUrl: String
                                     , dataStore: AMobileDataStore
    ): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(HttpTimeout) {
                connectTimeoutMillis = ApiConfig.CONNECT_TIMEOUT
                requestTimeoutMillis = ApiConfig.EXTENDED_REQUEST_TIMEOUT
                socketTimeoutMillis = ApiConfig.EXTENDED_SOCKET_TIMEOUT
            }
            install(HttpCache)
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            defaultRequest {
                url(baseUrl)
                headers { append("Accept", "application/json") }
                contentType(ContentType.Application.Json)
            }
        }.also { httpClient ->
            httpClient.plugin(HttpSend).intercept { request ->
                val token = dataStore.getAuthorization().first()
               request.headers.append("Authorization", "Bearer $token")
                execute(request)
            }
        }
    }


    /**
     * Creates an HTTP client with extended timeout settings for long-running operations.
     * This is specifically designed for operations like the Google Virtual Try-On API
     * which can take up to 90 seconds to respond.
     */
    fun createHttpClientFullUtl(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(HttpTimeout) {
                connectTimeoutMillis = ApiConfig.CONNECT_TIMEOUT
                requestTimeoutMillis = ApiConfig.EXTENDED_REQUEST_TIMEOUT
                socketTimeoutMillis = ApiConfig.EXTENDED_SOCKET_TIMEOUT
            }
            install(HttpCache)
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            defaultRequest {
                headers { append("Accept", "application/json") }
                contentType(ContentType.Application.Json)
            }
        }
    }


}