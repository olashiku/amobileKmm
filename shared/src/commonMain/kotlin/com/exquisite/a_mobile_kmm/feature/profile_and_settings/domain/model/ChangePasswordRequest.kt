package com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model

data class ChangePasswordRequest(
    val customerId: Int,
    val oldPassword: String,
    val newPassword: String
)
