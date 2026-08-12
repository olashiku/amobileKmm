package com.exquisite.a_mobile_kmm.feature.employee.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class GetAgentServiceCountsResponseDto(
    val responseMessage: String? = null,
    val responseCode: String? = null,
    val data: AgentServiceCountsDto? = null
)

@Serializable
data class AgentServiceCountsDto(
    val toiletCount: Int? = null,
    val pestControlCount: Int? = null,
    val basicCleaningCount: Int? = null,
    val deepCleaningCount: Int? = null,
    val septicRequestCount: Int? = null,
    val totalCount: Int? = null
)
