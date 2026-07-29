package com.exquisite.a_mobile_kmm.feature.pest_control.domain.model

import com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model.SummaryData
import kotlinx.serialization.Serializable

@Serializable
data class PestControlResidentialFormModel(
    val selectedServiceName: String = "",
    val selectedServiceId: Int = 0,
    val selectedRoomName: String = "",
    val selectedRoomId: Int = 0
)



fun getPricingList( pestControlResidentialFormModel: PestControlResidentialFormModel): List<SummaryData> {
    return listOf(
        SummaryData("Service Type", pestControlResidentialFormModel.selectedServiceName),
        SummaryData("Number of rooms", pestControlResidentialFormModel.selectedRoomName),
      )
}