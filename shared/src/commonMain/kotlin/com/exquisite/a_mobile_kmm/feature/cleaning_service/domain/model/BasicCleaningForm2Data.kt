package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import com.exquisite.a_mobile_kmm.core.screenUtils.formatToReadableDate
import kotlinx.serialization.Serializable

@Serializable
data class BasicCleaningForm2Model(
    val region: Pair<String, String>? = null,
    val location: Pair<String, String>? = null,
    val typeOfApartment: Pair<String, String>? = null,
    val address: String = "",
    val images:List<String> = emptyList()
)

