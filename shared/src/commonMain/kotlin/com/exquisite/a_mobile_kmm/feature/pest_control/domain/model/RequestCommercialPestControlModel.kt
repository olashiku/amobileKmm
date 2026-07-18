package com.exquisite.a_mobile_kmm.feature.pest_control.domain.model

data class RequestCommercialPestControlModel(
    val customerId: Int,
    val companyName: String,
    val companyEmail: String,
    val companyAddress: String,
    val availabilityDate: String,
    val availabilityTime: String,
    val recipientName: String,
    val recipientEmail: String,
    val recipientPhone: String
)
