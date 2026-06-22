package com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestCommercialPestControlRequestDto(
    val customerId: Int,
    val companyName: String,
    val companyEmail: String,
    val companyAddress: String,
    val availabilityDate: String,
    val availabilityTime: String,
    val resumptionTime: String,
    val buildingImage: List<String>
)
