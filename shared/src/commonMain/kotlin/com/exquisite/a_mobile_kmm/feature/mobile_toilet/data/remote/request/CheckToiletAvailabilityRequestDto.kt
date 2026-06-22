package com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class CheckToiletAvailabilityRequestDto(
    val serviceType: String,
    val eventDate: String
)
