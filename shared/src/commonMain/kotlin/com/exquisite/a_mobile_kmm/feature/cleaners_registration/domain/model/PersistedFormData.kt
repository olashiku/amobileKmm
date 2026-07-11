package com.exquisite.a_mobile_kmm.feature.cleaners_registration.domain.model

data class PersistedFormData(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val gender: String = "",
    val employmentStatus: String = "",
    val experienceLevel: String = ""
)