package com.exquisite.a_mobile_kmm.feature.cleaners_registration.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterCleanerRequestDto(
    val customerId: Int,
    val fullName: String,
    val email: String,
    val phone: String,
    val address: String,
    val resumeUrl: String,
    val pictureUrl: List<String>,
    val gender: String,
    val employmentStatus: String
)
