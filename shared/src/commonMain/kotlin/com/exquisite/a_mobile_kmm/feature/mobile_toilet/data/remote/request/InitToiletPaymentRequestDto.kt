package com.exquisite.a_mobile_kmm.feature.mobile_toilet.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class InitToiletPaymentRequestDto(
    val uniqueRef: String,
    val contactPersonName: String,
    val contactPersonPhone: String,
    val contactPersonEmail: String,
    val address: String,
    val typeOfEvent: String,
    val extraNote: String,
    val customerId: Int,
    val pictureOfEventLocation: List<String>,
    val pictureOfToiletPlacement: List<String>,
    val companyName: String,
    val companyEmail: String
)
