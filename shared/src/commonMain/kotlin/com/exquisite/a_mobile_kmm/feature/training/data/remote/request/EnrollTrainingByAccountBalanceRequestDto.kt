package com.exquisite.a_mobile_kmm.feature.training.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class EnrollTrainingByAccountBalanceRequestDto(
    val trainingId: Int,
    val customerId: Int,
    val fullName: String,
    val email: String,
    val phone: String,
    val address: String,
    val gender: String
)
