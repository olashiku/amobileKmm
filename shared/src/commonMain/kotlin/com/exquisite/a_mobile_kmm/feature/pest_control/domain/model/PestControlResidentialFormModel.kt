package com.exquisite.a_mobile_kmm.feature.pest_control.domain.model

import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.CleaningSummaryData
import kotlinx.serialization.Serializable

@Serializable
data class PestControlResidentialFormModel(
    val selectedServiceName: String = "",
    val selectedServiceId: Int = 0,
    val selectedRoomName: String = "",
    val selectedRoomId: Int = 0
)



fun getPricingList( pestControlResidentialFormModel: PestControlResidentialFormModel): List<CleaningSummaryData> {
    return listOf(
        CleaningSummaryData("Service Type", pestControlResidentialFormModel.selectedServiceName),
        CleaningSummaryData("Number of rooms", pestControlResidentialFormModel.selectedRoomName),
      )
}