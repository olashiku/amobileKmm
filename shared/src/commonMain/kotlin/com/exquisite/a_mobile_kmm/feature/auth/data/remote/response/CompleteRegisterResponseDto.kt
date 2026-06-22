package com.exquisite.a_mobile_kmm.feature.auth.data.remote.response

import com.exquisite.a_mobile_kmm.core.network.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class CompleteRegisterResponseDto(
     val responseMessage: String,
     val responseCode: String
)
