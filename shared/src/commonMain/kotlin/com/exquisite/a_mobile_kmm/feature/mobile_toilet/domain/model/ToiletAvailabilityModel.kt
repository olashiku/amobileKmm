package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ToiletAvailabilityModel(
    val availableStandardToilets: Int,
    val availableVipToilet: Int,
    val canPurchase: Boolean
)
