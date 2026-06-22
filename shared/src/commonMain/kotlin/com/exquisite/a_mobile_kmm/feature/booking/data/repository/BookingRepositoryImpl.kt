package com.exquisite.a_mobile_kmm.feature.booking.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.request.RateAndReviewRequestDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetCleaningBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetCustomerBookingsResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetPestControlBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetSepticBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.GetToiletBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.data.remote.response.RateAndReviewResponseDto
import com.exquisite.a_mobile_kmm.feature.booking.domain.repository.BookingRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class BookingRepositoryImpl(private val httpClient: HttpClient) : BookingRepository {

    override suspend fun getCustomerBookings(
        customerId: Int,
        pageNo: Int,
        pageSize: Int
    ): Flow<Result<GetCustomerBookingsResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/bookings/get_customer_booking") {
                parameter("customerId", customerId)
                parameter("pageNo", pageNo)
                parameter("pageSize", pageSize)
            }
        }
    }

    override suspend fun getCleaningBooking(bookingId: Int): Flow<Result<GetCleaningBookingResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/bookings/get_cleaning_booking") {
                parameter("bookingId", bookingId)
            }
        }
    }

    override suspend fun getSepticBooking(bookingId: Int): Flow<Result<GetSepticBookingResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/bookings/get_septic_booking") {
                parameter("bookingId", bookingId)
            }
        }
    }

    override suspend fun getPestControlBooking(bookingId: Int): Flow<Result<GetPestControlBookingResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/bookings/get_pest_control_booking") {
                parameter("bookingId", bookingId)
            }
        }
    }

    override suspend fun getToiletBooking(bookingId: Int): Flow<Result<GetToiletBookingResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/bookings/get_toilet_booking") {
                parameter("bookingId", bookingId)
            }
        }
    }

    override suspend fun rateAndReview(request: RateAndReviewRequestDto): Flow<Result<RateAndReviewResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/bookings/rate_and_review") {
                setBody(request)
            }
        }
    }
}
