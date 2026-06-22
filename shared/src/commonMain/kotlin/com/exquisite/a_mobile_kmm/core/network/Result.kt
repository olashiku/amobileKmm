package com.exquisite.a_mobile_kmm.core.network

sealed class Result<out T> {
    class Success<out T>(val data: T) : Result<T>()
    class Exception(val exception: Throwable) : Result<Nothing>()
}