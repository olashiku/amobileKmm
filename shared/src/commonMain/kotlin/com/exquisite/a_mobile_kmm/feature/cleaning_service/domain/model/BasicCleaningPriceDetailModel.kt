package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import com.exquisite.a_mobile_kmm.core.screenUtils.to12HourFormat
import com.exquisite.a_mobile_kmm.core.screenUtils.toFormattedDate


fun getCleaningSummaryData(data: BasicCleaningBreakdownModel): List<SummaryData> {
    return listOf(
        SummaryData("Cleaning Days", data.result.selectedDaysOfWeek.map {
            it.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
        }.joinToString(", ")),
        SummaryData(
            "Schedule",
            "${
                data.result.allScheduledDates.first().toFormattedDate()
            } - ${data.result.allScheduledDates.last().toFormattedDate()}"
        ),
        SummaryData("Total Session", data.result.allScheduledDates.count().toString()),
        SummaryData("Session Time", data.result.timeOfDay.to12HourFormat()),
    )
}

fun getCleaningSummaryDataWithPrice(
    data: BasicCleaningBreakdownModel,
    basicCleaningFormModel: BasicCleaningFormModel,
    basicCleaningForm2Model: BasicCleaningForm2Model
): List<SummaryData> {
    return listOf(
        SummaryData("Region", basicCleaningForm2Model.region?.first?:""),
        SummaryData("Apartment Type", basicCleaningForm2Model.typeOfApartment?.first?:""),
        SummaryData("Number of rooms", basicCleaningFormModel.numberOfRooms?.first?:""),
        SummaryData("Location",basicCleaningForm2Model.location?.first?:""),
        SummaryData("Address", basicCleaningForm2Model.address),
        SummaryData("Cleaning Days", data.result.selectedDaysOfWeek.map { it.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }.joinToString(", ")),
        SummaryData("Schedule", "${data.result.allScheduledDates.first().toFormattedDate()} - ${data.result.allScheduledDates.last().toFormattedDate()}"),
        SummaryData("Total Session", data.result.allScheduledDates.count().toString()),
        SummaryData("Session Time", data.result.timeOfDay.to12HourFormat()),
        SummaryData("Number of Images", basicCleaningForm2Model.images.count().toString()),
        )
}