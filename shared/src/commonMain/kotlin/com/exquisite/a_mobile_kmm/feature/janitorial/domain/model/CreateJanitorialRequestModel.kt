package com.exquisite.a_mobile_kmm.feature.janitorial.domain.model

data class CreateJanitorialRequestModel(
    val customerId: Int,
    val companyName: String,
    val companyEmail: String,
    val companyAddress: String,
    val availabilityDate: String,
    val availabilityTime: String,
    val resumptionTime: String,
    val buildingImage: List<String>,
    val phoneNo:String
)
