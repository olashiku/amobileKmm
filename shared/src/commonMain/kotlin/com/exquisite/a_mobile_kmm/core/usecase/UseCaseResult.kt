package com.exquisite.a_mobile_kmm.core.usecase

sealed class UseCaseResult<T>{
    data class Success<T>(val data: T) : UseCaseResult<T>()
    data class Error<T>(val message: String) : UseCaseResult<T>()
}