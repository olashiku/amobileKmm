package com.exquisite.a_mobile_kmm.feature.wallet.data.remote.response

import com.exquisite.a_mobile_kmm.core.serialization.EmptyObjectAsNullSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer

@Serializable
data class TransactionDto(
    val id: Int = 0,
    val narration: String = "",
    val drcr: String = "",
    val amount: Double = 0.0,
    val transaction_id: String = "",
    val created_at: String = "",
    val updated_at: String = ""
)

@Serializable
data class GetCustomerTransactionsResponseDto(
    @Serializable(with = TransactionListSerializer::class)
    val data: List<TransactionDto>? = null,
    val responseMessage: String = "",
    val responseCode: String = ""
)

object TransactionListSerializer :
    EmptyObjectAsNullSerializer<List<TransactionDto>>(ListSerializer(TransactionDto.serializer()))
