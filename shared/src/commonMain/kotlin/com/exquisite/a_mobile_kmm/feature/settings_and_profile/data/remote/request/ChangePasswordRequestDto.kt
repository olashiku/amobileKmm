package com.exquisite.a_mobile_kmm.feature.settings_and_profile.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequestDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val customerId: Int
)
