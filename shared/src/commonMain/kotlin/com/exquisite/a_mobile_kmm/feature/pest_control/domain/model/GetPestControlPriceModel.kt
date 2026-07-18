package com.exquisite.a_mobile_kmm.feature.pest_control.domain.model

data class GetPestControlPriceModel(
    val numberOfRooms: Int,
    val serviceId: Int,
    val customerId: Int
)
