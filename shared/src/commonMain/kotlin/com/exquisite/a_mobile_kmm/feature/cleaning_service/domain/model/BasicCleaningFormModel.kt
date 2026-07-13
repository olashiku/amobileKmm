package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BasicCleaningFormModel(
    val numberOfRooms: Pair<String,String> = Pair("",""),
    val cleaningTime: String = "",
    val cleaningDate: List<String> = emptyList()

)