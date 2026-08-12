package com.exquisite.a_mobile_kmm.feature.employee.domain.model

enum class BookingUpdateType(val value: String) {
    CLOCK_IN("CLOCK_IN"),
    CLOCK_OUT("CLOCK_OUT"),
    START_SERVICE("START_SERVICE"),
    COMPLETE_SERVICE("COMPLETE_SERVICE"),
    CANCEL_SERVICE("CANCEL_SERVICE")
}

data class UpdateAgentBookingRequest(
    val employeeId: Int,
    val bookingId: Int,
    val updateType: BookingUpdateType,
    val agentRemark: String
)

data class UpdateAgentBookingResult(
    val isSuccess: Boolean,
    val message: String
)
