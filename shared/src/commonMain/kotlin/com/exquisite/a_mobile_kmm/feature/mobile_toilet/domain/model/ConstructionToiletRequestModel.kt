package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model

data class ConstructionToiletRequestModel(
    val customerId: Int = 0,
    val companyName: String = "",
    val companyEmail: String= "",
    val constructionAddress: String= "",
    val availabilityDate: String= "",
    val availabilityTime: String= "",
    val recipientName: String= "",
    val recipientEmail: String= "",
    val recipientPhone: String= "",
    val numberOfPeopleOnSite: String= "",
    val numberOfMonths: String= ""
)
