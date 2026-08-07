package com.exquisite.a_mobile_kmm.feature.profile_and_settings.domain.model

data class EditProfileRequest(
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val customerId: Int,
    val profilePicture: String
)
