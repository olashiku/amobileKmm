package com.exquisite.a_mobile_kmm.feature.booking.domain.model

data class RateAndReviewRequest(
    val serviceType: String,
    val comment: String,
    val rate: Int,
    val customerId: Int,
    val bookingId: Int
)
