package com.exquisite.a_mobile_kmm.feature.wallet.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class BalanceDto(
    val id: Int = 0,
    val balance: Double = 0.0,
    val created_at: String = "",
    val updated_at: String = ""
)

@Serializable
data class GetCustomerBalanceResponseDto(
    @Serializable(with = BalanceDataSerializer::class)
    val data: BalanceDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object BalanceDataSerializer :
    EmptyObjectAsNullSerializer<BalanceDto>(BalanceDto.serializer())
