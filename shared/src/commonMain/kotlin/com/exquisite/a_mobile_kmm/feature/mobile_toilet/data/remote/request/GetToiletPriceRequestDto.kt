package com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class GetToiletPriceRequestDto(
    val minimumNumberOfGuest: String,
    val maximumNumberOfGuest: String,
    val serviceType: String,
    val numberOfStandardToilet: String,
    val numberOfVipToilets: String,
    val eventStartDate: String,
    val eventEndDate: String,
    val eventStartTIme: String,
    val eventEndTIme: String
)
