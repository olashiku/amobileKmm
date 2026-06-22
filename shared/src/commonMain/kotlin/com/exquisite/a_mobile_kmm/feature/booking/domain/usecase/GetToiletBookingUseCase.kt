package com.exquisite.a_mobile_kmm.feature.booking.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.booking.data.mapper.toToiletBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.ToiletBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.repository.BookingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetToiletBookingUseCase(private val bookingRepository: BookingRepository) {

    suspend operator fun invoke(bookingId: Int): Flow<UseCaseResult<ToiletBookingModel>> {
        return bookingRepository.getToiletBooking(bookingId).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        val toiletBookingModel = result.data.toToiletBookingModel()
                        if (toiletBookingModel != null) {
                            UseCaseResult.Success(toiletBookingModel)
                        } else {
                            UseCaseResult.Error("Invalid toilet booking response data")
                        }
                    } else {
                        UseCaseResult.Error(result.data.responseMessage)
                    }
                }
                is Result.Exception -> {
                    UseCaseResult.Error(result.exception.handleException())
                }
            }
        }.catch { exception ->
            emit(UseCaseResult.Error(exception.handleException()))
        }.flowOn(Dispatchers.IO)
    }
}
