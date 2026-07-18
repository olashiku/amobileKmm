package com.exquisite.a_mobile_kmm.feature.pest_control.domain.model

import com.exquisite.a_mobile_kmm.core.screen_components.DateModel

data class PestControlCommercialModel(
    val companyName: String = "",
    val companyEmail: String = "",
    val address: String = "",
    val selectedDate: DateModel? = null,
    val selectedTime: String? = null,
    val recipientName: String = "",
    val recipientEmail: String = "",
    val recipientPhone: String = ""
)
