package com.exquisite.a_mobile_kmm.core.network

import coil3.network.HttpException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

/**
 * Extension function to convert throwables from API calls into user-friendly error messages
 * Works with Result.Exception(val exception: Throwable)
 */
fun Throwable.handleException(): String {
    println("Exception occurred: ${this::class.simpleName} - ${this.message}")

    return when (this) {
        // Network connectivity issues
        is UnresolvedAddressException -> "No internet connection. Please check your network settings."

        is ConnectTimeoutException -> "Connection timeout. Please check your internet connection and try again."

        is SocketTimeoutException -> "Request timed out. Please try again."

        // HTTP status code exceptions (Ktor specific)
        is RedirectResponseException -> {
            // 3xx responses
            "Redirect error: ${this.response.status.description}"
        }


        is ClientRequestException -> {
            // 4xx responses
            when (this.response.status.value) {
                400 -> "Bad request. Please check your input."
                401 -> "Unauthorized. Please login again."
                403 -> "Access forbidden. You don't have permission to access this resource."
                404 -> "Resource not found."
                408 -> "Request timeout. Please try again."
                429 -> "Too many requests. Please try again later."
                else -> "Client error: ${this.response.status.description}"
            }
        }

        is ServerResponseException -> {
            // 5xx responses
            when (this.response.status.value) {
                500 -> "Internal server error. Please try again later."
                502 -> "Bad gateway. Please try again later."
                503 -> "Service unavailable. Please try again later."
                504 -> "Gateway timeout. Please try again later."
                else -> "Server error: ${this.response.status.description}"
            }
        }

        // General HTTP exception (from Coil3)
        is HttpException -> "Network error. Please check your internet connection."

        // JSON serialization/deserialization errors
        is SerializationException -> "Data format error. Please try again or contact support."

        // Null pointer or illegal state
        is NullPointerException -> "Unexpected error occurred. Please try again."

        is IllegalStateException -> "Invalid state: ${this.message ?: "Please try again"}"

        is IllegalArgumentException -> "Invalid data: ${this.message ?: "Please check your input"}"
        // Generic fallback

        else -> {
            println("exception_message ${this.message}")
            if(this.message?.contains("connect", ignoreCase = true) == true || message?.contains("UnknownHostException",ignoreCase = true) == true){
                "Please check your internet connection."
            }else{
                "Something went wrong. Please try again."
            }
        }
    }
}

/**
 * Extension function to get a shorter, more concise error message
 * Works with Result.Exception(val exception: Throwable)
 */
fun Throwable.handleExceptionShort(): String {
    return when (this) {
        is UnresolvedAddressException, is ConnectTimeoutException -> "Kindly check your internet connection"
        is SocketTimeoutException -> "Request timed out"
        is ClientRequestException -> when (this.response.status.value) {
            401 -> "Please login again"
            403 -> "Access forbidden"
            404 -> "Not found"
            else -> "Request failed"
        }

        is ServerResponseException -> "Server error"
        is SerializationException -> "Data error"
        else -> "Something went wrong"
    }
}