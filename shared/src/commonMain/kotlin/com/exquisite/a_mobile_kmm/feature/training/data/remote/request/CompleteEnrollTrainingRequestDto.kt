package com.exquisite.a_mobile_kmm.feature.training.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CompleteEnrollTrainingRequestDto(
    val customerId: Int,
    val ref: String,
    val txnRef: String
)
