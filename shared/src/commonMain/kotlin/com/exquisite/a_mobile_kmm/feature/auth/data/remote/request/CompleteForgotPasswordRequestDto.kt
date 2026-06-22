package com.exquisite.a_mobile_kmm.feature.auth.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CompleteForgotPasswordRequestDto(
    val uniqueRef: String,
    val password: String,
    val otp: String
)
