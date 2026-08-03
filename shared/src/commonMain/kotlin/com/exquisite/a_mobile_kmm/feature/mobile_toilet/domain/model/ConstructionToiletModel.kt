package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model

import com.exquisite.a_mobile_kmm.core.screen_components.DateModel

data class ConstructionToiletModel(
    val customerId: Int = 0,
    val companyName: String = "",
    val companyEmail: String= "",
    val constructionAddress: String= "",
    val availabilityDate: DateModel?= null,
    val availabilityTime: String?= null,
    val recipientName: String= "",
    val recipientEmail: String= "",
    val recipientPhone: String= "",
    val numberOfPeopleOnSite: String= "",
    val numberOfMonths: String= ""
)
