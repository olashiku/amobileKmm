package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import com.exquisite.a_mobile_kmm.core.screenUtils.formatToReadableDate
import kotlinx.serialization.Serializable

@Serializable
data class DeepCleaningFormData(
    val region: Pair<String, String>? = null,
    val location: Pair<String, String>? = null,
    val typeOfApartment: Pair<String, String>? = null,
    val numberOfRooms: Pair<String, String>? = null,
    val cleaningType: Pair<String, String>? = null,
    val address: Pair<String, String?>? = null
)

data class SummaryData(
    val title: String,
    val description: String
)

fun getCleaningSummaryData(data: DeepCleaningFormData): List<SummaryData> {
    return listOf(
        SummaryData("Region", data.region?.first ?: ""),
        SummaryData("Location", data.location?.first ?: ""),
        SummaryData("Apartment Type", data.typeOfApartment?.first ?: ""),
        SummaryData("Number of Rooms", data.numberOfRooms?.first ?: ""),
    )
}

fun getCheckoutSummaryData(
    data: DeepCleaningFormData,
    deepCleaningFormModel: DeepCleaningFormModel
): List<SummaryData> {
    return listOf(
        SummaryData("Region", data.region?.first ?: ""),
        SummaryData("Location", data.location?.first ?: ""),
        SummaryData("Apartment Type", data.typeOfApartment?.first ?: ""),
        SummaryData("Number of Rooms", data.numberOfRooms?.first ?: ""),
        SummaryData("Date",
            deepCleaningFormModel.cleaningDate.fullDate.formatToReadableDate()
        ),
        SummaryData("Time", deepCleaningFormModel.cleaningTime),
        SummaryData("Post Construction/Renovation?", deepCleaningFormModel.postConstruction.toString() ?: ""),
        SummaryData("Address", data.address?.first ?: "")



        )
}
