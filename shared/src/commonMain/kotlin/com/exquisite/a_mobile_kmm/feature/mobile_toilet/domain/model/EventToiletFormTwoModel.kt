package com.exquisite.a_mobile_kmm.feature.mobile_toilet.domain.model

import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import kotlinx.serialization.Serializable


@Serializable
data class EventToiletFormTwoModel(
    var numberOfStandardToilet: String = "",
    var numberOfVipToilet:String = "",
    var numberOfGuest:String = "",
    var eventStartDate: DateModel? = null,
    var eventStartTime:String? = null,
    var eventEndDate: DateModel? = null,
    var eventEndTime:String? = null,
)
