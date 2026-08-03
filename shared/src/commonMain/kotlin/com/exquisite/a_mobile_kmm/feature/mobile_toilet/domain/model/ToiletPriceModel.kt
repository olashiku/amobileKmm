package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ToiletPriceModel(
    val numberOfDays: Int,
    val discountGiven: Double,
    val overnight: Double,
    val totalNumberOfGuests: Int,
    val totalAmount: Double,
    val recommendedNumberOfStandardToilets: Int,
    val recommendedNumberOfVipToilets: Int,
    val uniqueRef: String
)
