package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model

data class GetToiletPriceRequestModel(
    val minimumNumberOfGuest: String,
    val maximumNumberOfGuest: String,
    val serviceType: String,
    val numberOfStandardToilet: String,
    val numberOfVipToilets: String,
    val eventStartDate: String,
    val eventEndDate: String,
    val eventStartTime: String,
    val eventEndTime: String
)
