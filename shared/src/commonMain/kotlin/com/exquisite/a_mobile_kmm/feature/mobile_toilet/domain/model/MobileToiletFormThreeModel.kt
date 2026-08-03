package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MobileToiletFormThreeModel(
    val companyName: String = "",
    val companyEmail: String = "",
    val contactName: String = "",
    val contactPhone: String = "",
    val contactEmail: String = "",
    val eventType: String = "",
    val address: String = "",
    val additionalMessage: String = "",
    val eventLocationImages: List<String> = emptyList(),
    val toiletPlacementImages: List<String> = emptyList()
)
