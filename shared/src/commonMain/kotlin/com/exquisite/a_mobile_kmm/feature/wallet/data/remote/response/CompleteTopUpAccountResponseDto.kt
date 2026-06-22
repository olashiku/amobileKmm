package com.exquisite.a_mobile_kmm.feature.wallet.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable

@Serializable
data class CompleteTopUpAccountResponseDto(
    @Serializable(with = CompleteTopUpBalanceDataSerializer::class)
    val data: BalanceDto? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object CompleteTopUpBalanceDataSerializer :
    EmptyObjectAsNullSerializer<BalanceDto>(BalanceDto.serializer())
