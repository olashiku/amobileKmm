package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import com.exquisite.a_mobile_kmm.core.screenUtils.to12HourFormat
import com.exquisite.a_mobile_kmm.core.screenUtils.toFormattedDate


fun getCleaningSummaryData(data: BasicCleaningBreakdownModel): List<CleaningSummaryData> {
    return listOf(
        CleaningSummaryData("Cleaning Days", data.result.selectedDaysOfWeek.map {
            it.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }.joinToString(", ")),
        CleaningSummaryData(
            "Schedule",
            "${
                data.result.allScheduledDates.first().toFormattedDate()
            } - ${data.result.allScheduledDates.last().toFormattedDate()}"
        ),
        CleaningSummaryData("Total Session", data.result.allScheduledDates.count().toString()),
        CleaningSummaryData("Session Time", data.result.timeOfDay.to12HourFormat()),
    )
}

fun getCleaningSummaryDataWithPrice(
    data: BasicCleaningBreakdownModel,
    basicCleaningFormModel: BasicCleaningFormModel,
    basicCleaningForm2Model: BasicCleaningForm2Model
): List<CleaningSummaryData> {
    return listOf(
        CleaningSummaryData("Region", basicCleaningForm2Model.region?.first?:""),
        CleaningSummaryData("Apartment Type", basicCleaningForm2Model.typeOfApartment?.first?:""),
        CleaningSummaryData("Number of rooms", basicCleaningFormModel.numberOfRooms?.first?:""),
        CleaningSummaryData("Location",basicCleaningForm2Model.location?.first?:""),
        CleaningSummaryData("Address", basicCleaningForm2Model.address),
        CleaningSummaryData("Cleaning Days", data.result.selectedDaysOfWeek.map { it.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }.joinToString(", ")),
        CleaningSummaryData("Schedule", "${data.result.allScheduledDates.first().toFormattedDate()} - ${data.result.allScheduledDates.last().toFormattedDate()}"),
        CleaningSummaryData("Total Session", data.result.allScheduledDates.count().toString()),
        CleaningSummaryData("Session Time", data.result.timeOfDay.to12HourFormat()),
        CleaningSummaryData("Number of Images", basicCleaningForm2Model.images.count().toString()),
        )
}