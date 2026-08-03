package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model

import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import kotlinx.serialization.Serializable

@Serializable
data class MobileToiletEventFormOneFormData(
    var eventDate : DateModel? = null,
    var selectedCleaningOption :String = "standard"
)