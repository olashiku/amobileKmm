package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import com.exquisite.a_mobile_kmm.core.screen_components.RadioOption
import kotlinx.serialization.Serializable

@Serializable
data class CleaningTypeModel(
    val id: Int,
    val name: String
)

val cleaningType = listOf(
    RadioOption(
        id = "2",
        title = "Just moving into the apartment"),
    RadioOption(
        id = "1",
        title = "Already living in the apartment")
)
