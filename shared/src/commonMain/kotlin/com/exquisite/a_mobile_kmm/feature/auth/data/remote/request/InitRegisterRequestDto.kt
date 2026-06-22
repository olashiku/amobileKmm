package com.exquisite.a_mobile_kmm.feature.auth.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class InitRegisterRequestDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val roleId: String
)