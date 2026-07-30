package com.exquisite.a_mobile_kmm.feature.training.domain.model

data class InitEnrollTrainingRequest(
    val trainingId: Int,
    val customerId: Int,
    val fullName: String,
    val email: String,
    val phone: String,
    val address: String,
    val gender: String
)
