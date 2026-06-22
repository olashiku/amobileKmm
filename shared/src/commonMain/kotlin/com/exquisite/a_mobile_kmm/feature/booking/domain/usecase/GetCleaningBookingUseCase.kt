package com.exquisite.a_mobile_kmm.feature.booking.domain.usecase

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.handleException
import com.exquisite.a_mobile_kmm.core.usecase.UseCaseResult
import com.exquisite.a_mobile_kmm.feature.booking.data.mapper.toCleaningBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.model.CleaningBookingModel
import com.exquisite.a_mobile_kmm.feature.booking.domain.repository.BookingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetCleaningBookingUseCase(private val bookingRepository: BookingRepository) {

    suspend operator fun invoke(bookingId: Int): Flow<UseCaseResult<CleaningBookingModel>> {
        return bookingRepository.getCleaningBooking(bookingId).map { result ->
            when (result) {
                is Result.Success -> {
                    if (result.data.responseCode == "00") {
                        val cleaningBookingModel = result.data.toCleaningBookingModel()
                        if (cleaningBookingModel != null) {
                            UseCaseResult.Success(cleaningBookingModel)
                        } else {
                            UseCaseResult.Error("Invalid cleaning booking response data")
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
