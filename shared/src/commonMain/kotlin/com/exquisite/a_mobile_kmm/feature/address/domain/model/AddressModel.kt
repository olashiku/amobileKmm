package com.exquisite.a_mobile_kmm.feature.address.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AddressModel(
    val id: Int = 0,
    val phone: String = "",
    val address: String= "",
    val createdAt: String= "",
    val updatedAt: String= "",
    var isSelected: Boolean = false
)
