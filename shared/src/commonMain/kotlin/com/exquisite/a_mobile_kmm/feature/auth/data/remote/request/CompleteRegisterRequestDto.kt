package com.exquisite.a_mobile_kmm.feature.auth.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CompleteRegisterRequestDto(
    val password: String,
    val uniqueRef: String,
    val otp: String,
    val profilePicture: String
)
