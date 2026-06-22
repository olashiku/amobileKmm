package com.exquisite.a_mobile_kmm.feature.booking.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class RateAndReviewRequestDto(
    val serviceType: String,
    val comment: String,
    val rate: String,
    val customerId: String,
    val bookingId: String
)
