package com.exquisite.a_mobile_kmm.feature.profile_and_settings.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequestDto(
    val customerId: Int,
    val oldPassword: String,
    val newPassword: String
)
