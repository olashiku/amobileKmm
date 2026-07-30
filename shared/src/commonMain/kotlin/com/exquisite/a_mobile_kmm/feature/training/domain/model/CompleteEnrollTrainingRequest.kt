package com.exquisite.a_mobile_kmm.feature.training.domain.model

data class CompleteEnrollTrainingRequest(
    val customerId: Int,
    val ref: String,
    val txnRef: String
)
