package com.exquisite.a_mobile_kmm.feature.cleaning_service.domain.model

import com.exquisite.a_mobile_kmm.core.screenUtils.to12HourFormat
import com.exquisite.a_mobile_kmm.core.screenUtils.toFullDateFormat


fun getCleaningSummaryData(data: BasicCleaningBreakdownModel): List<CleaningSummaryData> {
    return listOf(
        CleaningSummaryData("Cleaning Days", data.result.selectedDaysOfWeek.joinToString(", ")),
        CleaningSummaryData("Schedule",  "${data.result.allScheduledDates.first().toFullDateFormat()} - ${data.result.allScheduledDates.last().toFullDateFormat()}"),
        CleaningSummaryData("Total Session", data.result.allScheduledDates.count().toString()),
        CleaningSummaryData("Session Time", data.result.timeOfDay.to12HourFormat()),
)
}