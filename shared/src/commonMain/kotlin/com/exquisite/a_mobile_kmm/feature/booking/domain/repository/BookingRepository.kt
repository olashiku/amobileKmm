package com.exquisite.a_mobile_kmm.feature.booking.domain.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.request.RateAndReviewRequestDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetCleaningBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetCustomerBookingsResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetPestControlBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetSepticBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetToiletBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.RateAndReviewResponseDto
import kotlinx.coroutines.flow.Flow

interface BookingRepository {
    suspend fun getCustomerBookings(customerId: Int, pageNo: Int, pageSize: Int): Flow<Result<GetCustomerBookingsResponseDto>>
    suspend fun getCleaningBooking(bookingId: Int): Flow<Result<GetCleaningBookingResponseDto>>
    suspend fun getSepticBooking(bookingId: Int): Flow<Result<GetSepticBookingResponseDto>>
    suspend fun getPestControlBooking(bookingId: Int): Flow<Result<GetPestControlBookingResponseDto>>
    suspend fun getToiletBooking(bookingId: Int): Flow<Result<GetToiletBookingResponseDto>>
    suspend fun rateAndReview(request: RateAndReviewRequestDto): Flow<Result<RateAndReviewResponseDto>>
}
