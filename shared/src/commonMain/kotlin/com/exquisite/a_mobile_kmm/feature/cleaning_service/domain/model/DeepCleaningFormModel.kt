package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import com.exquisite.a_mobile_kmm.core.screen_components.DateModel
import kotlinx.serialization.Serializable

@Serializable
data class DeepCleaningFormModel(
    val images:List<String>,
    val cleaningDate: DateModel,
    val cleaningTime:String,
    val postConstruction:Boolean,
)