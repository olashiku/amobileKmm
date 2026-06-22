package com.exquisite.a_mobile_kmm.core.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

suspend inline fun <reified T> safeApiCall(
    crossinline apiCall: suspend () -> HttpResponse
): Flow<Result<T>> = withContext(Dispatchers.IO) {
    try {
            flow {
                emit(  Result.Success(apiCall().body<T>()))
            }

    } catch (ex: Exception) {
        flow {
            emit( Result.Exception(ex))
        }
    }
}