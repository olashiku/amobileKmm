package com.exquisite.a_mobile_kmm.feature.pest_control.domain.model

import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import kotlinx.serialization.Serializable

@Serializable
data class ResidentialPestControlFormTwoModel(
    val address: String = "",
    val images: List<String> = emptyList(),
    val inspectionDate: DateModel? = null,
    val inspectionTime: String? = null,
    val serviceDate: DateModel? = null,
    val serviceTime: String? = null,
    val extraNote: String = "",
    val hasPestInVehicle: Boolean = false,
    val numberOfVehicles: String = "",
    val wantsHotFogging: Boolean = false,
)