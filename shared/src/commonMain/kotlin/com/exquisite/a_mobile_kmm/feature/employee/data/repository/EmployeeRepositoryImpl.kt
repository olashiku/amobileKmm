package com.exquisite.a_mobile_kmm.feature.employee.data.repository

import com.exquisite.a_mobile_kmm.core.network.Result
import com.exquisite.a_mobile_kmm.core.network.safeApiCall
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.request.UpdateAgentBookingRequestDto
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.response.GetAgentBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.response.GetAgentServiceCountsResponseDto
import com.exquisite.a_mobile_kmm.feature.employee.data.remote.response.UpdateAgentBookingResponseDto
import com.exquisite.a_mobile_kmm.feature.employee.domain.repository.EmployeeRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.flow.Flow

class EmployeeRepositoryImpl(private val httpClient: HttpClient) : EmployeeRepository {

    override suspend fun getAgentServiceCounts(agentId: Int): Flow<Result<GetAgentServiceCountsResponseDto>> {
        return safeApiCall {
            httpClient.get("api/v1/bookings/get_agent_service_counts") {
                parameter("agentId", agentId)
            }
        }
    }

    override suspend fun updateAgentBooking(request: UpdateAgentBookingRequestDto): Flow<Result<UpdateAgentBookingResponseDto>> {
        return safeApiCall {
            httpClient.post("api/v1/bookings/update_agent_booking") {
                setBody(request)
            }
        }
    }

    override suspend fun getAgentBookings(
        agentId: Int,
        pageNo: Int,
        pageSize: Int,
        bookingType: String
    ): Flow<Result<GetAgentBookingResponseDto>> {
        println("bookingTypeee $bookingType")
        return safeApiCall {
            httpClient.get("api/v1/bookings/get_agent_booking") {
                parameter("agentId", agentId)
                parameter("pageNo", pageNo)
                parameter("pageSize", pageSize)
                parameter("bookingType", bookingType)
            }
        }
    }
}
