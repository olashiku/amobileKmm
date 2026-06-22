package com.exquisite.a_mobile_kmm.feature.pest_control.data.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class GetPestControlPriceRequestDto(
    val numberOfRooms: Int,
    val serviceId: Int,
    val customerId: Int
)
