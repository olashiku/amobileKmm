package com.exquisite.a_mobile_kmm.feature.auth.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class InitForgotPasswordRequestDto(
    val email: String
)
