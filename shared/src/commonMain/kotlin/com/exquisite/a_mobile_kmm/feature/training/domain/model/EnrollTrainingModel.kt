package com.exquisite.a_mobile_kmm.feature.training.domain.model

data class InitEnrollTrainingModel(
    val ref: String,
    val paymentLink: String
)

data class EnrollmentSuccessModel(
    val message: String
)
