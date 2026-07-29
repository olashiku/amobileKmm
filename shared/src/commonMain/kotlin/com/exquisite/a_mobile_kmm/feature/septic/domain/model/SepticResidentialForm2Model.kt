package com.exquisite.a_mobile_kmm.feature.septic.domain.model

import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import kotlinx.serialization.Serializable

@Serializable
data class SepticResidentialForm2Model(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val selectedDate: DateModel? = null,
    val selectedTime: String? = null,
    val additionalMessage: String = ""
)
