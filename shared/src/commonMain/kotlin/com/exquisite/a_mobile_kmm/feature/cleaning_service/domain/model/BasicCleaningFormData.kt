package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import com.exquisite.a_mobile_kmm.core.screenUtils.formatToReadableDate
import kotlinx.serialization.Serializable

@Serializable
data class BasicCleaningFormData(
    val numberOfRooms: Pair<String, String>? = null)

