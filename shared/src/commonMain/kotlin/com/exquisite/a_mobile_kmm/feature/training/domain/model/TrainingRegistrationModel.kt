package com.exquisite.a_mobile_kmm.feature.training.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class TrainingRegistrationModel(
    val fullName: String = "",
    val email: String= "",
    val phone: String= "",
    val address: String= "",
    val gender: String= "",
)