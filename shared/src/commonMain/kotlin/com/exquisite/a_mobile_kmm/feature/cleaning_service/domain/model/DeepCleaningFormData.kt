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

data class CleaningSummaryData(
    val title: String,
    val description: String
)

fun getCleaningSummaryData(data: DeepCleaningFormData): List<CleaningSummaryData> {
    return listOf(
        CleaningSummaryData("Region", data.region?.first ?: ""),
        CleaningSummaryData("Location", data.location?.first ?: ""),
        CleaningSummaryData("Apartment Type", data.typeOfApartment?.first ?: ""),
        CleaningSummaryData("Number of Rooms", data.numberOfRooms?.first ?: ""),
    )
}

fun getCheckoutSummaryData(
    data: DeepCleaningFormData,
    deepCleaningFormModel: DeepCleaningFormModel
): List<CleaningSummaryData> {
    return listOf(
        CleaningSummaryData("Region", data.region?.first ?: ""),
        CleaningSummaryData("Location", data.location?.first ?: ""),
        CleaningSummaryData("Apartment Type", data.typeOfApartment?.first ?: ""),
        CleaningSummaryData("Number of Rooms", data.numberOfRooms?.first ?: ""),
        CleaningSummaryData("Date",
            deepCleaningFormModel.cleaningDate.fullDate.formatToReadableDate()
        ),
        CleaningSummaryData("Time", deepCleaningFormModel.cleaningTime),
        CleaningSummaryData("Post Construction/Renovation?", deepCleaningFormModel.postConstruction.toString() ?: ""),
        CleaningSummaryData("Address", data.address?.first ?: "")



        )
}
